package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter interface for wrapping an {@link ApiError} into a legacy API response
 * model.
 *
 * <p>Implementations of this interface enable returning failure results instead
 * of throwing exceptions.
 *
 * @param <T> the type of the target result
 */
public interface FailureConverter<T> extends Converter<ApiError, T> {
}
