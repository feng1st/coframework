package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.RouteBy;
import io.codeone.framework.ext.annotation.RouteByContext;

public interface BizScenarioParamParserTestNonAbility {

    Object duplicateRouteBy(@RouteBy BizScenario param1, @RouteBy BizScenario param2);

    Object invalidRouteBy(@RouteBy Object param);

    @RouteByContext
    Object routeByConflict(@RouteBy BizScenario param);

    Object duplicateParam(BizScenario param1, BizScenario param2);
}
