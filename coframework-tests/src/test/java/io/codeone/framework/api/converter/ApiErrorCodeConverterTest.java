package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiErrorCode;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.util.ApiErrorCodeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiErrorCodeConverterTest {

    @Test
    public void nullCause() {
        Throwable cause = ApiErrorCodeUtils.getCause(null);
        Assertions.assertNull(cause);
    }

    @Test
    public void directCause() {
        Exception exception = new IllegalStateException();
        Throwable cause = ApiErrorCodeUtils.getCause(exception);
        Assertions.assertEquals(exception, cause);
    }

    @Test
    public void rootCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new IllegalStateException(e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertEquals(e0, cause);
        Assertions.assertNotEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void cyclicCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new IllegalStateException(e0);
        Exception e2 = new IllegalStateException(e1);
        e0.initCause(e2);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertEquals(e0, cause);
        Assertions.assertNotEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void illegalArgumentCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new IllegalArgumentException(e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void apiErrorCodeCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new ApiException("API_EXCEPTION", false, e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void apiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new ApiErrorCodeConverterTestLegacyException("LEGACY_EXCEPTION", e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void nullApiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new ApiErrorCodeConverterTestVoidException("VOID_EXCEPTION", e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiErrorCodeUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void nullApiErrorCode() {
        Assertions.assertNull(ApiErrorCodeUtils.toApiErrorCode(null));
    }

    @Test
    public void nonApiErrorCode() {
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(new IllegalStateException());
        Assertions.assertEquals("IllegalStateException", apiErrorCode.getCode());
        Assertions.assertTrue(apiErrorCode.isCritical());
    }

    @Test
    public void illegalArgument() {
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(new IllegalArgumentException());
        Assertions.assertEquals("INVALID_ARGS", apiErrorCode.getCode());
        Assertions.assertFalse(apiErrorCode.isCritical());
    }

    @Test
    public void apiErrorCode() {
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(new ApiException("API_EXCEPTION", false));
        Assertions.assertEquals("API_EXCEPTION", apiErrorCode.getCode());
        Assertions.assertFalse(apiErrorCode.isCritical());
    }

    @Test
    public void apiCompatibleException() {
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(new ApiErrorCodeConverterTestLegacyException("LEGACY_EXCEPTION"));
        Assertions.assertEquals("LEGACY_EXCEPTION", apiErrorCode.getCode());
        Assertions.assertTrue(apiErrorCode.isCritical());
    }

    @Test
    public void nullApiCompatibleException() {
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(new ApiErrorCodeConverterTestVoidException("VOID_EXCEPTION"));
        Assertions.assertEquals("ApiErrorCodeConverterTestVoidException", apiErrorCode.getCode());
        Assertions.assertTrue(apiErrorCode.isCritical());
    }
}