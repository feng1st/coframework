package io.codeone.framework.api.converter;

import lombok.Data;

@Data
public class ApiParamConverterTestLegacyParam {

    public void check() {
        throw new IllegalArgumentException("invalid param");
    }
}
