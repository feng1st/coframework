package io.codeone.framework.exception;

/**
 * Interface of all biz error enumerations and all biz runtime exceptions.
 */
public interface Error {

    /**
     * Error code.
     */
    String getCode();

    /**
     * Error message.
     */
    String getMessage();
}
