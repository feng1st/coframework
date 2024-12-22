package io.codeone.framework.plugin.binding;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnablePlugin(EnablePluginTestPluginFoo.class)
public class EnablePluginTestService {

    @EnablePlugin(EnablePluginTestPluginFoo.class)
    public static List<Object> staticMethod() {
        return new ArrayList<>();
    }

    @EnablePlugin({})
    public List<Object> empty() {
        return new ArrayList<>();
    }

    public List<Object> foo() {
        return new ArrayList<>();
    }

    @EnablePlugin({EnablePluginTestPluginFoo.class, EnablePluginTestPluginBar.class})
    public List<Object> fooBar() {
        return new ArrayList<>();
    }
}
