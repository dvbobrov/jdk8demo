package com.dbobrov.jdk8demo;


import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Fork(1)
@Measurement(iterations = 5)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class LambdaCapture {
    private Random random;
    private String s;

    @State(Scope.Thread)
    public static class Env {
        public String s = "";

        @Setup
        public void setup() {
            Random r = new Random();
            for (int i = 0; i < 3; i++) {
                s += 'a' + r.nextInt(26);
            }
        }
    }

    @Setup
    public void setup() {
        random = new Random();
        s = "";
        for (int i = 0; i < 3; i++) {
            s += 'a' + random.nextInt(26);
        }
    }

    @GenerateMicroBenchmark
    public boolean nonCaptured() {
        Predicate<String> f = (t) -> "aaa".equals(t);
        return f.test(s);
    }

    @GenerateMicroBenchmark
    public boolean captured(Env env) {
        Predicate<String> f = (t) -> env.s.equals(t);
        return f.test(s);
    }
}
