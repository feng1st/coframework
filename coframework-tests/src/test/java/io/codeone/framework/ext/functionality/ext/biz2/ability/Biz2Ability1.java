package io.codeone.framework.ext.functionality.ext.biz2.ability;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.functionality.sdk.ability.Ability1;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ2)
public class Biz2Ability1 implements Ability1 {

    @Override
    public int doSomething(BizScenario param) {
        return 2;
    }
}
