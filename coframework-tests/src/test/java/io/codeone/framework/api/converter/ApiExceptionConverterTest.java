package io.codeone.framework.api.converter;

import io.codeone.framework.api.shared.BizException;
import io.codeone.framework.api.util.ApiExceptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiExceptionConverterTest {

    @Test
    public void nullCause() {
        Throwable cause = ApiExceptionUtils.getCause(null);
        Assertions.assertNull(cause);
    }

    @Test
    public void directCause() {
        Exception exception = new IllegalStateException();
        Throwable cause = ApiExceptionUtils.getCause(exception);
        Assertions.assertEquals(exception, cause);
    }

    @Test
    public void rootCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new IllegalStateException(e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiExceptionUtils.getCause(e2);
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
        Throwable cause = ApiExceptionUtils.getCause(e2);
        Assertions.assertEquals(e0, cause);
        Assertions.assertNotEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void illegalArgumentCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new IllegalArgumentException(e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiExceptionUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void apiExceptionCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new BizException("API_EXCEPTION", e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiExceptionUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void apiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new ApiExceptionConverterTestLegacyException("LEGACY_EXCEPTION", e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiExceptionUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void nullApiCompatibleExceptionCause() {
        Exception e0 = new IllegalStateException();
        Exception e1 = new ApiExceptionConverterTestVoidException("VOID_EXCEPTION", e0);
        Exception e2 = new IllegalStateException(e1);
        Throwable cause = ApiExceptionUtils.getCause(e2);
        Assertions.assertNotEquals(e0, cause);
        Assertions.assertEquals(e1, cause);
        Assertions.assertNotEquals(e2, cause);
    }

    @Test
    public void nullCode() {
        Assertions.assertNull(ApiExceptionUtils.getCode(null));
    }

    @Test
    public void nonApiExceptionCode() {
        Assertions.assertEquals("IllegalStateException",
                ApiExceptionUtils.getCode(new IllegalStateException()));
    }

    @Test
    public void illegalArgumentCode() {
        Assertions.assertEquals("INVALID_ARGS",
                ApiExceptionUtils.getCode(new IllegalArgumentException()));
    }

    @Test
    public void apiExceptionCode() {
        Assertions.assertEquals("API_EXCEPTION",
                ApiExceptionUtils.getCode(new BizException("API_EXCEPTION")));
    }

    @Test
    public void apiCompatibleExceptionCode() {
        Assertions.assertEquals("LEGACY_EXCEPTION",
                ApiExceptionUtils.getCode(new ApiExceptionConverterTestLegacyException("LEGACY_EXCEPTION")));
    }

    @Test
    public void nullApiCompatibleExceptionCode() {
        Assertions.assertEquals("ApiExceptionConverterTestVoidException",
                ApiExceptionUtils.getCode(new ApiExceptionConverterTestVoidException("VOID_EXCEPTION")));
    }
}