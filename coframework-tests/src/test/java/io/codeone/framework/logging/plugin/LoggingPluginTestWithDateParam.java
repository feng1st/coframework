package io.codeone.framework.logging.plugin;

import lombok.Data;

import java.util.Date;

@Data
public class LoggingPluginTestWithDateParam {

    private Date date = new Date(100000000L);
}
