package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import io.codeone.framework.logging.domain.TestLoggingService;
import io.codeone.framework.logging.domain.param.Passenger;
import io.codeone.framework.response.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LoggingTests {

    @Resource
    private TestLoggingService testLoggingService;

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

    private void assertLog(String loggerName, Level level, String msg) {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender).doAppend(argument.capture());

        Assertions.assertEquals(loggerName, argument.getValue().getLoggerName());
        Assertions.assertEquals(level, argument.getValue().getLevel());
        Assertions.assertEquals(msg, argument.getValue().getMessage().replaceAll("elapsed=>\\d+", "elapsed=>0"));
    }

    @Test
    void testFrodo() {
        // Frodo is a good lad
        Result<Void> result = testLoggingService.letThrough(new Passenger().setName("Frodo"));
        assertLog("io.codeone.framework.logging.domain.TestLoggingService", Level.INFO,
                "||level=>INFO||method=>TestLoggingService.letThrough||success=>true||elapsed=>0||arg.passenger=>Passenger{name='Frodo'} BaseRequest{bizScenario=null}");
    }

    @Test
    void testBalrog() {
        // Balrog is an ancient fire demon!
        Result<Void> result = testLoggingService.letThrough(new Passenger().setName("Balrog"));
        assertLog("io.codeone.framework.logging.domain.TestLoggingService", Level.ERROR,
                "||level=>ERROR||method=>TestLoggingService.letThrough||success=>false||code=>INVALID_PARAM||message=>YOU SHALL NOT PASS!||elapsed=>0||arg.passenger=>Passenger{name='Balrog'} BaseRequest{bizScenario=null}");
    }

    @Test
    void testApiErrorToResult() {
        Result<Void> result = testLoggingService.throwApiError();
        assertLog("io.codeone.framework.logging.domain.TestLoggingService", Level.ERROR,
                "||level=>ERROR||method=>TestLoggingService.throwApiError||success=>false||code=>OUTER_SYS_ERROR||message=>Outer system error||elapsed=>0");
    }

    @Test
    void testInvalidParamToResult() {
        Result<Void> result = testLoggingService.throwInvalidParam();
        assertLog("io.codeone.framework.logging.domain.TestLoggingService", Level.ERROR,
                "||level=>ERROR||method=>TestLoggingService.throwInvalidParam||success=>false||code=>INVALID_PARAM||message=>Nah||elapsed=>0");
    }

    @Test
    void testExToResult() {
        Result<Void> result = testLoggingService.throwException();
        assertLog("io.codeone.framework.logging.domain.TestLoggingService", Level.ERROR,
                "||level=>ERROR||method=>TestLoggingService.throwException||success=>false||code=>Exception||message=>I'm deeply sorry||elapsed=>0");
    }
}
