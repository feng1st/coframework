package io.codeone.framework.api.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiParamTest {

    @Autowired
    private ApiParamTestService apiParamTestService;

    @Test
    public void withUserId() {
        ApiParamTestParam param = new ApiParamTestParam();
        param.setUserId(0L);
        apiParamTestService.consume(param);
    }

    @Test
    public void withoutUserId() {
        ApiParamTestParam param = new ApiParamTestParam();
        Assertions.assertEquals("userId is required",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> apiParamTestService.consume(param)).getMessage());
    }
}