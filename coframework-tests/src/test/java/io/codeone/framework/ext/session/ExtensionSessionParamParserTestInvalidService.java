package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.ExtensionSession;
import io.codeone.framework.ext.annotation.RouteBy;

public class ExtensionSessionParamParserTestInvalidService {

    @ExtensionSession(BizScenarioResolvePolicy.FIRST)
    public Object first(Object param1, Object param2) {
        return null;
    }

    @ExtensionSession(BizScenarioResolvePolicy.LAST)
    public Object last(Object param1, Object param2) {
        return null;
    }

    @ExtensionSession(BizScenarioResolvePolicy.SPECIFIED)
    public Object specifiedNone(BizScenarioParam param1, BizScenarioParam param2) {
        return null;
    }

    @ExtensionSession(BizScenarioResolvePolicy.SPECIFIED)
    public Object specifiedDuplicate(@RouteBy BizScenarioParam param1, @RouteBy BizScenarioParam param2) {
        return null;
    }

    @ExtensionSession(BizScenarioResolvePolicy.SPECIFIED)
    public Object specifiedInvalid(BizScenarioParam param1, @RouteBy Object param2) {
        return null;
    }

    @ExtensionSession(BizScenarioResolvePolicy.CUSTOM)
    public Object customInvalid(Object param1) {
        return null;
    }
}
