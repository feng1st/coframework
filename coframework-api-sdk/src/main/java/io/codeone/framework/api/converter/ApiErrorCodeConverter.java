package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiErrorCode;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a throwable into an {@link ApiErrorCode}.
 *
 * @param <S> the type of the source throwable
 */
public interface ApiErrorCodeConverter<S extends Throwable> extends Converter<S, ApiErrorCode> {
}
