package io.codeone.framework.api.converter;

import io.codeone.framework.api.request.ApiParam;
import org.springframework.core.convert.converter.Converter;

public interface ApiParamConverter<T>
        extends Converter<T, ApiParam> {
}
