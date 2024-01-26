package io.codeone.framework.api.support;

import io.codeone.framework.api.converter.FailedResultConverter;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Component;

@Component
public class DefaultFailedResultConverter implements FailedResultConverter<Result<?>> {

    @Override
    public Result<?> convert(ApiError source) {
        return Result.fail(source.getCode(), source.getMessage());
    }
}
