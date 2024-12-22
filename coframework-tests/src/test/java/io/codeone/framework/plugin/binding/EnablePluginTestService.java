package io.codeone.framework.plugin.binding;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnablePlugin(EnablePluginTestPluginFoo.class)
public class EnablePluginTestService {

    @EnablePlugin(EnablePluginTestPluginFoo.class)
    public static List<Object> staticMethod(List<Object> param) {
        return param;
    }

    @EnablePlugin({})
    public List<Object> empty(List<Object> param) {
        return param;
    }

    public List<Object> foo(List<Object> param) {
        return param;
    }

    @EnablePlugin({EnablePluginTestPluginFoo.class, EnablePluginTestPluginBar.class})
    public List<Object> fooBar(List<Object> param) {
        return param;
    }
}
