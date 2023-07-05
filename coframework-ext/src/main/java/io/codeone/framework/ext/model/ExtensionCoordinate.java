package io.codeone.framework.ext.model;

import io.codeone.framework.ext.BizScenario;
import lombok.Data;

@Data(staticConstructor = "of")
public class ExtensionCoordinate {

    private final Class<?> extensibleClass;

    private final BizScenario bizScenario;

    public ExtensionCoordinate ofBizScenario(BizScenario bizScenario) {
        return new ExtensionCoordinate(this.extensibleClass, bizScenario);
    }

    @Override
    public String toString() {
        return extensibleClass.getSimpleName() + "[" + bizScenario + "]";
    }
}
