package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Description;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.core.annotation.Order;

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

    private final String name;

    private final String description;

    private final int order;

    public static AbilityMethod of(Description description, Order order, Method method) {
        return new AbilityMethod(description, order, method);
    }

    public AbilityMethod(Description description, Order order, Method method) {
        String classCode = ScanModelUtils.getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + ScanModelUtils.getMethodKey(method);
        this.name = ScanModelUtils.getName(description, method);
        this.description = ScanModelUtils.getDescription(description);
        this.order = ScanModelUtils.getOrder(order);
    }

    @Override
    public String toString() {
        return name;
    }
}
