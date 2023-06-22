package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

public class BaseLogTests {

    private final Appender<ILoggingEvent> appender = Mockito.mock(Appender.class);

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @BeforeEach
    public void beforeEach() {
        logger.addAppender(appender);
    }

    @AfterEach
    public void afterEach() {
        logger.detachAppender(appender);
    }

    protected void assertLog(String loggerName, Level level, Class<? extends Throwable> errClass, String msg) {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender).doAppend(argument.capture());

        Assertions.assertEquals(loggerName, argument.getValue().getLoggerName());
        Assertions.assertEquals(level, argument.getValue().getLevel());
        if (errClass == null) {
            Assertions.assertNull(argument.getValue().getThrowableProxy());
        } else {
            Assertions.assertNotNull(argument.getValue().getThrowableProxy());
            Assertions.assertEquals(errClass.getName(), argument.getValue().getThrowableProxy().getClassName());
        }
        Assertions.assertEquals(msg, argument.getValue().getMessage().replaceAll("elapsed=>\\d+", "elapsed=>0"));
    }
}
