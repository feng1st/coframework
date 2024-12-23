package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.Extension;

@Extension
public class BizScenarioIteratorTestExtensionDefault implements BizScenarioIteratorTestExtensionPoint {

    @Override
    public Object execute(BizScenario bizScenario) {
        return "default";
    }
}
