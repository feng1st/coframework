package io.codeone.framework.logging.plugin;

import lombok.Data;

import java.util.Date;

@Data
public class LoggingPluginTestWithDateParam {

    private Date date = new Date(1000L * 60 * 60 * 24);
}
