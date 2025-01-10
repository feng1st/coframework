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
    public void code() {
        PageResult<Object> result = exToPageResultApiPluginTestService.code();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void codeMessage() {
        PageResult<Object> result = exToPageResultApiPluginTestService.codeMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void codeCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.codeCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void codeMessageCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.codeMessageCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiError() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiError();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("INTERNAL_SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("Internal system error", result.getErrorMessage());
    }

    @Test
    public void apiErrorMessage() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiErrorMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("INTERNAL_SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("System error", result.getErrorMessage());
    }

    @Test
    public void apiErrorCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiErrorCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("INTERNAL_SYS_ERROR", result.getErrorCode());
        Assertions.assertEquals("java.lang.IllegalStateException: Illegal state", result.getErrorMessage());
    }

    @Test
    public void apiErrorMessageCause() {
        PageResult<Object> result = exToPageResultApiPluginTestService.apiErrorMessageCause();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("INTERNAL_SYS_ERROR", result.getErrorCode());
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