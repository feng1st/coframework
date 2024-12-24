package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "foo")
public class BizScenarioIteratorTestNoDefaultEpForFoo implements BizScenarioIteratorTestNoDefaultEp {

    @Override
    public Object execute(BizScenario bizScenario) {
        return "foo";
    }
}
