package com.dbobrov.jdk8demo;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Defaults {
    @Ignore
    interface A {
        abstract int f(int x);

        default int g(int x) {
            return x + 42;
        }
    }

    @Ignore
    interface B {
        default int g(int x) {
            return x - 42;
        }
    }

    @Ignore
    static class C implements A {
        @Override
        public int f(int x) {
            return 42 - x;
        }
    }

    @Ignore
    static class D implements B {}



    @Test
    public void default0() {
        A a = new C();
        assertThat(a.g(1), is(43));

        B b = new D();
        assertThat(b.g(1), is(-41));

        a = x -> x / 2;
        assertThat(a.f(42), is(21));
    }













    @Ignore
    static class E implements A, B { // Oh, sh~

        @Override
        public int f(int x) {
            return 2 * x;
        }

        @Override
        public int g(int x) { // Implementation is required
            return x * x;
        }
    }

    @Ignore
    interface F {
        abstract int g(int x);
    }

    @Ignore
    static class G implements B, F {

        @Override
        public int g(int x) { // And here
            return B.super.g(x);
        }

    }

    @Test
    public void default1() {
        A e = new E();
        assertThat(e.g(10), is(100));

        F f = new G();
        assertThat(f.g(10), is(-32));
    }



    @Ignore
    interface Func<T, R> {
        R apply(T arg);

        static <U>Func<U, U> identity() {
            return u -> u;
        }
    }

    @Test
    public void staticMethod() {
        Func<Integer, Integer> id = Func.identity();
        assertThat(id.apply(42), is(42));
    }
}
