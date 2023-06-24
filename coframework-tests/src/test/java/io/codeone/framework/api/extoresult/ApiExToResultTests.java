package io.codeone.framework.api.extoresult;

import io.codeone.framework.api.extoresult.domain.param.MyParam;
import io.codeone.framework.api.extoresult.domain.service.TestApiExToResultService;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiExToResultTests {

    @Resource
    private TestApiExToResultService testApiExToResultService;

    @Test
    void testCheckArgs() {
        Result<Void> result = testApiExToResultService.withCheckArgs(new MyParam());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonErrors.INVALID_PARAM.getCode(), result.getErrorCode());
        Assertions.assertEquals("None accepted", result.getErrorMessage());
    }

    @Test
    void testWithCustomMessage() {
        Result<Void> result = testApiExToResultService.withCustomMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(RuntimeException.class.getSimpleName(), result.getErrorCode());
        Assertions.assertEquals("What's wrong", result.getErrorMessage());
    }

    @Test
    void testApiError() {
        Result<Void> result = testApiExToResultService.apiError();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonErrors.INVALID_PARAM.getCode(), result.getErrorCode());
        Assertions.assertEquals(CommonErrors.INVALID_PARAM.getMessage(), result.getErrorMessage());
    }

    @Test
    void testInvalidParam() {
        Result<Void> result = testApiExToResultService.invalidParam();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonErrors.INVALID_PARAM.getCode(), result.getErrorCode());
        Assertions.assertEquals("Negative", result.getErrorMessage());
    }

    @Test
    void testOtherException() {
        Result<Void> result = testApiExToResultService.otherException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(Exception.class.getSimpleName(), result.getErrorCode());
        Assertions.assertEquals("Deeply sorry", result.getErrorMessage());
    }
}
