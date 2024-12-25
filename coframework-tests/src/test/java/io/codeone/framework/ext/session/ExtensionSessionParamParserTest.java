package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.ExtensionSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class ExtensionSessionParamParserTest {

    @Test
    public void last() throws Throwable {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("last",
                Object.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("No BizScenarioParam found in method 'public java.lang.Object io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.last(java.lang.Object,java.lang.Object)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void specifiedDuplicate() throws Throwable {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedDuplicate",
                BizScenarioParam.class, BizScenarioParam.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("Duplicate @RouteBy found in method 'public java.lang.Object io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.specifiedDuplicate(io.codeone.framework.ext.BizScenarioParam,io.codeone.framework.ext.BizScenarioParam)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void specifiedInvalid() throws Throwable {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedInvalid",
                BizScenarioParam.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("Parameter with @RouteBy in method 'public java.lang.Object io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.specifiedInvalid(io.codeone.framework.ext.BizScenarioParam,java.lang.Object)' is not BizScenarioParam",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }
}