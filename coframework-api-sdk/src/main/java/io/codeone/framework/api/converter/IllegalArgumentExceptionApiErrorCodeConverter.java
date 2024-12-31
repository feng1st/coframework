package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.ApiErrorCode;
import io.codeone.framework.api.exception.ClientErrorCodes;
import org.springframework.stereotype.Component;

/**
 * Converts an {@code IllegalArgumentException} to a predefined {@link ApiErrorCode}.
 */
@Component
public class IllegalArgumentExceptionApiErrorCodeConverter
        implements ApiErrorCodeConverter<IllegalArgumentException> {

    /**
     * Maps an {@code IllegalArgumentException} to {@link ClientErrorCodes#INVALID_ARGS}.
     *
     * @param source the {@code IllegalArgumentException} to convert
     * @return the {@code ApiErrorCode} representing invalid arguments
     */
    @Override
    public ApiErrorCode convert(IllegalArgumentException source) {
        return ClientErrorCodes.INVALID_ARGS;
    }
}
