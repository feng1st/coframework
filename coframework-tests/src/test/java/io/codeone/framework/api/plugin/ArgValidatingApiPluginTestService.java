package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import org.springframework.stereotype.Service;

@API
@Service
public class ArgValidatingApiPluginTestService {

    public void consume(ArgValidatingApiPluginTestApiParam param) {
    }

    public void consume(ArgValidatingApiPluginTestNonApiParam param) {
    }
}
