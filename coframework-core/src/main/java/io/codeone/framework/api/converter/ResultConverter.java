package io.codeone.framework.api.converter;

import io.codeone.framework.response.Result;
import org.springframework.core.convert.converter.Converter;

public interface ResultConverter<T>
        extends Converter<Result<?>, T> {
}
