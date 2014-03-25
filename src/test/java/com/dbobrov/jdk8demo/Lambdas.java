package com.dbobrov.jdk8demo;

import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Lambdas {

    @Test
    public void comparatorOld() {
        List<String> list = new ArrayList<>();
        list.add("abacaba");
        list.add("fasd");
        list.add("jdjjssf");
        list.add("djmxa");
        list.add("aasfcvd");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        assertThat(list.get(0), is("jdjjssf"));
        assertThat(list.get(4), is("aasfcvd"));
    }

    @Test
    public void comparatorLambda() {
        List<String> list = new ArrayList<>();
        list.add("abacaba");
        list.add("fasd");
        list.add("jdjjssf");
        list.add("djmxa");
        list.add("aasfcvd");

        Collections.sort(list, (String a, String b) -> {
            return b.compareTo(a);
        });

        assertThat(list.get(0), is("jdjjssf"));
        assertThat(list.get(4), is("aasfcvd"));
    }

    @Test
    public void comparatorLambdaShort() {
        List<String> list = new ArrayList<>();
        list.add("abacaba");
        list.add("fasd");
        list.add("jdjjssf");
        list.add("djmxa");
        list.add("aasfcvd");

        Collections.sort(list, (a, b) -> b.compareTo(a));

        assertThat(list.get(0), is("jdjjssf"));
        assertThat(list.get(4), is("aasfcvd"));

    }















    @Test
    public void libraryLambdaClasses() {
        Function<String, Integer> function = t -> t.length();
        assertThat(function.apply("abacaba"), is(7));

        ToIntFunction<String> toIntFunction = value -> value.length();
        assertThat(toIntFunction.applyAsInt("abacaba"), is(7));

        ToIntBiFunction<Integer, Integer> max = (t, u) -> Math.max(t, u);
        assertThat(max.applyAsInt(73, 42), is(73));
    }





















    private IntUnaryOperator fib = n -> (n < 2) ? 1 :
            this.fib.applyAsInt(n - 1) + this.fib.applyAsInt(n - 2);
    @Test
    public void testRecursiveLambda() {
        assertThat(fib.applyAsInt(5), is(8));
    }


















    @Ignore
    private static interface Func {
        int f(int a, int b);
    }


    @Ignore
    @FunctionalInterface
    private static interface FuncAnnotated {
        int f(int x);
//        int g(int x); // Will not compile if this line is uncommented
    }




    @Test
    public void customLambdaClass() {

        Func lambda = (a, b) -> Math.max(a, b);
        assertThat(lambda.f(73, 42), is(73));

        FuncAnnotated lambda2 = a -> a * a;
        assertThat(lambda2.f(5), is(25));
    }










    @Test
    public void capture() {
        int i = 42; /* Should be EFFECTIVELY final */

//        i = 10; // Compile error if it's used in lambdas

//        IntUnaryOperator op = i -> i * i; // Compile error
        IntUnaryOperator op = j -> j + i;
        assertThat(op.applyAsInt(10), is(52));


//        IntSupplier getAndIncrement = () -> i++; // Compile error
    }









    @Test
    public void methodReference() {
        Func lambda = Math::max;

        assertThat(lambda.f(73, 42), is(73));

//        Function<Integer, String> toString = Integer::toString;
    }


















    @Test
    public void nonStaticMethodRef() {
        ToIntFunction<String> f = "abacaba"::compareTo;
        assertTrue(f.applyAsInt("a") > 0);
        assertThat(f.applyAsInt("abacaba"), is(0));
        assertTrue(f.applyAsInt("b") < 0);
    }
}
