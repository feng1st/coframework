package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenario;

@Extension
public class AbilityTestAbilityDefault implements AbilityTestAbility {

    @Override
    public Object byContext() {
        return "default";
    }

    @Override
    public Object byRouteBy(AbilityTestParam param, BizScenario bizScenario) {
        return "default";
    }

    @Override
    public Object byRouteByContext(AbilityTestParam param) {
        return "default";
    }

    @Override
    public Object byParamType(AbilityTestParam param) {
        return "default";
    }

    @Override
    public Object byDefault(Object param) {
        return "default";
    }
}
