package io.codeone.framework.ext.functionality;


import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.functionality.sdk.ability.Ability1;
import io.codeone.framework.ext.shared.constants.ExtConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ExtFunctionalityTests {

    @Resource
    private Ability1 ability1;

    @Test
    void testAbility1() {
        Assertions.assertEquals(1, ability1.doSomething(BizScenario.ofBizId(ExtConstants.BIZ1)));
        Assertions.assertEquals(2, ability1.doSomething(BizScenario.ofBizId(ExtConstants.BIZ2, ExtConstants.SUB1)));
    }
}
