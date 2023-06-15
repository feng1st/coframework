package io.codeone.framework.exception;

/**
 * Some commonly used biz error enumerations.
 */
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

    CommonErrors(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
