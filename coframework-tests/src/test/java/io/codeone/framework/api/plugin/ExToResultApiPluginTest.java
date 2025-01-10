package io.codeone.framework.api.plugin;

import io.codeone.framework.api.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExToResultApiPluginTest {

    @Autowired
    private ExToResultApiPluginTestService exToResultApiPluginTestService;

    @Test
    public void illegalArgumentException() {
        Result<Object> result = exToResultApiPluginTestService.illegalArgumentException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("INVALID_ARGS", result.getErrorCode());
        Assertions.assertEquals("Illegal argument", result.getErrorMessage());
    }

    @Test
    public void illegalStateException() {
        Result<Object> result = exToResultApiPluginTestService.illegalStateException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("IllegalStateException", result.getErrorCode());
        Assertions.assertEquals("Illegal state", result.getErrorMessage());
    }

    @Test
    public void code() {
        Result<Object> result = exToResultApiPluginTestService.code();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void codeMessage() {
        Result<Object> result = exToResultApiPluginTestService.codeMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void codeCause() {
        Result<Object> result = exToResultApiPluginTestService.codeCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void codeMessageCause() {
        Result<Object> result = exToResultApiPluginTestService.codeMessageCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiError() {
        Result<Object> result = exToResultApiPluginTestService.apiError();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("Service unavailable", result.getErrorMessage());
    }

    @Test
    public void apiErrorMessage() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiErrorCause() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void apiErrorMessageCause() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorMessageCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void customErrorMessage() {
        Result<Object> result = exToResultApiPluginTestService.customErrorMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("IllegalStateException", result.getErrorCode());
        Assertions.assertEquals("Custom error message", result.getErrorMessage());
    }
}