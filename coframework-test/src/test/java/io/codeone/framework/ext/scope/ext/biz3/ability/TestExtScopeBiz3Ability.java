package io.codeone.framework.ext.scope.ext.biz3.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.scope.sdk.ability.TestExtScopeAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ3)
public class TestExtScopeBiz3Ability implements TestExtScopeAbility {

    @Override
    public String genCode() {
        return "3";
    }
}
