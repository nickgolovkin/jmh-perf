package com.golovkin;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@State(Scope.Benchmark)
@Threads(1)
public class AppTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

    @Param({"10","100","1000"})
    public int iterations;


    @Setup(Level.Invocation)
    public void setupInvokation() throws Exception {
        LOGGER.info(() -> "Hello");
        // executed before each invocation of the benchmark
    }

    @Setup(Level.Iteration)
    public void setupIteration() throws Exception {
        // executed before each invocation of the iteration

}
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(warmups = 1, value = 1)
    @Warmup(batchSize = -1, iterations = 3, time = 10, timeUnit = TimeUnit.NANOSECONDS)
    @Measurement(batchSize = -1, iterations = 10, time = 10, timeUnit = TimeUnit.NANOSECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void test() throws Exception {
        String hello = null;

        nullSafe(() -> hello.length());
    }

    public <T> T nullSafe(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Test
    public void benchmark() throws Exception {
        String[] argv = {};
        org.openjdk.jmh.Main.main(argv);
    }

}