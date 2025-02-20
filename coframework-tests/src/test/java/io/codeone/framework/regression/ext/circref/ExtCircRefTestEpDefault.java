package io.codeone.framework.regression.ext.circref;

import io.codeone.framework.ext.annotation.Extension;
import org.springframework.beans.factory.annotation.Autowired;

@Extension
public class ExtCircRefTestEpDefault implements ExtCircRefTestEp {

    @Autowired
    private ExtCircRefTestNestedEp extCircRefTestNestedEp;

    @Override
    public void test() {
        extCircRefTestNestedEp.test();
    }
}
