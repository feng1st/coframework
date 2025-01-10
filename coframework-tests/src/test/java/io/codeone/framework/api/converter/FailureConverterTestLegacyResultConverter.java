package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.stereotype.Component;

@Component
public class FailureConverterTestLegacyResultConverter
        implements FailureConverter<FailureConverterTestLegacyResult> {

    @Override
    public FailureConverterTestLegacyResult convert(ApiError source) {
        return FailureConverterTestLegacyResult.of(source.getCode());
    }
}
