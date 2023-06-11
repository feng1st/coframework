package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.monitor.ExtInvocationMonitor;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.repo.ExtensionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Component(ExtProxyFactory.FACTORY_BEAN_NAME)
public class ExtProxyFactory {

    public static final String FACTORY_BEAN_NAME = "extProxyFactory";

    public static final String FACTORY_METHOD_NAME = "getProxy";

    @Autowired
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Autowired
    private ExtensionRepo extensionRepo;

    @Autowired(required = false)
    private ExtInvocationMonitor extInvocationMonitor;

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> extensibleClass) {
        ClassLoader classLoader = extensibleClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{extensibleClass};
        InvocationHandler invocationHandler = new ExtProxyInvocationHandler<>(
                bizScenarioParamRepo, extensionRepo, extInvocationMonitor, extensibleClass);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
