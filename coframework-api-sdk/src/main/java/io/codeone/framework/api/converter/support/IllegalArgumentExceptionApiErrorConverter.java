package io.codeone.framework.api.converter.support;

import io.codeone.framework.api.converter.ApiErrorConverter;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.ClientErrors;
import org.springframework.stereotype.Component;

/**
 * Converts an {@code IllegalArgumentException} to a predefined {@link ApiError}.
 */
@Component("coframeworkIllegalArgumentExceptionApiErrorConverter")
public class IllegalArgumentExceptionApiErrorConverter
        implements ApiErrorConverter<IllegalArgumentException> {

    /**
     * Maps an {@code IllegalArgumentException} to {@link ClientErrors#INVALID_ARGS}.
     *
     * @param source the {@code IllegalArgumentException} to convert
     * @return the {@code ApiError} representing invalid arguments
     */
    @Override
    public ApiError convert(IllegalArgumentException source) {
        return ApiError.of(ClientErrors.INVALID_ARGS.getCode(),
                ClientErrors.INVALID_ARGS.isCritical(),
                source.getMessage());
    }
}
