package io.codeone.framework.ext;

/**
 * Defines a contract for providing a {@link BizScenario} instance.
 *
 * <p>Classes implementing this interface allow the framework to extract a {@code
 * BizScenario} that can be used to locate a specific {@code Extension} implementation
 * of an {@code Extensible} interface.
 */
public interface BizScenarioParam {

    /**
     * Retrieves the {@code BizScenario} associated with this instance.
     *
     * @return the associated {@code BizScenario}
     */
    BizScenario getBizScenario();
}
