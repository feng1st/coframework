package io.codeone.framework.api.extoresult.domain.param;

import io.codeone.framework.request.BaseRequest;
import io.codeone.framework.util.ArgChecker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MyParam extends BaseRequest {

    @Override
    public void checkArgs() {
        ArgChecker.check(false, "None accepted");
    }
}
