package io.codeone.framework.ext.annotation;

import io.codeone.framework.api.parameter.BaseBizParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AbilityTestParam extends BaseBizParam {

    public static AbilityTestParam of(String bizId) {
        AbilityTestParam param = new AbilityTestParam();
        param.setBizId(bizId);
        return param;
    }
}
