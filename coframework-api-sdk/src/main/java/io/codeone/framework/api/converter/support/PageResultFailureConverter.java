package io.codeone.framework.api.converter.support;

import io.codeone.framework.api.converter.FailureConverter;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.response.PageResult;
import org.springframework.stereotype.Component;

/**
 * Converts {@link ApiError} to {@link PageResult} failures.
 */
@Component
public class PageResultFailureConverter implements FailureConverter<PageResult<?>> {

    /**
     * Converts {@link ApiError} to {@link PageResult}.
     *
     * @param source the error to convert
     * @return the failure result
     */
    @Override
    public PageResult<?> convert(ApiError source) {
        return PageResult.failure(source.getCode(), source.getMessage());
    }
}
