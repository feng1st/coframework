package io.codeone.framework.logging.spel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoggingExpressionParserTest {

    @Test
    void arg() {
        LoggingExpressionParser parser = new LoggingExpressionParser(new Object[]{1, "2"}, null);
        Assertions.assertEquals(1, parser.evaluate("#a0"));
        Assertions.assertEquals("2", parser.evaluate("#a1"));
    }

    @Test
    void result() {
        LoggingExpressionParser parser = new LoggingExpressionParser(new Object[0], 1);
        Assertions.assertEquals(1, parser.evaluate("#r"));
    }

    @Test
    void nullArgs() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new LoggingExpressionParser(null, null));
    }

    @Test
    void nullResult() {
        LoggingExpressionParser parser = new LoggingExpressionParser(new Object[0], null);
        Assertions.assertNull(parser.evaluate("#r?.code"));
    }
}