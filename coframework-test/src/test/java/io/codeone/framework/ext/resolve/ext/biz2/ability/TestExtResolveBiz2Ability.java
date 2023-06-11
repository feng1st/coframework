package io.codeone.framework.ext.resolve.ext.biz2.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.constants.TestExtConstants;
import io.codeone.framework.ext.resolve.sdk.ability.TestExtResolveAbility;

@Extension(bizId = TestExtConstants.BIZ2)
public class TestExtResolveBiz2Ability implements TestExtResolveAbility {

    @Override
    public String genCode() {
        return "2";
    }
}
