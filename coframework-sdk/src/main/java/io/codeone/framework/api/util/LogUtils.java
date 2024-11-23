package io.codeone.framework.api.util;

import io.codeone.framework.api.formatter.LogFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtils {

    public LogFormatter logFormatter;

    public Object format(Object context) {
        if (logFormatter != null) {
            return logFormatter.format(context);
        }
        return context;
    }
}
