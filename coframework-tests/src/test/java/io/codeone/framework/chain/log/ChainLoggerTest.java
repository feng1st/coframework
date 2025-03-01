package io.codeone.framework.chain.log;

import ch.qos.logback.classic.Level;
import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChainLoggerTest extends BaseLoggingTest {

    @Autowired
    private ChainLoggerTestNodeProduce chainLoggerTestNodeProduce;
    @Autowired
    private ChainLoggerTestNodeConsume chainLoggerTestNodeConsume;
    @Autowired
    private ChainLoggerTestNodeAbility chainLoggerTestNodeAbility;

    @Test
    public void logDefault() {
        test(() -> {
            Sequential.of(chainLoggerTestNodeProduce, chainLoggerTestNodeConsume).run(Context.of());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeProduce\",\"elapsed\":0}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeConsume\",\"elapsed\":0}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTestNodeProduce elapsed=0");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTestNodeConsume elapsed=0");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTestNodeProduce||elapsed=>0");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTestNodeConsume||elapsed=>0");
    }

    @Test
    public void logChainName() {
        test(() -> {
            Sequential.of(chainLoggerTestNodeProduce, chainLoggerTestNodeConsume).run(Context.of().chainName("chain"));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"chain\",\"node\":\"ChainLoggerTestNodeProduce\",\"elapsed\":0}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"chain\",\"node\":\"ChainLoggerTestNodeConsume\",\"elapsed\":0}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=chain node=ChainLoggerTestNodeProduce elapsed=0");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=chain node=ChainLoggerTestNodeConsume elapsed=0");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>chain||node=>ChainLoggerTestNodeProduce||elapsed=>0");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>chain||node=>ChainLoggerTestNodeConsume||elapsed=>0");
    }

    @Test
    public void logLambda() {
        test(() -> {
            Sequential.of(context -> {
                        context.put(String.class, "Hello chain");
                        return true;
                    },
                    context -> {
                        System.out.println(context.get(String.class));
                        return true;
                    }).run(Context.of());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0");
    }

    @Test
    public void logBreak() {
        test(() -> {
            Sequential.of(context -> {
                        context.put(String.class, "Hello chain");
                        return false;
                    },
                    context -> {
                        System.out.println(context.get(String.class));
                        return true;
                    }).run(Context.of());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 3, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"break\":true}");
        assertLog(1, 3, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0 break=true");
        assertLog(2, 3, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0||break=>true");
    }

    @Test
    public void logException() {
        test(() -> {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> Sequential.of(context -> {
                                context.put(String.class, "Hello chain");
                                throw new IllegalStateException();
                            },
                            context -> {
                                System.out.println(context.get(String.class));
                                return true;
                            }).run(Context.of()));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 3, "chain", Level.ERROR, IllegalStateException.class,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException\"}");
        assertLog(1, 3, "chain", Level.ERROR, IllegalStateException.class,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0 exception=java.lang.IllegalStateException");
        assertLog(2, 3, "chain", Level.ERROR, IllegalStateException.class,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0||exception=>java.lang.IllegalStateException");
    }

    @Test
    public void logParam() {
        test(() -> {
            Sequential.of(context -> {
                        context.put(String.class, "Hello chain");
                        context.log("text", "Hello chain");
                        return true;
                    },
                    context -> {
                        System.out.println(context.get(String.class));
                        return true;
                    }).run(Context.of());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"text\":\"Hello chain\"}}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0 params.text=\"Hello chain\"");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0||params.text=>\"Hello chain\"");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0");
    }

    @Test
    public void logOnExecute() {
        test(() -> {
            Sequential.of(context -> {
                        context.put(String.class, "Hello chain");
                        return true;
                    },
                    context -> {
                        System.out.println(context.get(String.class));
                        return true;
                    }).run(Context.of(Long.class, 10000L).onExecute(context -> {
                context.ifPresent(Long.class, o -> context.log("traceId", o));
                context.ifPresent(String.class, o -> context.log("text", o));
            }));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"traceId\":10000}}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"traceId\":10000,\"text\":\"Hello chain\"}}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0 params.traceId=10000");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTest$$Lambda elapsed=0 params.traceId=10000 params.text=\"Hello chain\"");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0||params.traceId=>10000");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTest$$Lambda||elapsed=>0||params.traceId=>10000||params.text=>\"Hello chain\"");
    }

    @Test
    public void route() {
        test(() -> {
            Assertions.assertEquals("foo", chainLoggerTestNodeAbility.run(Context.of().bizScenario(BizScenario.ofBizId("foo")), String.class));
            Assertions.assertEquals("bar", chainLoggerTestNodeAbility.run(Context.of().bizScenario(BizScenario.ofBizId("bar")), String.class));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

        assertLog(0, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeAbilityForFoo\",\"bizId\":\"foo\",\"scenario\":\"*\",\"elapsed\":0}");
        assertLog(1, 6, "chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeAbilityForBar\",\"bizId\":\"bar\",\"scenario\":\"*\",\"elapsed\":0}");
        assertLog(2, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTestNodeAbilityForFoo bizId=foo scenario=* elapsed=0");
        assertLog(3, 6, "chain", Level.INFO, null,
                "chain=anonymous node=ChainLoggerTestNodeAbilityForBar bizId=bar scenario=* elapsed=0");
        assertLog(4, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTestNodeAbilityForFoo||bizId=>foo||scenario=>*||elapsed=>0");
        assertLog(5, 6, "chain", Level.INFO, null,
                "chain=>anonymous||node=>ChainLoggerTestNodeAbilityForBar||bizId=>bar||scenario=>*||elapsed=>0");
    }
}