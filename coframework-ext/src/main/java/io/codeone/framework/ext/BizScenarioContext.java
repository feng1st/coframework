package io.codeone.framework.ext;

import io.codeone.framework.ext.bizscenario.BizScenarioStack;
import io.codeone.framework.plugin.function.Invokable;
import io.codeone.framework.plugin.function.VoidInvokable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BizScenarioContext {

    public BizScenario getBizScenario() {
        return BizScenarioStack.peek();
    }

    public <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        return BizScenarioStack.invoke(bizScenario, invokable);
    }

    public void invoke(BizScenario bizScenario, VoidInvokable invokable) throws Throwable {
        BizScenarioStack.invoke(bizScenario, invokable);
    }
}
