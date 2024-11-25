package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;

import java.util.Iterator;

public class BizScenarioIterator implements Iterator<BizScenario> {

    private final BizScenario bizScenario;

    private String bizId;

    private String scenario;

    public BizScenarioIterator(BizScenario bizScenario) {
        this.bizScenario = bizScenario;
        this.bizId = bizScenario.getBizId();
        this.scenario = bizScenario.getScenario();
    }

    @Override
    public boolean hasNext() {
        return hasNext(bizId) && hasNext(scenario);
    }

    @Override
    public BizScenario next() {
        BizScenario next = BizScenario.of(bizId, scenario);
        scenario = next(scenario);
        if (!hasNext(scenario)) {
            bizId = next(bizId);
            scenario = bizScenario.getScenario();
        }
        return next;
    }

    private boolean hasNext(String code) {
        return code != null;
    }

    private String next(String code) {
        if (BizScenarioUtils.ANY.equals(code)) {
            return null;
        }
        int i = code.lastIndexOf(BizScenarioUtils.SEPARATOR);
        if (i == -1) {
            return BizScenarioUtils.ANY;
        }
        return code.substring(0, i);
    }
}
