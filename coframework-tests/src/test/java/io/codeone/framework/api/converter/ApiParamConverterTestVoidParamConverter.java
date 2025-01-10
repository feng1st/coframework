package io.codeone.framework.api.converter;

import io.codeone.framework.api.parameter.ApiParam;
import org.springframework.stereotype.Component;

@Component
public class ApiParamConverterTestVoidParamConverter
        implements ApiParamConverter<ApiParamConverterTestVoidParam> {

    @Override
    public ApiParam convert(ApiParamConverterTestVoidParam source) {
        return null;
    }
}
