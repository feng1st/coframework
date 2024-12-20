package io.codeone.framework.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    public void successNull() {
        Result<Object> result = Result.success();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void successNonNull() {
        Result<Object> result = Result.success(0);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(0, result.getData());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
    }

    @Test
    public void failure() {
        Result<Object> result = Result.failure("CODE", "Message");
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals("CODE", result.getErrorCode());
        Assertions.assertEquals("Message", result.getErrorMessage());
    }
}