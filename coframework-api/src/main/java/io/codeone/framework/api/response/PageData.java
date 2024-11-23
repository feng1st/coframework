package io.codeone.framework.api.response;

import java.util.List;

public interface PageData<T> {

    List<T> getData();

    int getPageIndex();

    int getPageSize();

    Long getTotalCount();

    default boolean isEmpty() {
        return getData() == null
                || getData().isEmpty();
    }

    default boolean isHasMore() {
        return (long) getPageSize() * getPageIndex() < getTotalCount();
    }
}
