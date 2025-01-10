package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.util.ApiErrorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiErrorConverterTest {

    @Test
    public void nullCause() {
        ApiError cause = ApiErrorUtils.getCause(null);
        Assertions.assertNull(cause);
    }

    @Test
    public void directCause() {
        Exception exception = new IllegalStateException();
        ApiError cause = ApiErrorUtils.getCause(exception);
        Assertions.assertEquals("IllegalStateException", cause.getCode());
        Assertions.assertTrue(cause.isCritical());
        Assertions.assertNull(cause.getMessage());
    }

    @Test
    public void rootCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new IllegalStateException("e1", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("IllegalStateException", cause.getCode());
        Assertions.assertTrue(cause.isCritical());
        Assertions.assertEquals("e0", cause.getMessage());
    }

    @Test
    public void cyclicCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new IllegalStateException("e1", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        e0.initCause(e2);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("IllegalStateException", cause.getCode());
        Assertions.assertTrue(cause.isCritical());
        Assertions.assertEquals("e0", cause.getMessage());
    }

    @Test
    public void illegalArgumentCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new IllegalArgumentException("e1", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("INVALID_ARGS", cause.getCode());
        Assertions.assertFalse(cause.isCritical());
        Assertions.assertEquals("e1", cause.getMessage());
    }

    @Test
    public void apiErrorCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new ApiException("API_EXCEPTION", false, "e1", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("API_EXCEPTION", cause.getCode());
        Assertions.assertFalse(cause.isCritical());
        Assertions.assertEquals("e1", cause.getMessage());
    }

    @Test
    public void apiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new ApiErrorConverterTestLegacyException("LEGACY_EXCEPTION", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("LEGACY_EXCEPTION", cause.getCode());
        Assertions.assertTrue(cause.isCritical());
        Assertions.assertEquals("java.lang.IllegalStateException: e0", cause.getMessage());
    }

    @Test
    public void nullApiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException("e0");
        Exception e1 = new ApiErrorConverterTestVoidException("VOID_EXCEPTION", e0);
        Exception e2 = new IllegalStateException("e2", e1);
        ApiError cause = ApiErrorUtils.getCause(e2);
        Assertions.assertEquals("IllegalStateException", cause.getCode());
        Assertions.assertTrue(cause.isCritical());
        Assertions.assertEquals("e0", cause.getMessage());
    }

    @Test
    public void nullApiError() {
        Assertions.assertNull(ApiErrorUtils.getCause(null));
    }

    @Test
    public void nonApiError() {
        ApiError apiError = ApiErrorUtils.getCause(new IllegalStateException());
        Assertions.assertEquals("IllegalStateException", apiError.getCode());
        Assertions.assertTrue(apiError.isCritical());
    }

    @Test
    public void illegalArgument() {
        ApiError apiError = ApiErrorUtils.getCause(new IllegalArgumentException());
        Assertions.assertEquals("INVALID_ARGS", apiError.getCode());
        Assertions.assertFalse(apiError.isCritical());
    }

    @Test
    public void apiError() {
        ApiError apiError = ApiErrorUtils.getCause(new ApiException("API_EXCEPTION", false));
        Assertions.assertEquals("API_EXCEPTION", apiError.getCode());
        Assertions.assertFalse(apiError.isCritical());
    }

    @Test
    public void apiCompatibleException() {
        ApiError apiError = ApiErrorUtils.getCause(new ApiErrorConverterTestLegacyException("LEGACY_EXCEPTION"));
        Assertions.assertEquals("LEGACY_EXCEPTION", apiError.getCode());
        Assertions.assertTrue(apiError.isCritical());
    }

    @Test
    public void nullApiCompatibleException() {
        ApiError apiError = ApiErrorUtils.getCause(new ApiErrorConverterTestVoidException("VOID_EXCEPTION"));
        Assertions.assertEquals("ApiErrorConverterTestVoidException", apiError.getCode());
        Assertions.assertTrue(apiError.isCritical());
    }
}