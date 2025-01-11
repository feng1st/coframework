package io.codeone.framework.logging.plugin;

import lombok.Data;

import java.time.Duration;

@Data
public class LoggingPluginTestWithDurationParam {

    private Duration duration = Duration.ofHours(1L);
}
