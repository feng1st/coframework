package io.codeone.framework.plugin.binding;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AnnoPluginBindingTestAnno
public class AnnoPluginBindingTestService {

    public List<Object> method(List<Object> param) {
        return param;
    }
}
