package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiException;
import org.springframework.stereotype.Component;

@Component
public class ApiExceptionConverterTestLegacyExceptionConverter
        implements ApiExceptionConverter<ApiExceptionConverterTestLegacyException> {

    @Override
    public ApiException convert(ApiExceptionConverterTestLegacyException source) {
        return source::getCode;
    }
}
