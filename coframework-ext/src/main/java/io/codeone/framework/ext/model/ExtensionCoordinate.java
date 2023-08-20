package io.codeone.framework.ext.model;

import io.codeone.framework.ext.BizScenario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * This is the key of the Extension implementation mapping, and is composed of
 * a target Extensible interface and a specific {@link BizScenario}. Any
 * {@code BizScenario} for a particular Extensible interface should have at most
 * one Extension implementation.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
@EqualsAndHashCode
public class ExtensionCoordinate {

    private final Class<?> extensibleClass;

    private final BizScenario bizScenario;

    /**
     * Returns the next broader {@code ExtensionCoordinate} while looking up
     * Extension implementation if current one could not find a match. The
     * {@code ExtensionCoordinate} is composed of the same Extensible interface
     * but the next broader {@link BizScenario}.
     *
     * @param bizScenario the next broader {@code BizScenario}
     * @return the next broader {@code ExtensionCoordinate}
     */
    public ExtensionCoordinate ofBizScenario(BizScenario bizScenario) {
        return new ExtensionCoordinate(this.extensibleClass, bizScenario);
    }

    @Override
    public String toString() {
        return extensibleClass.getSimpleName() + "[" + bizScenario + "]";
    }
}
