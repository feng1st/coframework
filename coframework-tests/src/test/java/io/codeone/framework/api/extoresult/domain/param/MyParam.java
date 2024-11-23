package io.codeone.framework.api.extoresult.domain.param;

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

    @Override
    public void validate() {
        ArgChecker.check(false, "None accepted");
    }
}
