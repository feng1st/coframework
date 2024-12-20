package io.codeone.framework.api.plugin;

import io.codeone.framework.api.util.Validator;
import lombok.Data;

@Data
public class ArgValidatingApiPluginTestNonApiParam {

    private Long userId;

    public void validate() {
        Validator.requireNonNull(userId, "userId is required");
    }
}
