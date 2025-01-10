package io.codeone.framework.api.converter;

import io.codeone.framework.api.parameter.ApiParam;
import io.codeone.framework.api.util.ApiParamUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiParamConverterTest {

    @Test
    void nullParam() {
        Assertions.assertNull(ApiParamUtils.toApiParam(null));
    }

    @Test
    void apiParam() {
        ApiParamConverterTestApiParam param = new ApiParamConverterTestApiParam();
        ApiParam apiParam = ApiParamUtils.toApiParam(param);
        Assertions.assertEquals(param, apiParam);
    }

    @Test
    void legacyParam() {
        ApiParamConverterTestLegacyParam param = new ApiParamConverterTestLegacyParam();
        ApiParam apiParam = ApiParamUtils.toApiParam(param);
        Assertions.assertNotNull(apiParam);
        Assertions.assertThrows(IllegalArgumentException.class, apiParam::validate);
    }

    @Test
    void voidParam() {
        ApiParamConverterTestVoidParam param = new ApiParamConverterTestVoidParam();
        Assertions.assertNull(ApiParamUtils.toApiParam(param));
    }
}
