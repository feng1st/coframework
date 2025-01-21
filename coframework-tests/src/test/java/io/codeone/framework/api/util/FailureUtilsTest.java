package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ClientErrors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FailureUtilsTest {

    @Test
    void requireTrue() {
        Assertions.assertNull(FailureUtils.toFailure(ClientErrors.AUTH_FAILED, Object.class));
    }
}