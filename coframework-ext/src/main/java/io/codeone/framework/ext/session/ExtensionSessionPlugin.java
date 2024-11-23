package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.PluginBindingProcessor;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.AnnotationUtils;
import io.codeone.framework.plugin.util.Invokable;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = ExtensionSession.class)
public class ExtensionSessionPlugin implements PluginBindingProcessor, Plugin {

    @Autowired
    private ExtensionSessionIndexer extensionSessionIndexer;

    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        extensionSessionIndexer.index(method, session);
    }

    @Override
    public Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        BizScenario bizScenario = extensionSessionIndexer.resolve(method, args, session);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, invokable);
    }
}
