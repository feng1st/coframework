package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.api.util.ApiResultUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiResultConverterTest {

    @Test
    public void nullResult() {
        Assertions.assertNull(ApiResultUtils.toApiResult(null));
    }

    @Test
    public void result() {
        Result<?> result = Result.success();
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertEquals(result, apiResult);
    }

    @Test
    public void pageResultSuccess() {
        PageResult<?> result = PageResult.success(1, 20);
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertEquals(Result.success(Page.of(1, 20)), apiResult);
    }

    @Test
    public void pageResultFailure() {
        PageResult<?> result = PageResult.failure("CODE", "Message");
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertEquals(Result.failure("CODE", "Message"), apiResult);
    }

    @Test
    public void nonApiResult() {
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(new Object());
        Assertions.assertNull(apiResult);
    }

    @Test
    public void legacySuccResult() {
        ApiResultConverterTestLegacyResult<String> result = ApiResultConverterTestLegacyResult.succ("data");
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertTrue(apiResult.isSuccess());
        Assertions.assertEquals("data", apiResult.getData());
        Assertions.assertNull(apiResult.getErrorCode());
        Assertions.assertNull(apiResult.getErrorMessage());
    }

    @Test
    public void legacyFailResult() {
        ApiResultConverterTestLegacyResult<String> result = ApiResultConverterTestLegacyResult.fail("CODE", "Message");
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertFalse(apiResult.isSuccess());
        Assertions.assertNull(apiResult.getData());
        Assertions.assertEquals("CODE", apiResult.getErrorCode());
        Assertions.assertEquals("Message", apiResult.getErrorMessage());
    }

    @Test
    public void voidResult() {
        ApiResultConverterTestVoidResult<String> result = ApiResultConverterTestVoidResult.succ("data");
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Assertions.assertNull(apiResult);
    }
}