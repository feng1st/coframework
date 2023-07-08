package io.codeone.framework.ext.session.ext.biz1.ability;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.session.sdk.ability.TestExtSessionAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ1)
public class TestExtSessionBiz1Ability implements TestExtSessionAbility {

    @Override
    public String genCode() {
        return "1";
    }
}
