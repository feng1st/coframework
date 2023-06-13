package io.codeone.framework.ext.scope;


import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.scope.domain.service.TestScopeService;
import io.codeone.framework.ext.shared.constants.ExtConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ExtResolveTests {

    @Resource
    private TestScopeService testScopeService;

    @Test
    void testByClass() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);

        Assertions.assertEquals("1", testScopeService.genCodeClassAutoFirst(param1, param2));
        Assertions.assertEquals("2", testScopeService.genCodeClassAutoSpecified(param1, param2));
    }

    @Test
    void testByMethod() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testScopeService.genCodeFirst(param1, param2, param3));
        Assertions.assertEquals("3", testScopeService.genCodeLast(param1, param2, param3));
        Assertions.assertEquals("2", testScopeService.genCodeSpecified(param1, param2, param3));
    }

    @Test
    void testAuto() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testScopeService.genCodeAutoFirst(param1, param2, param3));
        Assertions.assertEquals("2", testScopeService.genCodeAutoSpecified(param1, param2, param3));
        Assertions.assertEquals("2", testScopeService.genCodeAutoCustom("John"));
        Assertions.assertEquals("3", testScopeService.genCodeAutoCustom("Bob"));
        Assertions.assertEquals("1", testScopeService.genCodeAutoCustom("Jim"));
    }

    @Test
    void testCustom() {
        Assertions.assertEquals("2", testScopeService.genCodeCustom("John"));
        Assertions.assertEquals("3", testScopeService.genCodeCustom("Bob"));
        Assertions.assertEquals("1", testScopeService.genCodeCustom("Jim"));
    }
}
