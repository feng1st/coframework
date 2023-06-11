package io.codeone.framework.ext.model;

import io.codeone.framework.ext.BizScenario;

import java.util.Objects;

public class ExtensionCoordinate {

    private final Class<?> extensibleClass;

    private final BizScenario bizScenario;

    public static ExtensionCoordinate of(Class<?> extensibleClass, BizScenario bizScenario) {
        return new ExtensionCoordinate(extensibleClass, bizScenario);
    }

    public ExtensionCoordinate ofBizScenario(BizScenario bizScenario) {
        return new ExtensionCoordinate(this.extensibleClass, bizScenario);
    }

    public ExtensionCoordinate(Class<?> extensibleClass, BizScenario bizScenario) {
        this.extensibleClass = extensibleClass;
        this.bizScenario = bizScenario;
    }

    public BizScenario getBizScenario() {
        return bizScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtensionCoordinate that = (ExtensionCoordinate) o;
        return Objects.equals(extensibleClass, that.extensibleClass) && Objects.equals(bizScenario, that.bizScenario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensibleClass, bizScenario);
    }

    @Override
    public String toString() {
        return extensibleClass.getSimpleName() + '@' + bizScenario;
    }
}
