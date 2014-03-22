package com.dbobrov.jdk8demo;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class Streams {
    @Test
    public void test0() {
        List<String> strings = asList("a", "aa", "aaa", "aaaa", "aaaaa");

        OptionalInt any = strings.stream().filter(s -> s.length() % 2 == 0).mapToInt(String::length).max();
        Assert.assertTrue(any.isPresent());
        assertThat(any.getAsInt(), is(4));
    }























    @Test
    public void test1() {
        String[] args = {"1  2", " 3 ", "4", "10 -100"};
        int sum =
                stream(args)
                .flatMap(s -> stream(s.trim().split("\\s+")))
                .mapToInt(Integer::parseInt).sum();

        assertThat(sum, is(-80));
    }






















    @Test
    public void quiz() {
        @SuppressWarnings("unchecked") List<Integer>[] g = new List[5]; // !
        int[] d = new int[5];

        setup(g, d);


        int[] cur = new int[] {0};
        while (cur.length > 0) {
            cur = IntStream.of(cur).flatMap(v -> {
                IntStream.Builder b = IntStream.builder();
                for (int i : g[v]) {
                    if (d[v] + 1 < d[i]) {
                        d[i] = d[v] + 1;
                        b.add(i);
                    }
                }
                return b.build();
            }).toArray();
        }

        test(d);
    }











    private void test(int[] d) {
        assertArrayEquals(new int[]{0, 1, 2, 2, 2}, d);
    }



    private void setup(List<Integer>[] g, int[] d) {
        for (int i = 0; i < g.length; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        d[0] = 0;

        g[0] = asList(1);
        g[1] = asList(0, 2, 3, 4);
        g[2] = asList(1, 3);
        g[3] = asList(1, 2);
        g[4] = asList(1);
    }
}
