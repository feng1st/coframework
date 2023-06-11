package io.codeone.framework.ext.resolve.domain.service;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.resolve.BizScenarioResolve;
import io.codeone.framework.ext.resolve.BizScenarioResolvePolicy;
import io.codeone.framework.ext.resolve.ResolveBy;
import io.codeone.framework.ext.resolve.ext.shared.resolve.AdminNameBizScenarioResolver;
import io.codeone.framework.ext.resolve.sdk.ability.TestExtResolveAbility;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@BizScenarioResolve(BizScenarioResolvePolicy.LAST_PARAM)
public class TestResolveService {

    @Resource
    private TestExtResolveAbility testExtResolveAbility;

    public String genCodeByClass(BizScenario param1, BizScenario param2) {
        return testExtResolveAbility.genCode();
    }

    @BizScenarioResolve(BizScenarioResolvePolicy.FIRST_PARAM)
    public String genCodeFirstParam(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtResolveAbility.genCode();
    }

    @BizScenarioResolve(BizScenarioResolvePolicy.LAST_PARAM)
    public String genCodeLastParam(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtResolveAbility.genCode();
    }

    @BizScenarioResolve(BizScenarioResolvePolicy.SPECIFIED)
    public String genCodeSpecified(BizScenario param1, @ResolveBy BizScenario param2, BizScenario param3) {
        return testExtResolveAbility.genCode();
    }

    @BizScenarioResolve(value = BizScenarioResolvePolicy.CUSTOM, customResolver = AdminNameBizScenarioResolver.class)
    public String genCodeCustom(String adminName) {
        return testExtResolveAbility.genCode();
    }
}
