package io.codeone.framework.ext.scope.domain.service;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.scope.BizScenarioResolvePolicy;
import io.codeone.framework.ext.scope.BizScenarioScope;
import io.codeone.framework.ext.scope.ResolveFrom;
import io.codeone.framework.ext.scope.ext.shared.resolve.AdminNameBizScenarioResolver;
import io.codeone.framework.ext.scope.sdk.ability.TestExtScopeAbility;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@BizScenarioScope
public class TestScopeService {

    @Resource
    private TestExtScopeAbility testExtScopeAbility;

    public String genCodeClassAutoFirst(BizScenario param1, BizScenario param2) {
        return testExtScopeAbility.genCode();
    }

    public String genCodeClassAutoSpecified(BizScenario param1, @ResolveFrom BizScenario param2) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope
    public String genCodeAutoFirst(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope
    public String genCodeAutoSpecified(BizScenario param1, @ResolveFrom BizScenario param2, BizScenario param3) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope(customResolver = AdminNameBizScenarioResolver.class)
    public String genCodeAutoCustom(String adminName) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope(BizScenarioResolvePolicy.FIRST)
    public String genCodeFirst(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope(BizScenarioResolvePolicy.LAST)
    public String genCodeLast(BizScenario param1, BizScenario param2, BizScenario param3) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope(BizScenarioResolvePolicy.SPECIFIED)
    public String genCodeSpecified(BizScenario param1, @ResolveFrom BizScenario param2, BizScenario param3) {
        return testExtScopeAbility.genCode();
    }

    @BizScenarioScope(value = BizScenarioResolvePolicy.CUSTOM, customResolver = AdminNameBizScenarioResolver.class)
    public String genCodeCustom(String adminName) {
        return testExtScopeAbility.genCode();
    }
}
