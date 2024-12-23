package io.codeone.framework.ext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioTest {

    @Test
    public void any() {
        Assertions.assertEquals("*|*", BizScenario.ofBizId().toString());
        Assertions.assertEquals("foo|*", BizScenario.ofBizId("foo").toString());
        Assertions.assertEquals("*|s", BizScenario.ofScenario("s").toString());
    }

    @Test
    public void prependScenario() {
        Assertions.assertEquals("foo|a",
                BizScenario.ofBizId("foo").prependScenario("a").toString());
        Assertions.assertEquals("foo|b.a",
                BizScenario.ofBizId("foo").prependScenario("a").prependScenario("b").toString());
    }

    @Test
    public void appendScenario() {
        Assertions.assertEquals("foo|a",
                BizScenario.ofBizId("foo").appendScenario("a").toString());
        Assertions.assertEquals("foo|a.b",
                BizScenario.ofBizId("foo").appendScenario("a").appendScenario("b").toString());
    }
}