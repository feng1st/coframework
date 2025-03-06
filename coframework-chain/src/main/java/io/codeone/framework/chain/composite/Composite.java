package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;

import java.util.List;

/**
 * Represents a composite chainable unit that consists of multiple components.
 *
 * <p>Composite units execute their components as part of their chainable logic.
 */
public interface Composite {

    /**
     * Retrieves the components of this composite unit.
     *
     * @return a list of {@link Chainable} components
     */
    List<? extends Chainable> getComponents();
}
