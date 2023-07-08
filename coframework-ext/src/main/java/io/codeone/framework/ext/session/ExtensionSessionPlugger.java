package io.codeone.framework.ext.session;

import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class ExtensionSessionPlugger
        extends AnnotationMethodPlugger<ExtensionSession> {

    @Resource
    private ExtensionSessionIndexer extensionSessionIndexer;

    @Override
    protected Class<ExtensionSession> getAnnotationType() {
        return ExtensionSession.class;
    }

    @Override
    protected List<Plugging> getPluggingList(Method method, ExtensionSession annotation) {
        // Finish the indexing of session resolvers before the evaluation of
        // plugging. This is a proper chance since the whole session resolving is
        // driven by plugging.
        extensionSessionIndexer.index(method, annotation);

        return Plugging.asList(ClassPlugging.of(ExtensionSessionPlugin.class));
    }
}
