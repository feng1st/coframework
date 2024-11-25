package io.codeone.framework.ext.session.ext.biz2.ability;

import io.codeone.framework.ext.annotation.Extension;
import io.codeone.framework.ext.session.sdk.ability.TestExtSessionAbility;
import io.codeone.framework.ext.shared.constants.ExtConstants;

@Extension(bizId = ExtConstants.BIZ2)
public class TestExtSessionBiz2Ability implements TestExtSessionAbility {

    @Override
    public String genCode() {
        return "2";
    }
}
