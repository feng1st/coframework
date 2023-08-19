package io.codeone.framework.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Provides some commonly used error enumerations that implemented the
 * {@link ApiError} interface. You can use them as parameters to construct an
 * instance of subclasses of the {@link BaseException}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CommonErrors implements ApiError {
    /**
     * Success
     */
    SUCCESS("Success"),
    /**
     * Invalid param
     */
    INVALID_PARAM("Invalid param"),
    /**
     * System error
     */
    SYS_ERROR("System error"),
    /**
     * Outer system error
     */
    OUTER_SYS_ERROR("Outer system error"),
    /**
     * Middleware error
     */
    MW_ERROR("Middleware error"),
    ;

    private final String message;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return name();
    }
}
