package io.codeone.framework.plugin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PluginTestAnno
public class PluginTestService {

    public Object returning(List<Object> param) {
        return new Object();
    }

    public void throwing(List<Object> param) {
        throw new IllegalStateException();
    }
}
