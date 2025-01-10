package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts an {@link ApiError} into a failed result.
 *
 * @param <T> the type of the target result
 */
public interface FailureConverter<T> extends Converter<ApiError, T> {
}
