package com.dbobrov.jdk8demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class StandardLibrary {
    @Test
    public void parallelSort() {
        int[] array = new Random().ints(100000).toArray();

        Arrays.parallelSort(array);

        for (int i = 1; i < array.length; i++) {
            assertTrue(array[i - 1] <= array[i]);
        }
    }
}
