package io.codeone.framework.ext.session;

import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "bar")
public class ExtensionSessionTestAbilityForBar implements ExtensionSessionTestAbility {

    @Override
    public Object execute(Object param) {
        return "bar";
    }
}
