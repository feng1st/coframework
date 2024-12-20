package io.codeone.framework.api.plugin;

import io.codeone.framework.api.response.PageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExToPageResultApiPluginTest {

    @Autowired
    private ExToPageResultApiPluginTestService exToPageResultApiPluginTestService;

    @Test
    public void illegalArgumentException() {
        PageResult<Object> result = exToPageResultApiPluginTestService.illegalArgumentException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("INVALID_ARGS", result.getErrorCode());
        Assertions.assertEquals("Illegal argument", result.getErrorMessage());
    }

    @Test
    public void illegalStateException() {
        PageResult<Object> result = exToPageResultApiPluginTestService.illegalStateException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("IllegalStateException", result.getErrorCode());
        Assertions.assertEquals("Illegal state", result.getErrorMessage());
    }

    @Test
    public void apiExceptionCode() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiExceptionCode();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void apiExceptionCodeMessage() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiExceptionCodeMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiExceptionCodeCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiExceptionCodeCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void apiExceptionCodeMessageCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiExceptionCodeMessageCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void customErrorMessage() {
        PageResult<Object> result = exToPageResultApiPluginTestService.customErrorMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("IllegalStateException", result.getErrorCode());
        Assertions.assertEquals("Custom error message", result.getErrorMessage());
    }
}