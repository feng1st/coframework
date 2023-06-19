package io.codeone.framework.ext.scope;


import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.scope.domain.service.TestExtScopeService;
import io.codeone.framework.ext.shared.constants.ExtConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ExtResolveTests {

    @Resource
    private TestExtScopeService testExtScopeService;

    @Test
    void testByClass() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);

        Assertions.assertEquals("1", testExtScopeService.genCodeClassAutoFirst(param1, param2));
        Assertions.assertEquals("2", testExtScopeService.genCodeClassAutoSpecified(param1, param2));
    }

    @Test
    void testByMethod() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testExtScopeService.genCodeFirst(param1, param2, param3));
        Assertions.assertEquals("3", testExtScopeService.genCodeLast(param1, param2, param3));
        Assertions.assertEquals("2", testExtScopeService.genCodeSpecified(param1, param2, param3));
    }

    @Test
    void testAuto() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testExtScopeService.genCodeAutoFirst(param1, param2, param3));
        Assertions.assertEquals("2", testExtScopeService.genCodeAutoSpecified(param1, param2, param3));
        Assertions.assertEquals("2", testExtScopeService.genCodeAutoCustom("John"));
        Assertions.assertEquals("3", testExtScopeService.genCodeAutoCustom("Bob"));
        Assertions.assertEquals("1", testExtScopeService.genCodeAutoCustom("Jim"));
    }

    @Test
    void testCustom() {
        Assertions.assertEquals("2", testExtScopeService.genCodeCustom("John"));
        Assertions.assertEquals("3", testExtScopeService.genCodeCustom("Bob"));
        Assertions.assertEquals("1", testExtScopeService.genCodeCustom("Jim"));
    }
}
