package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExtensionSessionTest {

    @Autowired
    private ExtensionSessionTestService extensionSessionTestService;

    @Test
    public void autoFirst() {
        Assertions.assertEquals("foo",
                extensionSessionTestService.autoFirst(BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar")));
    }

    @Test
    public void autoSpecified() {
        Assertions.assertEquals("bar",
                extensionSessionTestService.autoSpecified(BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar")));
    }

    @Test
    public void autoCustom() {
        Assertions.assertEquals("bar",
                extensionSessionTestService.autoCustom("bar"));
    }

    @Test
    public void autoIgnore() {
        Assertions.assertEquals("ignore",
                extensionSessionTestService.autoIgnore("foo"));
    }

    @Test
    public void first() {
        Assertions.assertEquals("foo",
                extensionSessionTestService.first(1,
                        BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar")));
    }

    @Test
    public void last() {
        Assertions.assertEquals("bar",
                extensionSessionTestService.last(BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar"),
                        3));
    }

    @Test
    public void specified() {
        Assertions.assertEquals("bar",
                extensionSessionTestService.specified(BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar")));
    }

    @Test
    public void custom() {
        Assertions.assertEquals("foo",
                extensionSessionTestService.custom("foo"));
    }

    @Test
    public void customException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> extensionSessionTestService.customException("foo"));
    }

    @Test
    public void ignore() {
        Assertions.assertEquals("ignore",
                extensionSessionTestService.ignore("bar"));
    }

    @Test
    public void nullBizScenario() {
        Assertions.assertEquals("BizScenario is null for parameter 1 in method \"io.codeone.framework.ext.session.ExtensionSessionTestService.first(Object, BizScenarioParam, BizScenarioParam)\". Ensure the parameter is correctly initialized with a valid BizScenario.",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> extensionSessionTestService.first(1,
                                null,
                                BizScenario.ofBizId("bar"))).getMessage());
        Assertions.assertEquals("BizScenario is null for parameter 1 in method \"io.codeone.framework.ext.session.ExtensionSessionTestService.first(Object, BizScenarioParam, BizScenarioParam)\". Ensure the parameter is correctly initialized with a valid BizScenario.",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> extensionSessionTestService.first(1,
                                () -> null,
                                BizScenario.ofBizId("bar"))).getMessage());
        Assertions.assertEquals("BizScenario could not be resolved using the custom resolver \"io.codeone.framework.ext.session.ExtensionSessionTestResolver\" for method \"io.codeone.framework.ext.session.ExtensionSessionTestService.custom(Object)\". Ensure the resolver is correctly implemented and can resolve a valid BizScenario.",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> extensionSessionTestService.custom(1)).getMessage());
    }

    @Test
    public void annoRouting() {
        Assertions.assertEquals("foo",
                extensionSessionTestService.annoRouting(1,
                        BizScenario.ofBizId("foo"),
                        BizScenario.ofBizId("bar"),
                        4));
    }

    @Test
    public void annoNoRouting() {
        Assertions.assertEquals("ignore",
                extensionSessionTestService.annoNoRouting(1,
                        null,
                        null,
                        4));
        Assertions.assertEquals("ignore",
                extensionSessionTestService.annoNoRouting(1,
                        () -> null,
                        () -> null,
                        4));
    }
}
