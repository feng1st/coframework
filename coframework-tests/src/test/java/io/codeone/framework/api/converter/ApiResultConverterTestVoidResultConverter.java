package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import org.springframework.stereotype.Component;

@Component
public class ApiResultConverterTestVoidResultConverter
        implements ApiResultConverter<ApiResultConverterTestVoidResult<?>> {

    @Override
    public ApiResult<?> convert(ApiResultConverterTestVoidResult<?> source) {
        return null;
    }
}
