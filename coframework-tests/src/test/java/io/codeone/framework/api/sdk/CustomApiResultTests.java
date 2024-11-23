package io.codeone.framework.api.sdk;

import io.codeone.framework.api.sdk.domain.model.NewCustomApiResult;
import io.codeone.framework.api.sdk.domain.service.TestCustomApiResultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomApiResultTests {

    @Autowired
    private TestCustomApiResultService testCustomApiResultService;

    @Test
    void testSuccess() {
        NewCustomApiResult<Integer> result = testCustomApiResultService.returnSuccess();
    }

    @Test
    void testFailed() {
        NewCustomApiResult<Integer> result = testCustomApiResultService.returnFailed();
    }
}
