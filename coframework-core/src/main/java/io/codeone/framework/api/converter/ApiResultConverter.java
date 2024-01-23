package io.codeone.framework.api.converter;

import io.codeone.framework.response.Result;
import org.springframework.core.convert.converter.Converter;

public interface ApiResultConverter<T>
        extends Converter<T, Result<?>> {
}
