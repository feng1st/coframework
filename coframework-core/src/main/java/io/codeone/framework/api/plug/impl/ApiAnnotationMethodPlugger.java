package io.codeone.framework.api.plug.impl;

import io.codeone.framework.api.API;
import io.codeone.framework.api.factory.ApiPluginFactory;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class ApiAnnotationMethodPlugger
        extends AnnotationMethodPlugger<API> {

    @Resource
    private ApiPluginFactory apiPluginFactory;

    @Override
    protected Class<API> getAnnotationType() {
        return API.class;
    }

    @Override
    protected List<Plugin<?>> getPlugins(Method method, API annotation) {
        return apiPluginFactory.getPlugins();
    }
}
