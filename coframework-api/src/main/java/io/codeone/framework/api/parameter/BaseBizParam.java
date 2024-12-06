package io.codeone.framework.api.parameter;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for parameters with business identity and scenario.
 *
 * <p>The associated {@link BizScenario} provides the identity used to route requests
 * to specific implementations of an {@code Extensible} interface.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseBizParam extends BaseParam implements BizScenarioParam {

    private BizScenario bizScenario;

    /**
     * Retrieves the business identity.
     *
     * @return the business identity
     */
    public String getBizId() {
        if (bizScenario == null) {
            return null;
        }
        return bizScenario.getBizId();
    }

    /**
     * Sets the business identity.
     *
     * @param bizId the business identity to set
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
