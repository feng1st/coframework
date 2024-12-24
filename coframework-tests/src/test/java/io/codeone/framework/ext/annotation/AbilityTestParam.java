package io.codeone.framework.ext.annotation;

import io.codeone.framework.api.parameter.BaseBizPageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(staticName = "of")
public class AbilityTestParam extends BaseBizPageParam {

    public static AbilityTestParam of(String bizId) {
        AbilityTestParam param = new AbilityTestParam();
        param.setBizId(bizId);
        return param;
    }
}
