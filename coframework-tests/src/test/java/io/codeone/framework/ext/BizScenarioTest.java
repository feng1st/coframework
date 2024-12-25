package io.codeone.framework.ext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioTest {

    @Test
    public void any() {
        Assertions.assertEquals("*|*", BizScenario.of().toString());
        Assertions.assertEquals("*|*", BizScenario.ofBizId().toString());
        Assertions.assertEquals("*|*", BizScenario.ofScenario().toString());
        Assertions.assertEquals("*|*", BizScenario.of().setBizId(null).setScenario(null).toString());
        Assertions.assertEquals("*|*", BizScenario.of().setBizId("").setScenario("").toString());
        Assertions.assertEquals("foo|*", BizScenario.ofBizId("foo").toString());
        Assertions.assertEquals("*|s", BizScenario.ofScenario("s").toString());
        Assertions.assertEquals("foo.bar|*", BizScenario.ofBizId(null, "foo", null, "bar", null).toString());
        Assertions.assertEquals("foo.bar|*", BizScenario.ofBizId("", "foo", "", "bar", "").toString());
        Assertions.assertEquals("*|s1.s2", BizScenario.ofScenario(null, "s1", null, "s2", null).toString());
        Assertions.assertEquals("*|s1.s2", BizScenario.ofScenario("", "s1", "", "s2", "").toString());
    }

    @Test
    public void invalid() {
        Assertions.assertEquals("Invalid code '#', should be '*', or '.' separated alphabets, numbers, '-' and '_'",
                Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofBizId("#")).getMessage());
        Assertions.assertEquals("Invalid code '#', should be '*', or '.' separated alphabets, numbers, '-' and '_'",
                Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofScenario("#")).getMessage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofBizId(".foo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofBizId("foo."));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofBizId("foo.#"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofScenario(".s"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofScenario("s."));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofScenario("s.#"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BizScenario.ofBizId("汉字"));
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