package io.codeone.framework.plugin.binding;

import org.springframework.stereotype.Service;

import java.util.List;

@PluginBindingProcessorTestAnno
@Service
public class PluginBindingProcessorTestService {

    public List<Object> method(List<Object> param) {
        return param;
    }
}
