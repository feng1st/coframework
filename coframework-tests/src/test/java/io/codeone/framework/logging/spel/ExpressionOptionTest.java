package io.codeone.framework.logging.spel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionOptionTest {

    @Test
    void nullParser() {
        ExpressionOption expressionOption = ExpressionOption.of(null);
        Assertions.assertFalse(expressionOption.supportSuccess());
        Assertions.assertFalse(expressionOption.supportCode());
        Assertions.assertFalse(expressionOption.supportMessage());
        Assertions.assertFalse(expressionOption.supportArgKvs());
        Assertions.assertNull(expressionOption.evaluateSuccess());
        Assertions.assertNull(expressionOption.evaluateCode());
        Assertions.assertNull(expressionOption.evaluateMessage());
        Assertions.assertNull(expressionOption.evaluateArgMap());
    }

    @Test
    void nullExp() {
        Object[] args = new Object[]{1, 2, 3};
        ExpressionOption expressionOption = ExpressionOption.of(new LoggingExpressionParser(args, null));
        Assertions.assertFalse(expressionOption.supportSuccess());
        Assertions.assertFalse(expressionOption.supportCode());
        Assertions.assertFalse(expressionOption.supportMessage());
        Assertions.assertFalse(expressionOption.supportArgKvs());
        Assertions.assertNull(expressionOption.evaluateSuccess());
        Assertions.assertNull(expressionOption.evaluateCode());
        Assertions.assertNull(expressionOption.evaluateMessage());
        Assertions.assertNull(expressionOption.evaluateArgMap());
    }

    @Test
    void emptyExp() {
        Object[] args = new Object[]{1, 2, 3};
        ExpressionOption expressionOption = ExpressionOption.of(new LoggingExpressionParser(args, null))
                .setExpSuccess("")
                .setExpCode("")
                .setExpMessage("")
                .setExpArgKvs(new String[0]);
        Assertions.assertFalse(expressionOption.supportSuccess());
        Assertions.assertFalse(expressionOption.supportCode());
        Assertions.assertFalse(expressionOption.supportMessage());
        Assertions.assertFalse(expressionOption.supportArgKvs());
        Assertions.assertNull(expressionOption.evaluateSuccess());
        Assertions.assertNull(expressionOption.evaluateCode());
        Assertions.assertNull(expressionOption.evaluateMessage());
        Assertions.assertNull(expressionOption.evaluateArgMap());
    }
}