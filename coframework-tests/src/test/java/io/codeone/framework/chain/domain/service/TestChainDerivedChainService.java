package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.domain.filter.TestChainNon3NFilter;
import io.codeone.framework.chain.domain.filter.TestChainNon5NFilter;
import io.codeone.framework.chain.domain.filter.TestChainOddFilter;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.spec.AddAfterAlteration;
import io.codeone.framework.chain.spec.BasicChainSpec;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.chain.spec.DerivedChainSpec;
import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestChainDerivedChainService {

    private static final ChainSpec DEFAULT_FILTER_CHAIN_SPEC = BasicChainSpec.of("testDerived",
            TestChainOddFilter.class,
            TestChainNon5NFilter.class);

    /**
     * This value could be provided by an {@link Ability} or an
     * {@link ExtensionPoint}.
     */
    private static final ChainSpec EXT1_FILTER_CHAIN_SPEC = DerivedChainSpec.of(DEFAULT_FILTER_CHAIN_SPEC,
            AddAfterAlteration.of(TestChainOddFilter.class, TestChainNon3NFilter.class));

    @Resource
    private ChainFactory chainFactory;

    public List<Long> filterLongsBasicChain(List<Long> target) {
        return filterLongs(DEFAULT_FILTER_CHAIN_SPEC, target);
    }

    public List<Long> filterLongsDerivedChain(List<Long> target) {
        return filterLongs(EXT1_FILTER_CHAIN_SPEC, target);
    }

    private List<Long> filterLongs(ChainSpec chainSpec, List<Long> target) {
        Chain<List<Long>> chain = chainFactory.getChain(chainSpec);

        Context<List<Long>> context = Context.of(target);

        return chain.execute(context);
    }
}
