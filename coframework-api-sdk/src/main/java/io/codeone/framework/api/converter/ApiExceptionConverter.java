package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiException;
import org.springframework.core.convert.converter.Converter;

public interface ApiExceptionConverter<S extends Throwable> extends Converter<S, ApiException> {
}
