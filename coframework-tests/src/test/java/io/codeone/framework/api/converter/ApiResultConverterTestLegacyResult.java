package io.codeone.framework.api.converter;

import lombok.Data;

@Data
public class ApiResultConverterTestLegacyResult<T> {

    private boolean succ;

    private T data;

    private String errCode;

    private String errMsg;

    public static <T> ApiResultConverterTestLegacyResult<T> succ(T data) {
        ApiResultConverterTestLegacyResult<T> result = new ApiResultConverterTestLegacyResult<>();
        result.setSucc(true);
        result.setData(data);
        return result;
    }

    public static <T> ApiResultConverterTestLegacyResult<T> fail(String errorCode, String errorMessage) {
        ApiResultConverterTestLegacyResult<T> result = new ApiResultConverterTestLegacyResult<>();
        result.setSucc(false);
        result.setErrCode(errorCode);
        result.setErrMsg(errorMessage);
        return result;
    }
}
