package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import org.springframework.stereotype.Component;

@Component
public class ApiResultConverterTestVoidResultConverter<T>
        implements ApiResultConverter<ApiResultConverterTestVoidResult<T>> {

    @Override
    public ApiResult<T> convert(ApiResultConverterTestVoidResult<T> source) {
        return null;
    }
}
