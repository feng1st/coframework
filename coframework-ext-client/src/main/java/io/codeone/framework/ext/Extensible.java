package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * Extensible is used to mark an interface as an extensible interface, which
 * means it can be implemented differently for different BizScenarios.
 * <p>
 * Implementations of an extensible interface are Extensions, and should be
 * annotated by '@Extension'.
 *
 * @see Extension
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Extensible {
}
