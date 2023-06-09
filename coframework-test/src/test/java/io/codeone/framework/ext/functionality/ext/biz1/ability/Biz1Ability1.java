package io.codeone.framework.ext.functionality.ext.biz1.ability;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.functionality.sdk.ability.Ability1;

@Extension(bizId = "biz1")
public class Biz1Ability1 implements Ability1 {

    @Override
    public int doSomething(BizScenario param) {
        return 1;
    }
}
