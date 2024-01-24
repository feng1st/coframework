package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.scan.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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

    /**
     * The name of this extension point method, which is
     * {@link ExtMethod#name()} if not null, or
     * {@code {simpleClassNameOfExtPtInterface}.{methodName}}.
     */
    private final String name;

    /**
     * The description of this extension point method, which is given in
     * {@link ExtMethod#description()}.
     */
    private final String description;

    /**
     * The virtual order of this extension point method, which is given in
     * {@link ExtMethod#order()}.
     */
    private final int order;

    /**
     * Constructs an {@code ExtensionPointMethod} from the extension point
     * method and its {@link ExtMethod} annotation.
     *
     * @param extMethod the {@code ExtMethod} annotation of the method
     * @param method    the extension point method
     * @return constructed {@code ExtensionPointMethod}
     */
    public static ExtensionPointMethod of(ExtMethod extMethod, Method method) {
        return new ExtensionPointMethod(extMethod, method);
    }

    /**
     * Constructs an {@code ExtensionPointMethod} from the extension point
     * method and its {@link ExtMethod} annotation.
     *
     * @param extMethod the {@code ExtMethod} annotation of the method
     * @param method    the extension point method
     */
    public ExtensionPointMethod(ExtMethod extMethod, Method method) {
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
