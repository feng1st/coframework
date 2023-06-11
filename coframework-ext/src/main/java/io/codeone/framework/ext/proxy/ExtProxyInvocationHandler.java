package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.monitor.ExtInvocationMonitor;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.repo.ExtensionRepo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExtProxyInvocationHandler<T> implements InvocationHandler {

    private final BizScenarioParamRepo bizScenarioParamRepo;

    private final ExtensionRepo extensionRepo;

    private final ExtInvocationMonitor extInvocationMonitor;

    private final Class<T> extensibleClass;

    public ExtProxyInvocationHandler(BizScenarioParamRepo bizScenarioParamRepo,
                                     ExtensionRepo extensionRepo,
                                     ExtInvocationMonitor extInvocationMonitor,
                                     Class<T> extensibleClass) {
        this.bizScenarioParamRepo = bizScenarioParamRepo;
        this.extensionRepo = extensionRepo;
        this.extInvocationMonitor = extInvocationMonitor;
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

    private Object invoke(Method method, Object[] args, BizScenario bizScenario) {
        BizScenarioExtension bizExt = extensionRepo.getExtension(extensibleClass, bizScenario);

        if (extInvocationMonitor != null) {
            try {
                extInvocationMonitor.monitor(extensibleClass, method, bizScenario, bizExt);
            } catch (Exception ignored) {
            }
        }

        try {
            return method.invoke(bizExt.getExtension(), args);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new IllegalStateException(e.getCause());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
