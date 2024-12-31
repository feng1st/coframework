package io.codeone.framework.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing common server-side error codes.
 */
@RequiredArgsConstructor
@Getter
public enum ServerErrorCodes implements ApiErrorCode {
    /**
     * The server has encountered a situation it does not know how to handle.
     */
    INTERNAL_SYS_ERROR(true),
    /**
     * This error response means that the server got an unexpected response from
     * a downstream system.
     */
    EXTERNAL_SYS_ERROR(true),
    /**
     * The server is not ready to handle the request. Common causes are a server
     * that is down for maintenance or that is overloaded.
     */
    SERVICE_UNAVAILABLE(false),
    /**
     * This error response is given when the server cannot get a response from a
     * downstream system in time.
     */
    EXTERNAL_SYS_TIMEOUT(true),
    ;

    private final boolean critical;

    /**
     * Retrieves the error code.
     *
     * @return the error code as a string
     */
    public String getCode() {
        return name();
    }
}
