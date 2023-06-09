package io.codeone.framework.ext.functionality.ext.defaultext.ability;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.functionality.sdk.ability.Ability1;

@Extension
public class DefaultAbility1 implements Ability1 {

    @Override
    public int doSomething(BizScenario param) {
        return 0;
    }
}
