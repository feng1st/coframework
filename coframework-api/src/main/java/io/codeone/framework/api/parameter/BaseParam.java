package io.codeone.framework.api.parameter;

import lombok.Data;

/**
 * Base class for API parameters requiring validation.
 *
 * <p>This class implements {@link ApiParam}, allowing the framework to perform
 * automatic validation on instances. Subclasses can inherit the validation behavior.
 */
@Data
public abstract class BaseParam implements ApiParam {

    /**
     * Validates the parameter. This method is a no-op by default and can be overridden
     * by subclasses.
     */
    @Override
    public void validate() {
    }
}
