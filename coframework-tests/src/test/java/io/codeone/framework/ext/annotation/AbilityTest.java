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

    @Test
    public void byContext() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byContext()));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byContext()));
    }

    @Test
    public void byRouteBy() {
        Assertions.assertEquals("foo",
                abilityTestAbility.byRouteBy(AbilityTestParam.of("bar"), BizScenario.ofBizId("foo")));
        Assertions.assertEquals("bar",
                abilityTestAbility.byRouteBy(AbilityTestParam.of("foo"), BizScenario.ofBizId("bar")));
    }

    @Test
    public void byRouteByContext() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byRouteByContext(AbilityTestParam.of("bar"))));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byRouteByContext(AbilityTestParam.of("foo"))));
    }

    @Test
    public void byParamType() {
        Assertions.assertEquals("foo",
                abilityTestAbility.byParamType(AbilityTestParam.of("foo")));
        Assertions.assertEquals("bar",
                abilityTestAbility.byParamType(AbilityTestParam.of("bar")));
    }

    @Test
    public void byDefault() throws Throwable {
        Assertions.assertEquals("foo",
                BizScenarioContext.invoke(BizScenario.ofBizId("foo"),
                        () -> abilityTestAbility.byDefault(new Object())));
        Assertions.assertEquals("bar",
                BizScenarioContext.invoke(BizScenario.ofBizId("bar"),
                        () -> abilityTestAbility.byDefault(new Object())));
    }
}
