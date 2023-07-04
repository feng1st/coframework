package io.codeone.framework.logging.aop;

import io.codeone.framework.logging.Logging;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class LoggingPlugger extends AnnotationMethodPlugger<Logging> {

    @Override
    protected Class<Logging> getAnnotationType() {
        return Logging.class;
    }

    @Override
    protected List<Plugging> getPluggingList(Method method, Logging annotation) {
        return Plugging.asList(ClassPlugging.of(LoggingPlugin.class));
    }
}
