package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.ExtensionSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class ExtensionSessionParamParserTest {

    @Test
    public void first() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("first",
                Object.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("No parameter of type BizScenarioParam found in method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.first(Object, Object)\". Ensure the method has at least one parameter of this type.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void last() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("last",
                Object.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("No parameter of type BizScenarioParam found in method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.last(Object, Object)\". Ensure the method has at least one parameter of this type.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void specifiedNone() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedNone",
                BizScenarioParam.class, BizScenarioParam.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("No parameter annotated with @RouteBy found in method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.specifiedNone(BizScenarioParam, BizScenarioParam)\". Ensure the method has exactly one parameter annotated with @RouteBy.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void specifiedDuplicate() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedDuplicate",
                BizScenarioParam.class, BizScenarioParam.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("Duplicate @RouteBy annotations found in method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.specifiedDuplicate(BizScenarioParam, BizScenarioParam)\". Ensure the method has at most one parameter annotated with @RouteBy.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void specifiedInvalid() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("specifiedInvalid",
                BizScenarioParam.class, Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("Parameter annotated with @RouteBy in method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.specifiedInvalid(BizScenarioParam, Object)\" is not of type BizScenarioParam. Ensure the parameter type is BizScenarioParam.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }

    @Test
    public void customInvalid() throws Exception {
        Method method = ExtensionSessionParamParserTestInvalidService.class.getMethod("customInvalid",
                Object.class);
        ExtensionSession session = method.getAnnotation(ExtensionSession.class);
        Assertions.assertEquals("No custom BizScenarioResolver specified in @ExtensionSession for method \"io.codeone.framework.ext.session.ExtensionSessionParamParserTestInvalidService.customInvalid(Object)\". Ensure the annotation defines a valid resolver.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> ExtensionSessionParamParser.parseParamIndex(method, session)).getMessage());
    }
}