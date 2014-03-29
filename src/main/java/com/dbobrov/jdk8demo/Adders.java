package com.dbobrov.jdk8demo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.logic.BlackHole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Adders {
    @State(Scope.Group)
    public static class AtomicState {
        AtomicLong counter;

        @Setup
        public void setup() {
            counter = new AtomicLong(0);
        }
    }

    @State(Scope.Group)
    public static class AdderState {
        LongAdder counter;

        @Setup
        public void setup() {
            counter = new LongAdder();
        }
    }

    @GenerateMicroBenchmark
    @Group("atomic")
    @GroupThreads(4)
    public void atomic(AtomicState state, BlackHole blackHole) {
        state.counter.getAndIncrement();
        blackHole.consume(state.counter);
    }

    @GenerateMicroBenchmark
    @Group("adder")
    @GroupThreads(4)
    public void adder(AdderState state, BlackHole blackHole) {
        state.counter.increment();
        blackHole.consume(state.counter);
    }
}
