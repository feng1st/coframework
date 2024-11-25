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

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExtensibleInvocationHandler<T> implements InvocationHandler {

    private final Class<T> extensibleInterface;

    @Autowired
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Autowired
    private ExtensionRepo extensionRepo;

    public ExtensibleInvocationHandler(Class<T> extensibleInterface) {
        this.extensibleInterface = extensibleInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        BizScenario bizScenario = retrieveBizScenario(args, method);
        return BizScenarioContext.invoke(bizScenario, () -> invoke(method, args, bizScenario));
    }

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

    private Object invoke(Method method, Object[] args, BizScenario bizScenario) throws Throwable {
        Object extension = extensionRepo.getExtension(extensibleInterface, bizScenario);
        try {
            return method.invoke(extension, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
