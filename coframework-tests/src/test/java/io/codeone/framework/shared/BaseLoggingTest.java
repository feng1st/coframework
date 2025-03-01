package io.codeone.framework.shared;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import io.codeone.framework.common.function.VoidInvokable;
import io.codeone.framework.common.log.util.LogFormatUtils;
import lombok.SneakyThrows;
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

    @SneakyThrows
    protected void test(VoidInvokable invokable, String... formats) {
        String origFormat = LogFormatUtils.format;
        try {
            for (String format : formats) {
                LogFormatUtils.format = format;
                invokable.invoke();
            }
        } finally {
            LogFormatUtils.format = origFormat;
        }
    }

    protected void assertLog(int index, int total,
                             String loggerName, Level level, Class<? extends Throwable> throwableClass, String msg) {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender, Mockito.times(total)).doAppend(argument.capture());

        LoggingEvent loggingEvent = argument.getAllValues().get(index);

        Assertions.assertEquals(loggerName, loggingEvent.getLoggerName());
        Assertions.assertEquals(level, loggingEvent.getLevel());
        Assertions.assertEquals(throwableClass == null ? null : throwableClass.getName(),
                loggingEvent.getThrowableProxy() == null ? null : loggingEvent.getThrowableProxy().getClassName());
        Assertions.assertEquals(msg, loggingEvent.getFormattedMessage()
                // JSON
                .replaceAll("\"elapsed\":\\d+", "\"elapsed\":0")
                // logfmt
                .replaceAll("elapsed=\\d+", "elapsed=0")
                // custom
                .replaceAll("elapsed=>\\d+", "elapsed=>0"));
    }
}
