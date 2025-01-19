package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioParamParserTest {

    @Test
    public void duplicateRouteBy() {
        Assertions.assertEquals(
                "Method \"io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.duplicateRouteBy(BizScenario, BizScenario)\" contains multiple parameters annotated with @RouteBy. Only one is allowed.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("duplicateRouteBy", BizScenario.class, BizScenario.class))).getMessage());
    }

    @Test
    public void invalidRouteBy() {
        Assertions.assertEquals("Parameter at index 0 in method \"io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.invalidRouteBy(Object)\", annotated with @RouteBy, must be of type BizScenarioParam.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("invalidRouteBy", Object.class))).getMessage());
    }

    @Test
    public void routeByConflict() {
        Assertions.assertEquals("Method \"io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.routeByConflict(BizScenario)\" contains conflicting annotations: @RouteBy and @RouteByContext. Only one is allowed.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("routeByConflict", BizScenario.class))).getMessage());
    }

    @Test
    public void duplicateParam() {
        Assertions.assertEquals("Method \"io.codeone.framework.ext.bizscenario.BizScenarioParamParserTestNonAbility.duplicateParam(BizScenario, BizScenario)\" contains multiple parameters of type BizScenarioParam. Only one is allowed.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                                BizScenarioParamParserTestNonAbility.class.getMethod("duplicateParam", BizScenario.class, BizScenario.class))).getMessage());
    }
}