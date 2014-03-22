package com.dbobrov.jdk8demo;

import org.openjdk.jmh.annotations.*;
import sun.misc.Contended;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Fork(1)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Contention {
    @State(Scope.Group)
    public static class IntPair {
        int a, b;
    }

    @GenerateMicroBenchmark
    @Group("simple")
    public void simpleInc(IntPair pair) {
        pair.a++;
    }

    @GenerateMicroBenchmark
    @Group("simple")
    public int simpleGet(IntPair pair) {
        return pair.b;
    }

















































    @State(Scope.Group)
    public static class PaddedIntPair {
        int a;
        int p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15;
        int b;
        int q01, q02, q03, q04, q05, q06, q07, q08, q09, q10, q11, q12, q13, q14, q15;
    }

    @GenerateMicroBenchmark
    @Group("padded")
    public void paddedInc(PaddedIntPair pair) {
        pair.a++;
    }

    @GenerateMicroBenchmark
    @Group("padded")
    public int paddedGet(PaddedIntPair pair) {
        return pair.b;
    }














































    @State(Scope.Group)
    public static class ContentedIntPair {
        int a;
        @Contended int b;
    }

    @GenerateMicroBenchmark
    @Group("contented")
    public void contentedInc(ContentedIntPair pair) {
        pair.a++;
    }

    @GenerateMicroBenchmark
    @Group("contented")
    public int contentedGet(ContentedIntPair pair) {
        return pair.b;
    }
}
