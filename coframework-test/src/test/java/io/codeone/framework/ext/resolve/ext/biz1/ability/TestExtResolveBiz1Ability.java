package io.codeone.framework.ext.resolve.ext.biz1.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.constants.TestExtConstants;
import io.codeone.framework.ext.resolve.sdk.ability.TestExtResolveAbility;

@Extension(bizId = TestExtConstants.BIZ1)
public class TestExtResolveBiz1Ability implements TestExtResolveAbility {

    @Override
    public String genCode() {
        return "1";
    }
}
