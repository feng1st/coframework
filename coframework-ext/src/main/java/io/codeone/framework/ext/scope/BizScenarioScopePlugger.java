package io.codeone.framework.ext.scope;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Component
public class BizScenarioScopePlugger
        extends AnnotationMethodPlugger<BizScenarioScope> {

    @Resource
    private BizScenarioScopeIndexer bizScenarioScopeIndexer;

    @Resource
    private BizScenarioScopePlugin plugin;

    @Override
    protected Class<BizScenarioScope> getAnnotationType() {
        return BizScenarioScope.class;
    }

    @Override
    protected List<Plugin> getPlugins(Method method, BizScenarioScope annotation) {
        bizScenarioScopeIndexer.index(method, annotation);
        return Collections.singletonList(plugin);
    }
}
