package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenario;

@Extension(bizId = "foo")
public class AbilityTestAbilityForFoo implements AbilityTestAbility {

    @Override
    public Object byContext() {
        return "foo";
    }

    @Override
    public Object byRouteBy(AbilityTestParam param, BizScenario bizScenario) {
        return "foo";
    }

    @Override
    public Object byRouteByContext(AbilityTestParam param) {
        return "foo";
    }

    @Override
    public Object byParamType(AbilityTestParam param) {
        return "foo";
    }

    @Override
    public Object byDefault(Object param) {
        return "foo";
    }
}
