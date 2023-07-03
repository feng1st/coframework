package io.codeone.framework.logging.aop;

import io.codeone.framework.logging.Logging;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Component
public class LoggingPlugger extends AnnotationMethodPlugger<Logging> {

    @Resource
    private LoggingPlugin plugin;

    @Override
    protected Class<Logging> getAnnotationType() {
        return Logging.class;
    }

    @Override
    protected List<Plugin> getPlugins(Method method, Logging annotation) {
        return Collections.singletonList(plugin);
    }
}
