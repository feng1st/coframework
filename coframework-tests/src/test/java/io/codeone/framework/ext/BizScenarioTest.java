package io.codeone.framework.ext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioTest {

    @Test
    public void any() {
        Assertions.assertEquals("*|*", BizScenario.of().toString());
        Assertions.assertEquals("foo|*", BizScenario.ofBizId("foo").toString());
        Assertions.assertEquals("*|s", BizScenario.ofScenario("s").toString());
        Assertions.assertEquals("foo.bar|*", BizScenario.ofBizId(null, "foo", null, "bar", null).toString());
        Assertions.assertEquals("foo.bar|*", BizScenario.ofBizId("", "foo", "", "bar", "").toString());
        Assertions.assertEquals("*|s1.s2", BizScenario.ofScenario(null, "s1", null, "s2", null).toString());
        Assertions.assertEquals("*|s1.s2", BizScenario.ofScenario("", "s1", "", "s2", "").toString());
        BizScenario bizScenario;
        bizScenario = BizScenario.of();
        bizScenario.setBizId(null);
        bizScenario.setScenario(null);
        Assertions.assertEquals("*|*", bizScenario.toString());
        bizScenario = BizScenario.of();
        bizScenario.setBizId("");
        bizScenario.setScenario("");
        Assertions.assertEquals("*|*", bizScenario.toString());
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