package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * This model is used to record all definitions of ability methods by the
 * {@code BaseAbilityScanner} (in {@code coframework-ext}). You can extend the
 * {@code BaseAbilityScanner} to for example store and index these models on the
 * server side.
 */
@Getter
@EqualsAndHashCode
public class AbilityMethod {

    /**
     * The code of the ability, which is equivalent to the class name of the
     * ability interface.
     */
    private final String classCode;

    /**
     * The code of this ability method, which is composed of
     * <pre>{@code {classCode}.{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String code;

    /**
     * The name of this ability method, which is {@link ExtMethod#name()} if not
     * null, or {@code {simpleClassNameOfAbilityInterface}.{methodName}}.
     */
    private final String name;

    /**
     * The description of this ability method, which is given in
     * {@link ExtMethod#description()}.
     */
    private final String description;

    /**
     * The virtual order of this ability method, which is given in
     * {@link ExtMethod#order()}.
     */
    private final int order;

    /**
     * Constructs an {@code AbilityMethod} from the ability method and its
     * {@link ExtMethod} annotation.
     *
     * @param extMethod the {@code ExtMethod} annotation of the method
     * @param method    the ability method
     * @return constructed {@code AbilityMethod}
     */
    public static AbilityMethod of(ExtMethod extMethod, Method method) {
        return new AbilityMethod(extMethod, method);
    }

    /**
     * Constructs an {@code AbilityMethod} from the ability method and its
     * {@link ExtMethod} annotation.
     *
     * @param extMethod the {@code ExtMethod} annotation of the method
     * @param method    the ability method
     */
    public AbilityMethod(ExtMethod extMethod, Method method) {
        String classCode = ScanModelUtils.getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + ScanModelUtils.getMethodKey(method);
        this.name = ScanModelUtils.getName(extMethod, method);
        this.description = ScanModelUtils.getDescription(extMethod);
        this.order = ScanModelUtils.getOrder(extMethod);
    }

    @Override
    public String toString() {
        return name;
    }
}
