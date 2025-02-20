package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.stereotype.Component;

@Component("coframework")
public class ApiErrorConverterTestLegacyExceptionConverter
        implements ApiErrorConverter<ApiErrorConverterTestLegacyException> {

    @Override
    public ApiError convert(ApiErrorConverterTestLegacyException source) {
        return ApiError.of(source.getCode(), true, source.getMessage());
    }
}
