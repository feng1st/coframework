package io.codeone.framework.ext.annotation;

@Extension(bizId = "foo")
public class AbilityTestAnotherAbilityForFoo implements AbilityTestAnotherAbility {

    @Override
    public Object byRouteByContext(AbilityTestParam param) {
        return "foo";
    }
}
