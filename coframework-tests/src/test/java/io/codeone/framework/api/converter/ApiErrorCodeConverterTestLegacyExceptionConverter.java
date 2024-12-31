package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ApiErrorCodeConverterTestLegacyExceptionConverter
        implements ApiErrorCodeConverter<ApiErrorCodeConverterTestLegacyException> {

    @Override
    public ApiErrorCode convert(ApiErrorCodeConverterTestLegacyException source) {
        return ApiErrorCode.of(source.getCode(), true);
    }
}
