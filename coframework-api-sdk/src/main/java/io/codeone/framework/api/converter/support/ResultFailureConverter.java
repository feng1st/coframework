package io.codeone.framework.api.converter.support;

import io.codeone.framework.api.converter.FailureConverter;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Component;

/**
 * Converts {@link ApiError} to {@link Result} failures.
 */
@Component("coframeworkResultFailureConverter")
public class ResultFailureConverter implements FailureConverter<Result<?>> {

    /**
     * Converts {@link ApiError} to {@link Result}.
     *
     * @param source the error to convert
     * @return the failure result
     */
    @Override
    public Result<?> convert(ApiError source) {
        return Result.failure(source.getCode(), source.getMessage());
    }
}
