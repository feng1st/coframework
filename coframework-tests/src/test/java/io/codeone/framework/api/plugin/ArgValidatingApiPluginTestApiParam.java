package io.codeone.framework.api.plugin;

import io.codeone.framework.api.parameter.BaseParam;
import io.codeone.framework.api.util.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArgValidatingApiPluginTestApiParam extends BaseParam {

    private Long userId;

    @Override
    public void validate() {
        super.validate();
        Validator.requireNonNull(userId, "userId is required");
    }
}
