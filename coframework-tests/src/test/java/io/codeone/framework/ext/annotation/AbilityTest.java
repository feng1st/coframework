package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbilityTest {

    @Autowired
    private AbilityTestAbility abilityTestAbility;

    @Autowired
    private AbilityTestAnotherAbility abilityTestAnotherAbility;

    @Test
    public void byContext() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byContext()));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byContext()));
        Assertions.assertEquals("default",
                BizScenarioContext.invoke(BizScenario.ofBizId("baz"),
                        () -> abilityTestAbility.byContext()));
    }

    @Test
    public void byRouteBy() {
        Assertions.assertEquals("foo",
                abilityTestAbility.byRouteBy(AbilityTestParam.of("bar"), BizScenario.ofBizId("foo")));
        Assertions.assertEquals("bar",
                abilityTestAbility.byRouteBy(AbilityTestParam.of("foo"), BizScenario.ofBizId("bar")));
        Assertions.assertEquals("default",
                abilityTestAbility.byRouteBy(AbilityTestParam.of("foo"), BizScenario.ofBizId("baz")));
    }

    @Test
    public void byRouteByContext() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byRouteByContext(AbilityTestParam.of("bar"))));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byRouteByContext(AbilityTestParam.of("foo"))));
        Assertions.assertEquals("default",
                BizScenarioContext.invoke(BizScenario.ofBizId("baz"),
                        () -> abilityTestAbility.byRouteByContext(AbilityTestParam.of("foo"))));

        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAnotherAbility.byRouteByContext(AbilityTestParam.of("bar"))));
    }

    @Test
    public void byParamType() {
        Assertions.assertEquals("foo",
                abilityTestAbility.byParamType(AbilityTestParam.of("foo")));
        Assertions.assertEquals("bar",
                abilityTestAbility.byParamType(AbilityTestParam.of("bar")));
        Assertions.assertEquals("default",
                abilityTestAbility.byParamType(AbilityTestParam.of("baz")));
    }

    @Test
    public void byDefault() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byDefault(new Object())));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byDefault(new Object())));
        Assertions.assertEquals("default",
                BizScenarioContext.invoke(BizScenario.ofBizId("baz"),
                        () -> abilityTestAbility.byDefault(new Object())));
    }

    @Test
    public void nullBizScenario() {
        Assertions.assertEquals("The BizScenario in parameter 0 of method \"io.codeone.framework.ext.annotation.AbilityTestAbility.byParamType(AbilityTestParam)\" is null. Ensure that a BizScenario is provided either in the parameter or the current context.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> abilityTestAbility.byParamType(null)).getMessage());
        Assertions.assertEquals("The BizScenario in parameter 0 of method \"io.codeone.framework.ext.annotation.AbilityTestAbility.byParamType(AbilityTestParam)\" is null. Ensure that a BizScenario is provided either in the parameter or the current context.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> abilityTestAbility.byParamType(AbilityTestParam.of())).getMessage());
        Assertions.assertEquals("Failed to retrieve BizScenario from current context for method \"io.codeone.framework.ext.annotation.AbilityTestAbility.byContext()\"",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> abilityTestAbility.byContext()).getMessage());
    }

    @Test
    public void exception() {
        Assertions.assertThrows(RuntimeException.class,
                () -> BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAnotherAbility.exception()));
    }

    @Test
    public void voidInvokable() throws Throwable {
        BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                () -> {
                    abilityTestAbility.byDefault(new Object());
                });
    }

    @Test
    public void methodOfObject() throws Throwable {
        BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                () -> abilityTestAbility.hashCode());
    }
}
