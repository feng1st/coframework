package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenario;

@Ability
public interface AbilityTestAbility {

    Object byContext();

    Object byRouteBy(AbilityTestParam param, @RouteBy BizScenario bizScenario);

    @RouteByContext
    Object byRouteByContext(AbilityTestParam param);

    Object byParamType(AbilityTestParam param);

    Object byDefault(Object param);
}
