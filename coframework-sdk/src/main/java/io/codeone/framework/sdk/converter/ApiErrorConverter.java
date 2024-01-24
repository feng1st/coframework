package io.codeone.framework.sdk.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

public interface ApiErrorConverter
        extends Converter<Throwable, ApiError> {
}
