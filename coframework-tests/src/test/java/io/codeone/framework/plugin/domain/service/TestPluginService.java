package io.codeone.framework.plugin.domain.service;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.ext.plugin.*;
import org.springframework.stereotype.Service;

@Service
@EnablePlugin({BlockLargeArgsTestPlugin.class,
        BlockLargeResultTestPlugin.class,
        DecrResultTestPlugin.class,
        ExToZeroTestPlugin.class,
        IncrArgsTestPlugin.class})
public class TestPluginService {

    public Long sum(Long a, Long b) {
        return a + b;
    }

    @EnablePlugin({DecrResultTestPlugin.class,
            ExToZeroTestPlugin.class,
            IncrArgsTestPlugin.class})
    public Long sumNoChecking(Long a, Long b) {
        return a + b;
    }

    @EnablePlugin({BlockLargeArgsTestPlugin.class,
            BlockLargeResultTestPlugin.class,
            ExToZeroTestPlugin.class})
    public Long sumNoAltering(Long a, Long b) {
        return a + b;
    }

    @EnablePlugin({BlockLargeArgsTestPlugin.class,
            BlockLargeResultTestPlugin.class,
            DecrResultTestPlugin.class,
            IncrArgsTestPlugin.class})
    public Long sumNoWrapping(Long a, Long b) {
        return a + b;
    }

    @EnablePlugin({IncrArgsTestPlugin.class,
            IncrArgsTestPlugin.class})
    public Long dupPlugins(Long a, Long b) {
        return a + b;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
