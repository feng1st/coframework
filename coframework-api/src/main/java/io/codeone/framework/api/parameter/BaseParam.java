package io.codeone.framework.api.parameter;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Data;

@Data
public abstract class BaseParam implements ApiParam, BizScenarioParam {

    private BizScenario bizScenario;

    @Override
    public void validate() {
    }

    public String getBizId() {
        if (bizScenario == null) {
            return null;
        }
        return bizScenario.getBizId();
    }

    public void setBizId(String bizId) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofBizId(bizId);
        } else {
            bizScenario.setBizId(bizId);
        }
    }

    public String getScenario() {
        if (bizScenario == null) {
            return null;
        }
        return bizScenario.getScenario();
    }

    public void setScenario(String scenario) {
        if (bizScenario == null) {
            bizScenario = BizScenario.ofScenario(scenario);
        } else {
            bizScenario.setScenario(scenario);
        }
    }
}
