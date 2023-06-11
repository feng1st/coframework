package io.codeone.framework.ext.resolve.sdk.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.RouteByContext;

@Ability
@RouteByContext
public interface TestExtResolveAbility {

    String genCode();
}
