package io.codeone.framework.ext.scope;

import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class BizScenarioScopePlugger
        extends AnnotationMethodPlugger<BizScenarioScope> {

    @Resource
    private BizScenarioScopeIndexer bizScenarioScopeIndexer;

    @Override
    protected Class<BizScenarioScope> getAnnotationType() {
        return BizScenarioScope.class;
    }

    @Override
    protected List<Plugging> getPluggingList(Method method, BizScenarioScope annotation) {
        bizScenarioScopeIndexer.index(method, annotation);
        return Plugging.asList(ClassPlugging.of(BizScenarioScopePlugin.class));
    }
}
