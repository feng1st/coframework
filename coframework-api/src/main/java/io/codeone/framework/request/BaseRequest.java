package io.codeone.framework.request;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Base class of all requests.
 *
 * <p>By default, it implemented {@link ApiParam} and {@link BizScenarioParam}.
 * But they can be safely ignored if not relevant.
 */
@Data
@Accessors(chain = true)
public abstract class BaseRequest implements ApiParam, BizScenarioParam {

    /**
     * The business identity of the caller, and in which scenario this
     * invocation would be. In case the invocation will be handled differently.
     */
    private BizScenario bizScenario;

    /**
     * Returns the business identity of the caller, which is extracted from the
     * {@code bizScenario} property if it is not null, otherwise null.
     *
     * @return the business identity of the caller
     */
    public String getBizId() {
        return bizScenario == null ? null : bizScenario.getBizId();
    }

    /**
     * Sets the business identity of the caller. It will update the
     * {@code bizId} of the {@code bizScenario} property if it is not null,
     * otherwise it will create a new {@code bizScenario} property with the
     * given {@code bizId}.
     *
     * @param bizId the business identity of the caller
     * @return itself (chaining)
     */
    public BaseRequest setBizId(String bizId) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofBizId(bizId);
        } else {
            bizScenario.setBizId(bizId);
        }
        return this;
    }

    /**
     * Returns the scenario of this invocation, which is extracted from the
     * {@code bizScenario} property if it is not null, otherwise null.
     *
     * @return the scenario of this invocation
     */
    public String getScenario() {
        return bizScenario == null ? null : bizScenario.getScenario();
    }

    /**
     * Sets the scenario of this invocation. It will update the {@code scenario}
     * of the {@code bizScenario} property if it is not null, otherwise it will
     * create a new {@code bizScenario} property with the given
     * {@code scenario}.
     *
     * @param scenario the scenario of this invocation
     * @return itself (chaining)
     */
    public BaseRequest setScenario(String scenario) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofScenario(scenario);
        } else {
            bizScenario.setScenario(scenario);
        }
        return this;
    }
}
