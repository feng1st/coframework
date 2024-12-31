package io.codeone.framework.shared;

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

public class BaseLoggingTest {

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private final Appender<ILoggingEvent> appender = Mockito.mock(Appender.class);

    @BeforeEach
    public void beforeEach() {
        logger.addAppender(appender);
    }

    @AfterEach
    public void afterEach() {
        logger.detachAppender(appender);
    }

    protected void assertLog(String loggerName, Level level, Class<? extends Throwable> throwableClass, String msg) {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender).doAppend(argument.capture());

        Assertions.assertEquals(loggerName, argument.getValue().getLoggerName());
        Assertions.assertEquals(level, argument.getValue().getLevel());
        Assertions.assertEquals(throwableClass == null ? null : throwableClass.getName(),
                argument.getValue().getThrowableProxy() == null ? null : argument.getValue().getThrowableProxy().getClassName());
        Assertions.assertEquals(msg, argument.getValue().getFormattedMessage()
                // JSON
                .replaceAll("\"elapsed\":\\d+", "\"elapsed\":0")
                // or toString
                .replaceAll("elapsed=\\d+", "elapsed=0"));
    }

    protected void assertLogs(String... msgs) {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender, Mockito.atLeast(msgs.length)).doAppend(argument.capture());

        Assertions.assertEquals(msgs.length, argument.getAllValues().size());
        for (int i = 0; i < msgs.length; i++) {
            Assertions.assertEquals(msgs[i],
                    argument.getAllValues().get(i).getFormattedMessage()
                            // JSON
                            .replaceAll("\"elapsed\":\\d+", "\"elapsed\":0")
                            // or toString
                            .replaceAll("elapsed=\\d+", "elapsed=0"));
        }
    }
}
