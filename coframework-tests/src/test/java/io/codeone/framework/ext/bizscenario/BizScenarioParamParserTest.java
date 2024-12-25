package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioParamParserTest {

    @Test
    public void duplicateRouteBy() {
        Assertions.assertEquals("Duplicate @RouteBy parameters found on method 'public abstract java.lang.Object io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.duplicateRouteBy(io.codeone.framework.ext.BizScenario,io.codeone.framework.ext.BizScenario)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("duplicateRouteBy", BizScenario.class, BizScenario.class))).getMessage());
    }

    @Test
    public void invalidRouteBy() {
        Assertions.assertEquals("Parameter annotated with @RouteBy is not BizScenarioParam on method 'public abstract java.lang.Object io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.invalidRouteBy(java.lang.Object)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("invalidRouteBy", Object.class))).getMessage());
    }

    @Test
    public void routeByConflict() {
        Assertions.assertEquals("Conflicting annotations @RouteBy and @RouteByContext on method 'public abstract java.lang.Object io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.routeByConflict(io.codeone.framework.ext.BizScenario)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("routeByConflict", BizScenario.class))).getMessage());
    }

    @Test
    public void duplicateParam() {
        Assertions.assertEquals("Duplicate BizScenarioParam parameters found on method 'public abstract java.lang.Object io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.duplicateParam(io.codeone.framework.ext.BizScenario,io.codeone.framework.ext.BizScenario)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("duplicateParam", BizScenario.class, BizScenario.class))).getMessage());
    }
}