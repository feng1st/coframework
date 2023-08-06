package io.codeone.framework.exception;

/**
 * The recommended interface of all custom exceptions and error enumerations.
 * The framework can recognize {@code ApiError}, and uses its properties in
 * logging and API result wrapping.
 *
 * <p>We suggest that all custom exceptions should extend the
 * {@link BaseException} which is an implementation of this interface. And using
 * enumerations to define custom errors, please refer to the
 * {@link CommonErrors} as an example.
 */
public interface ApiError {

    /**
     * The code to identify this particular error. It should be distinguishable
     * and easy to look up.
     *
     * <p>Those codes should be constants and be part of the API.
     *
     * @return the code of this custom exception
     */
    String getCode();

    /**
     * Specific details about this error. It should be human-readable since it
     * may be exposed to end users.
     *
     * @return the message of this custom exception
     */
    String getMessage();
}
