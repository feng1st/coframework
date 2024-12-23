package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenario;

@Extension(bizId = "bar")
public class AbilityTestAbilityForBar implements AbilityTestAbility {

    @Override
    public Object byContext() {
        return "bar";
    }

    @Override
    public Object byRouteBy(AbilityTestParam param, BizScenario bizScenario) {
        return "bar";
    }

    @Override
    public Object byRouteByContext(AbilityTestParam param) {
        return "bar";
    }

    @Override
    public Object byParamType(AbilityTestParam param) {
        return "bar";
    }

    @Override
    public Object byDefault(Object param) {
        return "bar";
    }
}
