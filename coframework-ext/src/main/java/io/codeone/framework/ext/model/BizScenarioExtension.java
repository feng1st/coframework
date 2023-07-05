package io.codeone.framework.ext.model;

import io.codeone.framework.ext.BizScenario;
import lombok.Data;

@Data(staticConstructor = "of")
public class BizScenarioExtension {

    private final Object extension;

    private final BizScenario bizScenario;

    public static BizScenarioExtension ofEmpty() {
        return new BizScenarioExtension(null, null);
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
