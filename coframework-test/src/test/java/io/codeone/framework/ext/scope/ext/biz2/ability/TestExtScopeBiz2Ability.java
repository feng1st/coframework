package io.codeone.framework.ext.scope.ext.biz2.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.scope.sdk.ability.TestExtScopeAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ2)
public class TestExtScopeBiz2Ability implements TestExtScopeAbility {

    @Override
    public String genCode() {
        return "2";
    }
}
