package io.codeone.framework.api.converter;

import io.codeone.framework.api.parameter.ApiParam;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter interface for transforming legacy API parameters into an {@link ApiParam}.
 *
 * <p>Implementations of this interface can enhance existing APIs by converting
 * and validating parameters. This enables automatic activation via {@code @API}
 * annotations.
 *
 * @param <S> the type of the parameter
 */
public interface ApiParamConverter<S> extends Converter<S, ApiParam> {
}
