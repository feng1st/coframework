package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "foo.bar", scenarios = "s2")
public class BizScenarioIteratorTestExtensionForBar implements BizScenarioIteratorTestExtensionPoint {

    @Override
    public Object execute(BizScenario bizScenario) {
        return "bar";
    }
}
