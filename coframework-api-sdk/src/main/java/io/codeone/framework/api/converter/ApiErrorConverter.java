package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter interface for transforming a throwable into an {@link ApiError}.
 *
 * <p>Implementations of this interface can map exceptions to {@link ApiError},
 * allowing for proper logging, recording error codes, and wrapping failure results.
 *
 * @param <S> the type of the source throwable
 */
public interface ApiErrorConverter<S extends Throwable> extends Converter<S, ApiError> {
}
