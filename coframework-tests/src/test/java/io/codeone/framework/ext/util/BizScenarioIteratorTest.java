package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BizScenarioIteratorTest {

    @Autowired
    private BizScenarioIteratorTestExtensionPoint bizScenarioIteratorTestExtensionPoint;

    @Test
    public void foo() {
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo", "s1.a")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo", "s1.b")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo.baz", "s1.a")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo", "s1.a.i")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.ofBizId("foo", "baz").withScenario("s1", "a", "i")));
        Assertions.assertEquals("foo",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.ofScenario("s1", "a", "i").withBizId("foo", "baz")));
    }

    @Test
    public void bar() {
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo.bar", "s2")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo.bar.baz", "s2")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.of("foo.bar", "s2.a")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.ofBizId("foo", "bar", "baz").withScenario("s2", "a")));
        Assertions.assertEquals("bar",
                bizScenarioIteratorTestExtensionPoint.execute(BizScenario.ofScenario("s2", "a").withBizId("foo", "bar", "baz")));
    }
}