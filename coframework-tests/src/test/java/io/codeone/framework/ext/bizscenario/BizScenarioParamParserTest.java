package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioParamParserTest {

    @Test
    public void duplicateRouteBy() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                        BizScenarioParamParserTestNonAbility.class.getMethod("duplicateRouteBy", BizScenario.class, BizScenario.class)));
    }

    @Test
    public void invalidRouteBy() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                        BizScenarioParamParserTestNonAbility.class.getMethod("invalidRouteBy", Object.class)));
    }

    @Test
    public void routeByConflict() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                        BizScenarioParamParserTestNonAbility.class.getMethod("routeByConflict", BizScenario.class)));
    }

    @Test
    public void duplicateParam() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> BizScenarioParamParser.parseParamIndex(BizScenarioParamParserTestNonAbility.class,
                        BizScenarioParamParserTestNonAbility.class.getMethod("duplicateParam", BizScenario.class, BizScenario.class)));
    }
}