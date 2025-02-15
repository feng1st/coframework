package io.codeone.framework.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing common server-side errors.
 */
@RequiredArgsConstructor
@Getter
public enum ServerErrors implements ApiError {
    /**
     * The server has encountered a situation it does not know how to handle.
     */
    INTERNAL_SYS_ERROR(true, "Internal system error"),
    /**
     * This error response means that the server got an unexpected response from
     * a downstream system.
     */
    EXTERNAL_SYS_ERROR(true, "External system error"),
    /**
     * The server is not ready to handle the request. Common causes are a server
     * that is down for maintenance or that is overloaded.
     */
    SERVICE_UNAVAILABLE(false, "Service unavailable"),
    /**
     * This error response is given when the server cannot get a response from a
     * downstream system in time.
     */
    EXTERNAL_SYS_TIMEOUT(true, "External system timeout"),
    ;

    private final boolean critical;

    private final String message;

    /**
     * Retrieves the error code.
     *
     * @return the error code as a string
     */
    @Override
    public String getCode() {
        return name();
    }
}
