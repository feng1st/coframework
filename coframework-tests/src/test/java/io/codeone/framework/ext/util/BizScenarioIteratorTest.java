package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BizScenarioIteratorTest {

    @Autowired
    private BizScenarioIteratorTestEp bizScenarioIteratorTestEp;

    @Autowired
    private BizScenarioIteratorTestNoDefaultEp bizScenarioIteratorTestNoDefaultEp;

    @Test
    public void foo() {
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo", "s1.a")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo", "s1.b")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo.baz", "s1.a")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo", "s1.a.i")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.ofBizId("foo", "baz").withScenario("s1", "a", "i")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestEp.execute(BizScenario.ofScenario("s1", "a", "i").withBizId("foo", "baz")));
    }

    @Test
    public void bar() {
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo.bar", "s2")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo.bar.baz", "s2")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo.bar", "s2.a")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestEp.execute(BizScenario.ofBizId("foo", "bar", "baz").withScenario("s2", "a")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestEp.execute(BizScenario.ofScenario("s2", "a").withBizId("foo", "bar", "baz")));
    }

    @Test
    public void defaultExt() {
        Assertions.assertEquals("default",
                bizScenarioIteratorTestEp.execute(BizScenario.ofBizId("foo")));
        Assertions.assertEquals("default",
                bizScenarioIteratorTestEp.execute(BizScenario.ofScenario("s2")));
        Assertions.assertEquals("default",
                bizScenarioIteratorTestEp.execute(BizScenario.of("foo", "s2")));
        Assertions.assertEquals("default",
                bizScenarioIteratorTestEp.execute(BizScenario.of("baz", "s3")));
    }

    @Test
    public void noDefault() {
        Assertions.assertEquals("No Extension found for Extensible interface \"io.codeone.framework.ext.util.BizScenarioIteratorTestNoDefaultEp\" and BizScenario \"bar|*\". Ensure an appropriate Extension is registered.",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> bizScenarioIteratorTestNoDefaultEp.execute(BizScenario.ofBizId("bar"))).getMessage());
    }
}