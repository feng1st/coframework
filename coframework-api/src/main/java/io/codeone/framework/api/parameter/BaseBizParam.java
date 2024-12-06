package io.codeone.framework.api.parameter;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for parameters that:
 * <ul>
 *   <li>Implements {@link ApiParam}, allowing automatic validation by the framework.
 *   <li>Implements {@link BizScenarioParam}, which can be used to route to a specific
 *   implementation of an {@code Extensible} interface when the {@code bizScenario}
 *   is present.
 * </ul>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseBizParam extends BaseParam implements BizScenarioParam {

    private BizScenario bizScenario;

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
