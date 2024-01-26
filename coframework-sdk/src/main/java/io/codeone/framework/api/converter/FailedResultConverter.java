package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiError;
import org.springframework.core.convert.converter.Converter;

public interface FailedResultConverter<T>
        extends Converter<ApiError, T> {
}
