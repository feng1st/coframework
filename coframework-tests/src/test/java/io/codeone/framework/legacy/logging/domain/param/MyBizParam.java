package io.codeone.framework.legacy.logging.domain.param;

import io.codeone.framework.api.parameter.BaseBizParam;
import io.codeone.framework.api.util.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MyBizParam extends BaseBizParam {

    private Long id;

    @Override
    public void validate() {
        Validator.requireNonNull(id, "id is null");
    }
}
