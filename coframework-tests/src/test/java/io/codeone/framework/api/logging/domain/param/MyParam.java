package io.codeone.framework.api.logging.domain.param;

import io.codeone.framework.api.request.BaseRequest;
import io.codeone.framework.api.util.ArgChecker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MyParam extends BaseRequest {

    private Long id;

    @Override
    public void checkArgs() {
        ArgChecker.checkNotNull(id, "id is null");
    }
}
