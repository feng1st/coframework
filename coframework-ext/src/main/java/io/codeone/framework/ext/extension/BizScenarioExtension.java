package io.codeone.framework.ext.extension;

import io.codeone.framework.ext.BizScenario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * This is the compound of an Extension implementation and the
 * {@link BizScenario} it implemented for. This model is used for caching and
 * monitoring.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
public class BizScenarioExtension {

    private final Object extension;

    private final BizScenario bizScenario;

    /**
     * Returns a not-found placeholder to be used by cache.
     *
     * @return a not-found placeholder
     */
    public static BizScenarioExtension ofEmpty() {
        return new BizScenarioExtension(null, null);
    }

    /**
     * Returns {@code true} if this instance is a not-found placeholder.
     *
     * @return {@code true} if this instance is a not-found placeholder
     */
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
