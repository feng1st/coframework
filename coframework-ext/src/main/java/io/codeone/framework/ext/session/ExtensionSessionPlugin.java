package io.codeone.framework.ext.session;

import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.common.util.TypeStringUtils;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.ExtensionSession;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.binding.PluginBindingProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * Plugin for handling methods annotated with {@link ExtensionSession}.
 *
 * <p>This plugin resolves the {@link BizScenario} for methods before execution
 * and integrates it into the execution context. The resolved {@link BizScenario}
 * is pushed to the {@link BizScenarioContext}, enabling subsequent routing of {@code
 * Extension} implementations based on the context. If no {@link BizScenario} is
 * resolved, the method executes normally.
 *
 * <p>The resolution process leverages the parameters of the method, the {@link
 * ExtensionSession} configuration, or a custom {@link BizScenarioResolver} specified
 * in the annotation.
 */
@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = ExtensionSession.class)
public class ExtensionSessionPlugin implements PluginBindingProcessor, Plugin {

    @Autowired
    private ExtensionSessionRepo extensionSessionRepo;

    @Autowired
    private BizScenarioResolverCache bizScenarioResolverCache;

    /**
     * Processes the method after binding by building the parameter index for resolving
     * {@link BizScenario}.
     *
     * @param method      the method being processed
     * @param targetClass the target class containing the method
     */
    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        if (session != null) {
            extensionSessionRepo.buildParamIndex(method, session);
        }
    }

    /**
     * Intercepts the method execution and resolves the {@link BizScenario} for
     * the current invocation.
     *
     * <p>If a {@link BizScenario} is resolved, it is pushed to the {@link BizScenarioContext},
     * ensuring that subsequent {@code Extension} implementations can route based
     * on this context. The method execution occurs within this resolved context.
     * If no {@link BizScenario} is resolved, the method executes without modifying
     * the context.
     *
     * @param method    the method being invoked
     * @param args      the arguments passed to the method
     * @param invokable the invokable representing the original method
     * @return the result of the method invocation
     * @throws Throwable if an error occurs during resolution or method execution
     */
    @Override
    public Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        BizScenario bizScenario = resolveBizScenario(method, args, session);
        if (bizScenario == null) {
            return invokable.invoke();
        }
        return BizScenarioContext.invoke(bizScenario, invokable);
    }

    /**
     * Resolves the {@link BizScenario} for the given method and arguments.
     *
     * <p>The resolution process checks:
     * <ul>
     *   <li>If a parameter annotated with {@link BizScenarioParam} contains a valid
     *   {@link BizScenario}</li>
     *   <li>If a custom resolver is specified in the {@link ExtensionSession} annotation</li>
     * </ul>
     *
     * @param method  the method being invoked
     * @param args    the arguments passed to the method
     * @param session the {@link ExtensionSession} annotation on the method
     * @return the resolved {@link BizScenario}, or {@code null} if no resolution
     * is needed
     * @throws IllegalStateException if the resolution fails due to missing or invalid
     *                               configuration
     */
    private BizScenario resolveBizScenario(Method method, Object[] args, ExtensionSession session) {

        BizScenario bizScenario;

        if (session == null) {
            for (Object arg : args) {
                if (arg instanceof BizScenarioParam) {
                    BizScenarioParam bizScenarioParam = (BizScenarioParam) arg;
                    if ((bizScenario = bizScenarioParam.getBizScenario()) != null) {
                        return bizScenario;
                    }
                }
            }
            return null;
        }

        int paramIndex = extensionSessionRepo.getParamIndex(method);

        if (paramIndex >= 0) {
            BizScenarioParam bizScenarioParam = (BizScenarioParam) args[paramIndex];
            if (bizScenarioParam != null
                    && (bizScenario = bizScenarioParam.getBizScenario()) != null) {
                return bizScenario;
            }
            throw new IllegalArgumentException(String.format(
                    "BizScenario is null for parameter %d in method \"%s\". Ensure the parameter is correctly initialized with a valid BizScenario.",
                    paramIndex,
                    TypeStringUtils.toString(method)));
        }

        if (paramIndex == ExtensionSessionRepo.INDEX_CUSTOM_RESOLVER) {
            BizScenarioResolver resolver = bizScenarioResolverCache.getResolver(session.customResolver());
            if ((bizScenario = resolver.resolve(args)) != null) {
                return bizScenario;
            }
            throw new IllegalArgumentException(String.format(
                    "BizScenario could not be resolved using the custom resolver \"%s\" for method \"%s\". Ensure the resolver is correctly implemented and can resolve a valid BizScenario.",
                    TypeStringUtils.toString(session.customResolver()),
                    TypeStringUtils.toString(method)));
        }

        return null;
    }
}
