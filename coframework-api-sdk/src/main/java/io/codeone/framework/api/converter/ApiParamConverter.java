package io.codeone.framework.api.converter;

import io.codeone.framework.api.parameter.ApiParam;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a parameter into an {@link ApiParam}.
 *
 * @param <S> the type of the parameter
 */
public interface ApiParamConverter<S> extends Converter<S, ApiParam> {
}
