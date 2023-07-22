package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.VoidInvokable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExtensionUtils {

    public BizScenario getBizScenario() {
        return BizScenarioContext.getBizScenario();
    }

    public <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        return BizScenarioContext.invoke(bizScenario, invokable);
    }

    public void invoke(BizScenario bizScenario, VoidInvokable invokable) throws Throwable {
        BizScenarioContext.invoke(bizScenario, () -> {
            invokable.invoke();
            return null;
        });
    }
}
