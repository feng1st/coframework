package io.codeone.framework.ext;

import io.codeone.framework.ext.util.BizScenarioIterator;
import io.codeone.framework.ext.util.BizScenarioUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.Transient;
import java.util.Iterator;

@Getter
@EqualsAndHashCode
public class BizScenario implements BizScenarioParam, Iterable<BizScenario> {

    private String bizId = BizScenarioUtils.ANY;

    private String scenario = BizScenarioUtils.ANY;

    public static BizScenario ofBizId(String... bizId) {
        return of(BizScenarioUtils.join(bizId), BizScenarioUtils.ANY);
    }

    public static BizScenario ofScenario(String... scenario) {
        return of(BizScenarioUtils.ANY, BizScenarioUtils.join(scenario));
    }

    public static BizScenario of(String[] bizId, String[] scenario) {
        return of(BizScenarioUtils.join(bizId), BizScenarioUtils.join(scenario));
    }

    public static BizScenario of(String[] bizId, String scenario) {
        return of(BizScenarioUtils.join(bizId), scenario);
    }

    public static BizScenario of(String bizId, String[] scenario) {
        return of(bizId, BizScenarioUtils.join(scenario));
    }

    public static BizScenario of(String bizId, String scenario) {
        BizScenario bizScenario = new BizScenario();
        bizScenario.setBizId(bizId);
        bizScenario.setScenario(scenario);
        return bizScenario;
    }

    public BizScenario withBizId(String... bizId) {
        return of(BizScenarioUtils.join(bizId), this.scenario);
    }

    public BizScenario withScenario(String... scenario) {
        return of(this.bizId, BizScenarioUtils.join(scenario));
    }

    public BizScenario prependScenario(String scenario) {
        return withScenario(scenario, this.scenario);
    }

    public BizScenario appendScenario(String scenario) {
        return of(this.scenario, scenario);
    }

    public void setBizId(String bizId) {
        BizScenarioUtils.validate(bizId);
        this.bizId = BizScenarioUtils.emptyToAny(bizId);
    }

    public void setScenario(String scenario) {
        BizScenarioUtils.validate(scenario);
        this.scenario = BizScenarioUtils.emptyToAny(scenario);
    }

    @Override
    public String toString() {
        return bizId + "|" + scenario;
    }

    @Override
    @Transient
    public BizScenario getBizScenario() {
        return this;
    }

    @Override
    public Iterator<BizScenario> iterator() {
        return new BizScenarioIterator(this);
    }
}
