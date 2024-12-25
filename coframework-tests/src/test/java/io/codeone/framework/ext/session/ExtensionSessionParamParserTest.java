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
        Assertions.assertThrows(IllegalStateException.class,
                () -> ExtensionSessionParamParser.parseParamIndex(method, session));
    }

    @Test
    public void specifiedDuplicate() throws Throwable {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedDuplicate",
                BizScenarioParam.class, BizScenarioParam.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertThrows(IllegalStateException.class,
                () -> ExtensionSessionParamParser.parseParamIndex(method, session));
    }

    @Test
    public void specifiedInvalid() throws Throwable {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedInvalid",
                BizScenarioParam.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertThrows(IllegalStateException.class,
                () -> ExtensionSessionParamParser.parseParamIndex(method, session));
    }
}