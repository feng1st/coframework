package io.codeone.framework.ext.session.sdk.ability;

import io.codeone.framework.ext.annotation.Ability;
import io.codeone.framework.ext.annotation.RouteByContext;

@Ability
@RouteByContext
public interface TestExtSessionAbility {

    String genCode();
}
