package bechmarks;


import org.openjdk.jmh.annotations.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5)
@Fork(1)
@Measurement(iterations = 5)
public class HashSetPerf {

    private static class Simple {
        private final int hc;

        public Simple(int hc) {
            this.hc = hc;
        }

        @Override
        public int hashCode() {
            return hc;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }

    private static class Ordered extends Simple implements Comparable<Ordered> {
        private final int c;

        public Ordered(int hc, int c) {
            super(hc);
            this.c = c;
        }

        @Override
        public int compareTo(Ordered o) {
            return Integer.compare(c, o.c);
        }
    }

    @GenerateMicroBenchmark
    public Set<Simple> measureSimple() {
        Set<Simple> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(new Simple(1));
        }
        return set;
    }











    @GenerateMicroBenchmark
    public Set<Ordered> measureOrdered() {
        Set<Ordered> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(new Ordered(1, i));
        }
        return set;
    }















    @GenerateMicroBenchmark
    public Set<Simple> measurePartiallyOrdered1() {
        Set<Simple> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                set.add(new Simple(1));
            } else {
                set.add(new Ordered(1, i));
            }
        }
        return set;
    }

    @GenerateMicroBenchmark
    public Set<Simple> measurePartiallyOrdered2() {
        Set<Simple> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                set.add(new Simple(1));
            } else {
                set.add(new Ordered(1, i));
            }
        }
        return set;
    }


}