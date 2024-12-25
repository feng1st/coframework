package io.codeone.framework.ext.annotation;

@Ability
@RouteByContext
public interface AbilityTestAnotherAbility {

    Object byRouteByContext(AbilityTestParam param);

    default void exception() {
        throw new RuntimeException();
    }
}
