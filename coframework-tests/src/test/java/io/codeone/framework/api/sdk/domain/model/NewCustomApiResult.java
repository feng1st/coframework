package io.codeone.framework.api.sdk.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewCustomApiResult<T> extends CustomApiResult<T> {
}
