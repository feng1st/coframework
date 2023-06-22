package io.codeone.framework.plugin.domain.service;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.ext.plugin.*;
import org.springframework.stereotype.Service;

@Service
@EnablePlugin({BlockLargeArgsTestPlugin.class,
        BlockSmallResultTestPlugin.class,
        DecrResultTestPlugin.class,
        ExToZeroTestPlugin.class,
        IncrArgsTestPlugin.class})
public class TestPluginService {

    public Long sum(Long a, Long b) {
        return a + b;
    }
}
