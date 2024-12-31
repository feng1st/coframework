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
    public void apiErrorCode() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorCode();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void apiErrorCodeMessage() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorCodeMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiErrorCodeCause() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorCodeCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("SERVICE_UNAVAILABLE", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void apiErrorCodeMessageCause() {
        Result<Object> result = exToResultApiPluginTestService.apiErrorCodeMessageCause();
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