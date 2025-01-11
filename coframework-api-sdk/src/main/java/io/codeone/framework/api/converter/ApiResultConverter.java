package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter interface for transforming a source object into an {@link ApiResult}.
 *
 * <p>Implementations of this interface can adapt the return types of legacy APIs
 * to {@link ApiResult}, enabling accurate logging of success, error codes, and
 * error messages.
 *
 * @param <S> the type of the source object
 */
public interface ApiResultConverter<S> extends Converter<S, ApiResult<?>> {
}
