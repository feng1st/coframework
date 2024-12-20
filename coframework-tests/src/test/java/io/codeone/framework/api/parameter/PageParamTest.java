package io.codeone.framework.api.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PageParamTest {

    @Test
    public void defaultValue() {
        PageParamTestParam param = new PageParamTestParam();
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(20, param.getPageSize());
    }

    @Test
    public void invalidValue() {
        PageParamTestParam param = new PageParamTestParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(1, param.getPageSize());
    }

    @Test
    public void firstPage() {
        PageParamTestParam param = new PageParamTestParam();
        param.setPageIndex(1);
        param.setPageSize(10);
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(10, param.getPageSize());
        Assertions.assertEquals(0, param.getOffset());
    }

    @Test
    public void fifthPage() {
        PageParamTestParam param = new PageParamTestParam();
        param.setPageIndex(5);
        param.setPageSize(10);
        Assertions.assertEquals(5, param.getPageIndex());
        Assertions.assertEquals(10, param.getPageSize());
        Assertions.assertEquals(40, param.getOffset());
    }
}