package io.codeone.framework.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing common client-side error codes.
 */
@RequiredArgsConstructor
@Getter
public enum ClientErrorCodes implements ApiErrorCode {
    /**
     * The server cannot or will not process the request due to illegal arguments.
     */
    INVALID_ARGS(false),
    /**
     * The client must authenticate itself to get the requested response.
     */
    AUTH_FAILED(false),
    /**
     * The client does not have access rights to the content. Unlike {@link #AUTH_FAILED},
     * the client's identity is known to the server.
     */
    ACCESS_DENIED(false),
    /**
     * The server cannot find the requested resource. Servers may also send this
     * response instead of {@code ACCESS_DENIED} to hide the existence of a resource
     * from an unauthorized client.
     */
    NOT_FOUND(false),
    /**
     * This response is sent when a request conflicts with the current state of
     * the server.
     */
    INVALID_STATE(false),
    /**
     * The user has sent too many requests in a given amount of time (rate limiting).
     */
    TOO_MANY_REQUESTS(false),
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
