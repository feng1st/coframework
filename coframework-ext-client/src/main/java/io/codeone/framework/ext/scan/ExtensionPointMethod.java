package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Description;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * This model is used to record all definitions of extension point methods by
 * the {@code BaseExtensionPointScanner} (in {@code coframework-ext}). You can
 * extend the {@code BaseExtensionPointScanner} to for example store and index
 * these models on the server side.
 */
@Getter
@EqualsAndHashCode
public class ExtensionPointMethod {

    /**
     * The code of the extension point, which is equivalent to the class name of
     * the extension point interface.
     */
    private final String classCode;

    /**
     * The code of this extension point method, which is composed of
     * <pre>{@code {classCode}.{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String code;

    private final String name;

    private final String description;

    private final int order;

    public static ExtensionPointMethod of(Description description, Order order, Method method) {
        return new ExtensionPointMethod(description, order, method);
    }

    public ExtensionPointMethod(Description description, Order order, Method method) {
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
