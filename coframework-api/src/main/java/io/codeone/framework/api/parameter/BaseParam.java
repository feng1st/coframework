package io.codeone.framework.api.parameter;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;

/**
 * Base class for parameters that:
 * <ul>
 *   <li>Implements {@link ApiParam}, allowing automatic validation by the framework.</li>
 *   <li>Implements {@link BizScenarioParam}, which can be used to route to a specific
 *   implementation of an {@code Extensible} interface when the {@code bizScenario}
 *   is present.</li>
 * </ul>
 */
@Data
public abstract class BaseParam implements ApiParam, BizScenarioParam {

    private BizScenario bizScenario;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
    }

    /**
     * Retrieves the business ID.
     *
     * @return the business ID
     */
    public String getBizId() {
        if (bizScenario == null) {
            return null;
        }
        return bizScenario.getBizId();
    }

    /**
     * Sets the business ID.
     *
     * @param bizId the business ID to set
     */
    public void setBizId(String bizId) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofBizId(bizId);
        } else {
            bizScenario.setBizId(bizId);
        }
    }

    /**
     * Retrieves the scenario.
     *
     * @return the scenario
     */
    public String getScenario() {
        if (bizScenario == null) {
            return null;
        }
        return bizScenario.getScenario();
    }

    /**
     * Sets the scenario.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(String scenario) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofScenario(scenario);
        } else {
            bizScenario.setScenario(scenario);
        }
    }
}
