package io.codeone.framework.sdk.converter;

import io.codeone.framework.api.response.Result;
import org.springframework.core.convert.converter.Converter;

public interface ResultConverter<T>
        extends Converter<Result<?>, T> {
}
