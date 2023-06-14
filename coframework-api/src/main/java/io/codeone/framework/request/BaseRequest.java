package io.codeone.framework.request;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;

import java.util.Objects;

/**
 * Base class of all Requests.
 * <p>
 * By default, it implemented ApiParam and BizScenarioParam. But they can be safely ignored if not relevant.
 */
public abstract class BaseRequest implements ApiParam, BizScenarioParam {

    private BizScenario bizScenario;

    @Override
    public BizScenario getBizScenario() {
        return bizScenario;
    }

    public BaseRequest setBizScenario(BizScenario bizScenario) {
        this.bizScenario = bizScenario;
        return this;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseRequest that = (BaseRequest) o;
        return Objects.equals(bizScenario, that.bizScenario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bizScenario);
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "bizScenario=" + bizScenario +
                '}';
    }
}
