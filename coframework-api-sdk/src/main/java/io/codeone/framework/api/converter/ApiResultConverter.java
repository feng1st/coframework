package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import org.springframework.core.convert.converter.Converter;

public interface ApiResultConverter<S> extends Converter<S, ApiResult<?>> {
}
