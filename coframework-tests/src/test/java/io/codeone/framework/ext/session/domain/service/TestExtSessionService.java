package io.codeone.framework.ext.session.domain.service;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.session.BizScenarioResolvePolicy;
import io.codeone.framework.ext.session.ExtensionSession;
import io.codeone.framework.ext.session.ResolveFrom;
import io.codeone.framework.ext.session.ext.shared.resolve.AdminNameBizScenarioResolver;
import io.codeone.framework.ext.session.sdk.ability.TestExtSessionAbility;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@ExtensionSession
public class TestExtSessionService {

    @Resource
    private TestExtSessionAbility testExtSessionAbility;

    public String genCodeClassAutoFirst(BizScenario param1, BizScenario param2) {
        return testExtSessionAbility.genCode();
    }

    public String genCodeClassAutoSpecified(BizScenario param1, @ResolveFrom BizScenario param2) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession
    public String genCodeAutoFirst(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession
    public String genCodeAutoSpecified(BizScenario param1, @ResolveFrom BizScenario param2, BizScenario param3) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession(customResolver = AdminNameBizScenarioResolver.class)
    public String genCodeAutoCustom(String adminName) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession(BizScenarioResolvePolicy.FIRST)
    public String genCodeFirst(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession(BizScenarioResolvePolicy.LAST)
    public String genCodeLast(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession(BizScenarioResolvePolicy.SPECIFIED)
    public String genCodeSpecified(BizScenario param1, @ResolveFrom BizScenario param2, BizScenario param3) {
        return testExtSessionAbility.genCode();
    }

    @ExtensionSession(value = BizScenarioResolvePolicy.CUSTOM, customResolver = AdminNameBizScenarioResolver.class)
    public String genCodeCustom(String adminName) {
        return testExtSessionAbility.genCode();
    }
}
