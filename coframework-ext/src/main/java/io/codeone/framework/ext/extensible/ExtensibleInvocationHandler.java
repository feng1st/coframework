package io.codeone.framework.ext.extensible;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.bizscenario.BizScenarioParamRepo;
import io.codeone.framework.ext.extension.ExtensionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Invocation handler for proxies of {@code Extensible} interfaces.
 *
 * <p>This handler dynamically resolves the appropriate {@code Extension} implementation
 * based on the {@link BizScenario} associated with the method invocation.
 *
 * @param <T> the type of the extensible interface
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExtensibleInvocationHandler<T> implements InvocationHandler {

    private final Class<T> extensibleInterface;

    @Autowired
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Autowired
    private ExtensionRepo extensionRepo;

    /**
     * Constructs an invocation handler for the specified extensible interface.
     *
     * @param extensibleInterface the extensible interface to handle
     */
    public ExtensibleInvocationHandler(Class<T> extensibleInterface) {
        this.extensibleInterface = extensibleInterface;
    }

    /**
     * Handles a method invocation on the proxy.
     *
     * <p>Resolves the appropriate {@code Extension} implementation and delegates
     * the method call to it.
     *
     * @param proxy  the proxy instance
     * @param method the method being invoked
     * @param args   the arguments for the method call
     * @return the result of the method invocation
     * @throws Throwable if any error occurs during invocation
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        BizScenario bizScenario = retrieveBizScenario(args, method);
        return BizScenarioContext.invoke(bizScenario, () -> invoke(method, args, bizScenario));
    }

    /**
     * Resolves the {@link BizScenario} for the method invocation.
     *
     * @param args   the method arguments
     * @param method the method being invoked
     * @return the resolved {@link BizScenario}
     * @throws IllegalStateException if no valid {@link BizScenario} can be resolved
     */
    private BizScenario retrieveBizScenario(Object[] args, Method method) {
        BizScenario bizScenario;

        int paramIndex = bizScenarioParamRepo.getParamIndex(method);
        if (paramIndex >= 0) {
            BizScenarioParam bizScenarioParam = (BizScenarioParam) args[paramIndex];
            if (bizScenarioParam != null
                    && (bizScenario = bizScenarioParam.getBizScenario()) != null) {
                return bizScenario;
            }
        }

        if ((bizScenario = BizScenarioContext.getBizScenario()) != null) {
            return bizScenario;
        }

        if (paramIndex >= 0) {
            throw new IllegalStateException(String.format(
                    "Failed to retrieve BizScenario from parameter %d of method '%s' or current context",
                    paramIndex,
                    method));
        } else {
            throw new IllegalStateException(String.format(
                    "Failed to retrieve BizScenario from current context for method '%s'",
                    method));
        }
    }

    /**
     * Delegates the method invocation to the appropriate {@code Extension}.
     *
     * @param method      the method being invoked
     * @param args        the method arguments
     * @param bizScenario the resolved {@link BizScenario}
     * @return the result of the method invocation
     * @throws Throwable if any error occurs during invocation
     */
    private Object invoke(Method method, Object[] args, BizScenario bizScenario) throws Throwable {
        Object extension = extensionRepo.getExtension(extensibleInterface, bizScenario);
        try {
            return method.invoke(extension, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
