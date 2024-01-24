package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.scan.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * This model is used to record all implementations of ability methods by the
 * {@code BaseAbilityScanner} (in {@code coframework-ext}). You can extend the
 * {@code BaseAbilityScanner} to for example store and index these models on the
 * server side.
 */
@Getter
@EqualsAndHashCode
public class AbilityImpl {

    /**
     * The code of the implemented ability, which is equivalent to the class
     * name of the ability interface.
     */
    private final String classCode;

    /**
     * The code of the implemented ability method, which is composed of
     * <pre>{@code {classCode}.{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String methodCode;

    /**
     * The code of this implementation method, which is composed of
     * <pre>{@code {classCode}[{bizScenario}].{methodName}({simpleClassNameOfParameter},...)}</pre>
     */
    private final String code;

    /**
     * The class name of the actual class that implemented the ability method.
     * The class is the declaring class of the implemented method which the
     * extension class is using. And it could be the extension class itself, or
     * a superclass of it. For example, {@code BaseSaveAbility} is the
     * {@code implementingClass} here:
     * <pre>{@code
     * @Ability
     * public interface SaveAbility {
     *     void save();
     * }
     *
     * // This is the actual class that implemented the ability method
     * public abstract class BaseSaveAbility implements SaveAbility {
     *     @Override
     *     public void save() {
     *     }
     * }
     *
     * // Did not implemented the ability method but inherited it from
     * // BaseSaveAbility
     * @Extension(bizId = "BIZ1")
     * public class Biz1SaveAbility extends BaseSaveAbility {
     * }
     * }</pre>
     */
    private final String implementingClass;

    /**
     * The code of the actual {@link BizScenario} the implemented method is
     * applied for. It is from the nearest {@code @Extension} of the implemented
     * method in the class hierarchy. For example, {@code (bizId = "BIZ1")} is
     * the {@code bizScenario} here:
     * <pre>{@code
     * @Ability
     * public interface SaveAbility {
     *     void save();
     * }
     *
     * public abstract class BaseSaveAbility implements SaveAbility {
     *     @Override
     *     public void save() {
     *     }
     * }
     *
     * // This is the nearest (and usually broader) bizScenario the implemented
     * // method is applied for
     * @Extension(bizId = "BIZ1")
     * public class Biz1SaveAbility extends BaseSaveAbility {
     * }
     *
     * // Not this one
     * @Extension(bizId = "BIZ1.TEAM_A")
     * public class Biz1TeamASaveAbility extends Biz1SaveAbility {
     * }
     * }</pre>
     */
    private final String bizScenario;

    /**
     * Constructs an {@code AbilityImpl} from the implemented ability method,
     * the declaring class of that method, and the bizScenario the method is
     * applied for.
     *
     * @param method            an implementation of the ability method
     * @param implementingClass the declaring class of the method
     * @param bizScenario       the bizScenario the method is applied for
     * @return constructed {@code AbilityImpl}
     */
    public static AbilityImpl of(Method method, Class<?> implementingClass, BizScenario bizScenario) {
        return new AbilityImpl(method, implementingClass, bizScenario);
    }

    /**
     * Constructs an {@code AbilityImpl} from the implemented ability method,
     * the declaring class of that method, and the bizScenario the method is
     * applied for.
     *
     * @param method            an implementation of the ability method
     * @param implementingClass the declaring class of the method
     * @param bizScenario       the bizScenario the method is applied for
     */
    public AbilityImpl(Method method, Class<?> implementingClass, BizScenario bizScenario) {
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
