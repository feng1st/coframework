package io.codeone.framework.api.parameter;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BasePageParam extends BaseParam {

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.START_PAGE_INDEX);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, PageConstants.MIN_PAGE_SIZE);
    }

    public long getOffset() {
        return ((long) this.pageIndex - PageConstants.START_PAGE_INDEX) * this.pageSize;
    }
}
