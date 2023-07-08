package io.codeone.framework.ext.session.sdk.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.RouteByContext;

@Ability
@RouteByContext
public interface TestExtSessionAbility {

    String genCode();
}
