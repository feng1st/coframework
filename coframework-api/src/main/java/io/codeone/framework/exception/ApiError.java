package io.codeone.framework.exception;

/**
 * Interface of all custom exceptions and error enumerations.
 * <p>
 * ApiErrors will be recognized by the framework, and their properties will be
 * used in logging and API result wrapping.
 */
public interface ApiError {

    /**
     * The code to identify a particular error. It should be distinguishable
     * and easy to look up.
     * <p>
     * Those codes should be seen as constants and be part of the API.
     */
    String getCode();

    /**
     * Specific details about the error. It should be human-readable since it
     * may be exposed to customers.
     */
    String getMessage();
}
