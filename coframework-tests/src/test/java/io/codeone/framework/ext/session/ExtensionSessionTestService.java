package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.ExtensionSession;
import io.codeone.framework.ext.annotation.RouteBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtensionSessionTestService {

    @Autowired
    private ExtensionSessionTestAbility extensionSessionTestAbility;

    @ExtensionSession
    public Object autoFirst(BizScenarioParam param1, BizScenarioParam param2) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession
    public Object autoSpecified(BizScenarioParam param1, @RouteBy BizScenarioParam param2) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession(customResolver = ExtensionSessionTestResolver.class)
    public Object autoCustom(Object bizId) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession
    public Object autoIgnore(Object bizId) {
        return "ignore";
    }

    @ExtensionSession(BizScenarioResolvePolicy.FIRST)
    public Object first(Object param1, BizScenarioParam param2, BizScenarioParam param3) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession(BizScenarioResolvePolicy.LAST)
    public Object last(BizScenarioParam param1, BizScenarioParam param2, Object param3) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession(BizScenarioResolvePolicy.SPECIFIED)
    public Object specified(BizScenarioParam param1, @RouteBy BizScenarioParam param2) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession(value = BizScenarioResolvePolicy.CUSTOM, customResolver = ExtensionSessionTestResolver.class)
    public Object custom(Object bizId) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSession(BizScenarioResolvePolicy.IGNORE)
    public Object ignore(Object bizId) {
        return "ignore";
    }

    @ExtensionSessionTestAnno
    public Object annoRouting(Object param1, BizScenarioParam param2, BizScenarioParam param3, Object param4) {
        return extensionSessionTestAbility.execute(null);
    }

    @ExtensionSessionTestAnno
    public Object annoNoRouting(Object param1, BizScenarioParam param2, BizScenarioParam param3, Object param4) {
        return "ignore";
    }
}
