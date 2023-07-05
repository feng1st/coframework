package io.codeone.framework.request;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Base class of all requests.
 * <p>
 * By default, it implemented ApiParam and BizScenarioParam. But they can be
 * safely ignored if not relevant.
 */
@Data
@Accessors(chain = true)
public abstract class BaseRequest implements ApiParam, BizScenarioParam {

    /**
     * The business identity of the caller, and in which scenario this
     * invocation would be. In case it will be handled differently.
     */
    private BizScenario bizScenario;

    public String getBizId() {
        return bizScenario == null ? null : bizScenario.getBizId();
    }

    public BaseRequest setBizId(String bizId) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofBizId(bizId);
        } else {
            bizScenario.setBizId(bizId);
        }
        return this;
    }

    public String getScenario() {
        return bizScenario == null ? null : bizScenario.getScenario();
    }

    public BaseRequest setScenario(String scenario) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofScenario(scenario);
        } else {
            bizScenario.setScenario(scenario);
        }
        return this;
    }
}
