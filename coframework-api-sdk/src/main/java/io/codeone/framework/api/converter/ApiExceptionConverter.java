package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiException;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a throwable into an {@link ApiException}.
 *
 * @param <S> the type of the source throwable
 */
public interface ApiExceptionConverter<S extends Throwable> extends Converter<S, ApiException> {
}
