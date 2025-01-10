package io.codeone.framework.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing common client-side errors.
 */
@RequiredArgsConstructor
@Getter
public enum ClientErrors implements ApiError {
    /**
     * The server cannot or will not process the request due to illegal arguments.
     */
    INVALID_ARGS(false, "Invalid arguments"),
    /**
     * The client must authenticate itself to get the requested response.
     */
    AUTH_FAILED(false, "Authentication failed"),
    /**
     * The client does not have access rights to the content. Unlike {@link #AUTH_FAILED},
     * the client's identity is known to the server.
     */
    ACCESS_DENIED(false, "Access denied"),
    /**
     * The server cannot find the requested content. Servers may also send this
     * response instead of {@code ACCESS_DENIED} to hide the existence of a content
     * from an unauthorized client.
     */
    NOT_FOUND(false, "Not found"),
    /**
     * This response is sent when a request conflicts with the current state of
     * the server.
     */
    INVALID_STATE(false, "Invalid state"),
    /**
     * The user has sent too many requests in a given amount of time (rate limiting).
     */
    TOO_MANY_REQUESTS(false, "Too many requests"),
    ;

    private final boolean critical;

    private final String message;

    /**
     * Retrieves the error code.
     *
     * @return the error code as a string
     */
    public String getCode() {
        return name();
    }
}
