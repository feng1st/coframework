package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.binding.PluginBindingProcessor;
import io.codeone.framework.plugin.function.Invokable;
import io.codeone.framework.plugin.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = ExtensionSession.class)
public class ExtensionSessionPlugin implements PluginBindingProcessor, Plugin {

    @Autowired
    private ExtensionSessionRepo extensionSessionRepo;

    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        extensionSessionRepo.buildParamIndex(method, session);
    }

    @Override
    public Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        ExtensionSession session = AnnotationUtils.getAnnotation(method, ExtensionSession.class);
        BizScenario bizScenario = extensionSessionRepo.resolveBizScenario(method, args, session);
        if (bizScenario == null) {
            return invokable.invoke();
        }
        return BizScenarioContext.invoke(bizScenario, invokable);
    }
}
