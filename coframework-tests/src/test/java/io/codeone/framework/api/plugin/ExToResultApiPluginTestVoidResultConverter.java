package io.codeone.framework.api.plugin;

import io.codeone.framework.api.converter.FailureConverter;
import io.codeone.framework.api.exception.ApiError;
import org.springframework.stereotype.Component;

@Component
public class ExToResultApiPluginTestVoidResultConverter
        implements FailureConverter<ExToResultApiPluginTestVoidResult> {

    @Override
    public ExToResultApiPluginTestVoidResult convert(ApiError source) {
        return null;
    }
}
