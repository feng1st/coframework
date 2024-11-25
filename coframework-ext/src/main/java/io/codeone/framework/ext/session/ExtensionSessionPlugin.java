package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.binding.PluginBindingProcessor;
import io.codeone.framework.plugin.function.Invokable;
import io.codeone.framework.plugin.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Objects;

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = ExtensionSession.class)
public class ExtensionSessionPlugin implements PluginBindingProcessor, Plugin {

    @Autowired
    private ExtensionSessionRepo extensionSessionRepo;

    @Autowired
    private BizScenarioResolverCache bizScenarioResolverCache;

    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        extensionSessionRepo.buildParamIndex(method, session);
    }

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

    private BizScenario resolveBizScenario(Method method, Object[] args, ExtensionSession session) {
        int paramIndex = extensionSessionRepo.getParamIndex(method);

        BizScenario bizScenario;
        if (paramIndex >= 0) {
            BizScenarioParam bizScenarioParam = (BizScenarioParam) args[paramIndex];
            if (bizScenarioParam != null
                    && (bizScenario = bizScenarioParam.getBizScenario()) != null) {
                return bizScenario;
            }
            if (session != null) {
                throw new IllegalStateException(String.format(
                        "BizScenario is null for parameter %d in method '%s'",
                        paramIndex,
                        method));
            }
        }

        if (paramIndex == ExtensionSessionRepo.INDEX_CUSTOM_RESOLVER) {
            Objects.requireNonNull(session);
            BizScenarioResolver resolver = bizScenarioResolverCache.getResolver(session.customResolver());
            if ((bizScenario = resolver.resolve(args)) != null) {
                return bizScenario;
            }
            throw new IllegalStateException(String.format(
                    "BizScenario could not be resolved using resolver '%s' for method '%s'",
                    session.customResolver().getSimpleName(),
                    method));
        }

        return null;
    }
}
