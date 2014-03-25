package com.dbobrov.jdk8demo;


import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10)
@Fork(3)
@Measurement(iterations = 5)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class LambdaCapture {

    @State(Scope.Thread)
    public static class Env {
        public String s = "aa";
        public String t = "aa";

        @Setup
        public void setup() {
            Random r = new Random();
            s += 'a' + r.nextInt('h' - 'a');
            t += 'g' + r.nextInt('z' - 'g');
        }
    }

    @GenerateMicroBenchmark
    public boolean nonCapturedLambda(Env env) {
        Predicate<String> f = t -> "aaa".equals(t);
        return f.test(env.t);
    }

    @GenerateMicroBenchmark
    public boolean capturedLambda(Env env) {
        Predicate<String> f = t -> env.s.equals(t);
        return f.test(env.t);
    }

    @GenerateMicroBenchmark
    public boolean nonCapturedClass(Env env) {
        Predicate<String> f = new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return "aaa".equals(t);
            }
        };
        return f.test(env.t);
    }

    @GenerateMicroBenchmark
    public boolean capturedClass(Env env) {
        Predicate<String> f = new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return env.s.equals(t);
            }
        };
        return f.test(env.t);
    }
}
