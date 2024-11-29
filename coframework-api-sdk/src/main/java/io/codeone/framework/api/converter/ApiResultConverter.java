package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a source object into an {@link ApiResult}.
 *
 * @param <S> the type of the source object
 */
public interface ApiResultConverter<S> extends Converter<S, ApiResult<?>> {
}
