package io.codeone.framework.common.log.config;

import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogFormatUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogConfigTest {

    @Autowired
    private LogConfig logConfig;

    @Test
    void setFormat() {
        String format = LogFormatUtils.format;

        logConfig.setFormat(LogFormat.CUSTOM);

        Assertions.assertEquals("custom", LogFormatUtils.format);

        LogFormatUtils.format = format;
    }
}