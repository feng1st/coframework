package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.stereotype.Component;

@Component
public class ApiErrorConverterTestVoidExceptionConverter
        implements ApiErrorConverter<ApiErrorConverterTestVoidException> {

    @Override
    public ApiError convert(ApiErrorConverterTestVoidException source) {
        return null;
    }
}
