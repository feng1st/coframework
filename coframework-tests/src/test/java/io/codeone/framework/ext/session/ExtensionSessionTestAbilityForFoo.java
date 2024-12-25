package io.codeone.framework.ext.session;

import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "foo")
public class ExtensionSessionTestAbilityForFoo implements ExtensionSessionTestAbility {

    @Override
    public Object execute(Object param) {
        return "foo";
    }
}
