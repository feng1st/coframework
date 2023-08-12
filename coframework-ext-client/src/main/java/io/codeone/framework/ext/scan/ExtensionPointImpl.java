package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * This model is used to record all implementations of extension point methods
 * by the {@code BaseExtensionPointScanner} (in {@code coframework-ext}). You
 * can extend the {@code BaseExtensionPointScanner} to for example store and
 * index these models on the server side.
 */
@Getter
@EqualsAndHashCode
public class ExtensionPointImpl {

    /**
     * The code of the implemented extension point, which is equivalent to the
     * class name of the extension point interface.
     */
    private final String classCode;

    /**
     * The code of the implemented extension point method, which is composed of
     * <pre>{@code {classCode}.{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String methodCode;

    /**
     * The code of this implementation method, which is composed of
     * <pre>{@code {classCode}[{bizScenario}].{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String code;

    /**
     * The class name of the actual class that implemented the extension point
     * method. The class is the declaring class of the implemented method which
     * the extension class is using. And it could be the extension class itself,
     * or a superclass of it.
     *
     * @see AbilityImpl#getImplementingClass()
     */
    private final String implementingClass;

    /**
     * The code of the actual {@link BizScenario} the implemented method is
     * applied for. It is from the nearest {@code @Extension} of the implemented
     * method in the class hierarchy.
     *
     * @see AbilityImpl#getBizScenario()
     */
    private final String bizScenario;

    /**
     * Constructs an {@code ExtensionPointImpl} from the implemented extension
     * point method, the declaring class of that method, and the bizScenario the
     * method is applied for.
     *
     * @param method            an implementation of the extension point method
     * @param implementingClass the declaring class of the method
     * @param bizScenario       the bizScenario the method is applied for
     * @return constructed {@code ExtensionPointImpl}
     */
    public static ExtensionPointImpl of(Method method, Class<?> implementingClass, BizScenario bizScenario) {
        return new ExtensionPointImpl(method, implementingClass, bizScenario);
    }

    /**
     * Constructs an {@code ExtensionPointImpl} from the implemented extension
     * point method, the declaring class of that method, and the bizScenario the
     * method is applied for.
     *
     * @param method            an implementation of the extension point method
     * @param implementingClass the declaring class of the method
     * @param bizScenario       the bizScenario the method is applied for
     */
    public ExtensionPointImpl(Method method, Class<?> implementingClass, BizScenario bizScenario) {
        String classCode = ScanModelUtils.getClassKey(method);
        String methodKey = ScanModelUtils.getMethodKey(method);
        this.classCode = classCode;
        this.methodCode = classCode + "." + methodKey;
        this.code = classCode + "[" + bizScenario.getCode() + "]." + methodKey;
        this.implementingClass = ScanModelUtils.getClassKey(implementingClass);
        this.bizScenario = bizScenario.getCode();
    }

    @Override
    public String toString() {
        return code;
    }
}
