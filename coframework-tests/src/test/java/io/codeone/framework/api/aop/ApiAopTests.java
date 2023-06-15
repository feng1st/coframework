package io.codeone.framework.api.aop;

import io.codeone.framework.api.aop.domain.TestApiAopService;
import io.codeone.framework.api.aop.domain.param.Passenger;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiAopTests {

    @Resource
    private TestApiAopService testApiAopService;

    @Test
    void testFrodo() {
        // Frodo is a good lad
        Result<Void> result = testApiAopService.letThrough(new Passenger().setName("Frodo"));
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void testBalrog() {
        // Balrog is an ancient fire demon!
        Result<Void> result = testApiAopService.letThrough(new Passenger().setName("Balrog"));
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), "YOU SHALL NOT PASS!");
    }

    @Test
    void testApiErrorToResult() {
        Result<Void> result = testApiAopService.throwApiError();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.OUTER_SYS_ERROR.getCode());
        Assertions.assertEquals(result.getErrorMessage(), CommonErrors.OUTER_SYS_ERROR.getMessage());
    }

    @Test
    void testInvalidParamToResult() {
        Result<Void> result = testApiAopService.throwInvalidParam();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), "Nah");
    }

    @Test
    void testExToResult() {
        Result<Void> result = testApiAopService.throwException();
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(result.getErrorCode(), "Exception");
        Assertions.assertEquals(result.getErrorMessage(), "I'm deeply sorry");
    }
}
