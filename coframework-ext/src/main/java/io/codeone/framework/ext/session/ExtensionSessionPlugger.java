package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Associates {@link ExtensionSessionPlugin} with {@link ExtensionSession}.
 */
@Component
public class ExtensionSessionPlugger
        extends AnnotationMethodPlugger<ExtensionSession> {

    @Resource
    private ExtensionSessionIndexer extensionSessionIndexer;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<ExtensionSession> getAnnotationType() {
        return ExtensionSession.class;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method is also used to compute and store the indexes of
     * {@link BizScenarioParam} parameters used for {@link ExtensionSession}
     * entry methods, before the {@link ExtensionSessionPlugin} takes effect.
     */
    @Override
    protected List<Plugging> getPluggingList(Method method, ExtensionSession annotation) {
        extensionSessionIndexer.index(method, annotation);

        return Collections.singletonList(ClassPlugging.of(ExtensionSessionPlugin.class));
    }
}
