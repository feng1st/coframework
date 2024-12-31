package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ApiErrorCodeConverterTestVoidExceptionConverter
        implements ApiErrorCodeConverter<ApiErrorCodeConverterTestVoidException> {

    @Override
    public ApiErrorCode convert(ApiErrorCodeConverterTestVoidException source) {
        return null;
    }
}
