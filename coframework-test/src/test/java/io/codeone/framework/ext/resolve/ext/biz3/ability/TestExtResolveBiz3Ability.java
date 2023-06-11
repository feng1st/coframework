package io.codeone.framework.ext.resolve.ext.biz3.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.constants.TestExtConstants;
import io.codeone.framework.ext.resolve.sdk.ability.TestExtResolveAbility;

@Extension(bizId = TestExtConstants.BIZ3)
public class TestExtResolveBiz3Ability implements TestExtResolveAbility {

    @Override
    public String genCode() {
        return "3";
    }
}
