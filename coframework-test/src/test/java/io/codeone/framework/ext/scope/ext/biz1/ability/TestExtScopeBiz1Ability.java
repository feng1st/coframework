package io.codeone.framework.ext.scope.ext.biz1.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.scope.sdk.ability.TestExtScopeAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ1)
public class TestExtScopeBiz1Ability implements TestExtScopeAbility {

    @Override
    public String genCode() {
        return "1";
    }
}
