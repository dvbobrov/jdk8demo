package com.dbobrov.jdk8demo;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
public class ParallelSort {
    @Param({"100", "1000", "10000", "100000", "1000000"})
    public int size;
    Random random = new Random();
    int[] arr;

    @Setup
    public void setup() {
        arr = new Random().ints(size).toArray();
    }

    public void randomShuffle() {
        for(int i = 1; i < arr.length; i++) {
            int j = random.nextInt(i);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    @GenerateMicroBenchmark
    public int[] baseline() {
        randomShuffle();
        return arr;
    }

    @GenerateMicroBenchmark
    public int[] simpleSort() {
        randomShuffle();
        Arrays.sort(arr);
        return arr;
    }

    @GenerateMicroBenchmark
    public int[] parallelSort() {
        randomShuffle();
        Arrays.parallelSort(arr);
        return arr;
    }
}
