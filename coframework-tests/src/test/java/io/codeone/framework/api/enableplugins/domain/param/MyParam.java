package io.codeone.framework.api.enableplugins.domain.param;

import io.codeone.framework.api.parameter.BaseParam;
import io.codeone.framework.api.util.ArgChecker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MyParam extends BaseParam {

    private Long id;

    @Override
    public void validate() {
        ArgChecker.checkNotNull(id, "id is null");
    }
}
