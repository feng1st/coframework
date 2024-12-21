package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiException;
import org.springframework.stereotype.Component;

@Component
public class ApiExceptionConverterTestVoidExceptionConverter
        implements ApiExceptionConverter<ApiExceptionConverterTestVoidException> {

    @Override
    public ApiException convert(ApiExceptionConverterTestVoidException source) {
        return null;
    }
}
