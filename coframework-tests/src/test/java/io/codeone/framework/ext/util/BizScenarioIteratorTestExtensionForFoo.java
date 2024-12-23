package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "foo", scenarios = {"s1.a", "s1.b"})
public class BizScenarioIteratorTestExtensionForFoo implements BizScenarioIteratorTestExtensionPoint {

    @Override
    public Object execute(BizScenario bizScenario) {
        return "foo";
    }
}
