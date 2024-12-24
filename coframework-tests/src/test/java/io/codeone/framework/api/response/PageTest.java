package io.codeone.framework.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class PageTest {

    @Test
    public void invalidPageIndexSize() {
        Page<Object> page = Page.of(0, 0);
        Assertions.assertNull(page.getData());
        Assertions.assertEquals(1, page.getPageIndex());
        Assertions.assertEquals(1, page.getPageSize());
        Assertions.assertNull(page.getTotalCount());
        Assertions.assertNull(page.isHasMore());
    }

    @Test
    public void ofPageData() {
        PageData<Object> pageData = Page.of(Arrays.asList(1, 2, 3), 5, 3, 100L);
        Page<Object> page = Page.of(pageData);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(3, page.getPageSize());
        Assertions.assertEquals(100L, page.getTotalCount());
        Assertions.assertTrue(page.isHasMore());
    }

    @Test
    public void ofPageIndexSize() {
        Page<Object> page = Page.of(5, 10);
        Assertions.assertNull(page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(10, page.getPageSize());
        Assertions.assertNull(page.getTotalCount());
        Assertions.assertNull(page.isHasMore());
    }

    @Test
    public void ofDataPageIndexSize() {
        Page<Object> page = Page.of(Arrays.asList(1, 2, 3), 5, 10);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(10, page.getPageSize());
        Assertions.assertNull(page.getTotalCount());
        Assertions.assertNull(page.isHasMore());
    }

    @Test
    public void ofDataPageIndexSizeTotalCount() {
        Page<Object> page = Page.of(Arrays.asList(1, 2, 3), 5, 3, 100L);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(3, page.getPageSize());
        Assertions.assertEquals(100L, page.getTotalCount());
        Assertions.assertTrue(page.isHasMore());
    }

    @Test
    public void isEmpty() {
        Page<Object> page = Page.of(Collections.emptyList(), 5, 3);
        Assertions.assertEquals(Collections.emptyList(), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(3, page.getPageSize());
        Assertions.assertNull(page.getTotalCount());
        Assertions.assertNull(page.isHasMore());
    }

    @Test
    public void hasMore() {
        Page<Object> page = Page.of(Arrays.asList(1, 2, 3), 5, 3, 16L);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(3, page.getPageSize());
        Assertions.assertEquals(16L, page.getTotalCount());
        Assertions.assertTrue(page.isHasMore());
    }

    @Test
    public void hasNoMore() {
        Page<Object> page = Page.of(Arrays.asList(1, 2, 3), 5, 3, 15L);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), page.getData());
        Assertions.assertEquals(5, page.getPageIndex());
        Assertions.assertEquals(3, page.getPageSize());
        Assertions.assertEquals(15L, page.getTotalCount());
        Assertions.assertFalse(page.isHasMore());
    }
}