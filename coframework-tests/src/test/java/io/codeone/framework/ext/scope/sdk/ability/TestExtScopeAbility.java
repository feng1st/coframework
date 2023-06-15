package io.codeone.framework.ext.scope.sdk.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.RouteByContext;

@Ability
@RouteByContext
public interface TestExtScopeAbility {

    String genCode();
}
