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
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), "None accepted");
    }

    @Test
    void testWithCustomMessage() {
        Result<Void> result = testApiExToResultService.withCustomMessage();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), RuntimeException.class.getSimpleName());
        Assertions.assertEquals(result.getErrorMessage(), "What's wrong");
    }

    @Test
    void testApiError() {
        Result<Void> result = testApiExToResultService.apiError();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), CommonErrors.INVALID_PARAM.getMessage());
    }

    @Test
    void testInvalidParam() {
        Result<Void> result = testApiExToResultService.invalidParam();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), "Negative");
    }

    @Test
    void testOtherException() {
        Result<Void> result = testApiExToResultService.otherException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), Exception.class.getSimpleName());
        Assertions.assertEquals(result.getErrorMessage(), "Deeply sorry");
    }
}
