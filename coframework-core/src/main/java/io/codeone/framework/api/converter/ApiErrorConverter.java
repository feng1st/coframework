package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

public interface ApiErrorConverter
        extends Converter<Throwable, ApiError> {
}
