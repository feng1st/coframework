package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ClientErrors;
import io.codeone.framework.api.util.FailureUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FailureConverterTest {

    @Test
    void nullResult() {
        Assertions.assertNull(FailureUtils.toFailure(null, FailureConverterTestLegacyResult.class));
    }

    @Test
    void legacyResult() {
        FailureConverterTestLegacyResult result = FailureUtils.toFailure(ClientErrors.NOT_FOUND, FailureConverterTestLegacyResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("NOT_FOUND", result.getCode());
    }
}
