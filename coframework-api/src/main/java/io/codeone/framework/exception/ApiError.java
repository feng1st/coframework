package io.codeone.framework.exception;

/**
 * Interface of all business error enumerations and all business runtime
 * exceptions.
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
