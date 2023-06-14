package io.codeone.framework.ext.model;

import io.codeone.framework.ext.BizScenario;

public class BizScenarioExtension {

    private final Object extension;

    private final BizScenario bizScenario;

    public static BizScenarioExtension of(Object extension, BizScenario bizScenario) {
        return new BizScenarioExtension(extension, bizScenario);
    }

    public static BizScenarioExtension ofEmpty() {
        return new BizScenarioExtension(null, null);
    }

    public BizScenarioExtension(Object extension, BizScenario bizScenario) {
        this.extension = extension;
        this.bizScenario = bizScenario;
    }

    public Object getExtension() {
        return extension;
    }

    public BizScenario getBizScenario() {
        return bizScenario;
    }

    public boolean isEmpty() {
        return extension == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "(empty)";
        }
        return extension.getClass().getSimpleName() + "<" + bizScenario + ">";
    }
}
