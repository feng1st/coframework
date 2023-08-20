package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.model.ExtensionCoordinate;
import io.codeone.framework.ext.monitor.ExtInvocation;
import io.codeone.framework.ext.monitor.ExtInvocationMonitor;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.repo.ExtensionRepo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * This is an Extension-routing-and-executing proxy for a specific Extensible
 * interface.
 *
 * <p>Whenever this proxy is called, it will try to find a {@link BizScenario}
 * instance from a pre-scanned parameter of the delegated method or the
 * {@link BizScenarioContext}, and then use the {@code BizScenario} instance to
 * route and invoke the particular Extension implementation of the Extensible
 * interface this proxy represents. An exception will be thrown if no
 * {@code BizScenario} instance can be found.
 *
 * @param <T> the type of the Extensible interface
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExtProxyInvocationHandler<T> implements InvocationHandler {

    private final Class<T> extensibleClass;

    @Resource
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Resource
    private ExtensionRepo extensionRepo;

    @Resource
    private Optional<ExtInvocationMonitor> extInvocationMonitor;

    /**
     * Constructs an Extension-routing-and-executing proxy for the specified
     * Extensible interface.
     *
     * @param extensibleClass the Extensible interface this proxy represents
     */
    public ExtProxyInvocationHandler(Class<T> extensibleClass) {
        this.extensibleClass = extensibleClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        BizScenario bizScenario = getBizScenario(args, method);
        return BizScenarioContext.invoke(bizScenario, () -> invoke(method, args, bizScenario));
    }

    private BizScenario getBizScenario(Object[] args, Method method) {
        BizScenario bizScenario;
        int paramIndex = bizScenarioParamRepo.getParamIndex(method);
        if (paramIndex == -1) {
            bizScenario = BizScenarioContext.getBizScenario();
        } else {
            bizScenario = ((BizScenarioParam) args[paramIndex]).getBizScenario();
        }
        if (bizScenario == null) {
            throw new IllegalStateException("Could not get BizScenario from context or args");
        }
        return bizScenario;
    }

    private Object invoke(Method method, Object[] args, BizScenario bizScenario) throws Throwable {
        ExtensionCoordinate coordinate = ExtensionCoordinate.of(extensibleClass, bizScenario);
        BizScenarioExtension bizExt = extensionRepo.getExtension(coordinate);

        if (extInvocationMonitor.isPresent()) {
            try {
                extInvocationMonitor.get().monitor(ExtInvocation.of(method, coordinate, bizExt));
            } catch (Exception ignored) {
            }
        }

        try {
            return method.invoke(bizExt.getExtension(), args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
