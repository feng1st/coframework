package io.codeone.framework.api.parameter;

/**
 * Represents a parameter that requires validation.
 *
 * <p>Parameters are automatically validated by the framework if the associated
 * services or methods are annotated with {@code API}.
 */
public interface ApiParam {

    /**
     * Validates the parameter, throwing an {@code IllegalArgumentException} if
     * invalid.
     *
     * <p>Use this method to ensure parameters meet all necessary conditions before
     * processing.
     *
     * @see io.codeone.framework.api.util.Validator
     */
    void validate();
}
