package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.repo.ExtensionRepo;
import io.codeone.framework.ext.util.LazyBean;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExtProxyInvocationHandler<T> implements InvocationHandler {

    private final LazyBean<BizScenarioParamRepo> bizScenarioParamRepo;

    private final LazyBean<ExtensionRepo> extensionRepo;

    private final Class<T> extensibleClass;

    public ExtProxyInvocationHandler(BeanFactory beanFactory, Class<T> extensibleClass) {
        this.bizScenarioParamRepo = new LazyBean<>(beanFactory, BizScenarioParamRepo.class);
        this.extensionRepo = new LazyBean<>(beanFactory, ExtensionRepo.class);
        this.extensibleClass = extensibleClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getClass".equals(method.getName())) {
            return extensibleClass;
        } else if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        BizScenario bizScenario = getBizScenario(args, method);
        return BizScenarioContext.invoke(bizScenario, () -> invoke(method, args, bizScenario));
    }

    private BizScenario getBizScenario(Object[] args, Method method) {
        BizScenario bizScenario;
        int paramIndex = bizScenarioParamRepo.get().getParamIndex(method);
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
        BizScenarioExtension bizExt = extensionRepo.get().getExtension(extensibleClass, bizScenario);
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
