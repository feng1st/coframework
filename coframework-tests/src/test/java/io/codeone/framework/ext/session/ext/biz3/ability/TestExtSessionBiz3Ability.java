package io.codeone.framework.ext.session.ext.biz3.ability;

import io.codeone.framework.ext.annotation.Extension;
import io.codeone.framework.ext.session.sdk.ability.TestExtSessionAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ3)
public class TestExtSessionBiz3Ability implements TestExtSessionAbility {

    @Override
    public String genCode() {
        return "3";
    }
}
