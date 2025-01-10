package io.codeone.framework.api.converter;

import io.codeone.framework.api.parameter.ApiParam;
import org.springframework.stereotype.Component;

@Component
public class ApiParamConverterTestLegacyParamConverter
        implements ApiParamConverter<ApiParamConverterTestLegacyParam> {

    @Override
    public ApiParam convert(ApiParamConverterTestLegacyParam source) {
        return source::check;
    }
}
