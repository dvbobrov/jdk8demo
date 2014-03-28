package com.dbobrov.jdk8demo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Unsigned {
    @Test
    public void unsignedToString() {
        int a = 1 << 31;
        assertThat(Integer.toString(a),
                is("-2147483648"));
        assertThat(Integer.toUnsignedString(a),
                is("2147483648"));
    }

    @Test
    public void unsignedArithmetics() {
        int a = (1 << 31) + (1 << 20);

        assertThat(Integer.toString(a), is("-2146435072"));
        assertThat(Integer.toUnsignedString(a), is("2148532224"));
    }

    @Test
    public void compare() {
        assertThat(-1 < 1, is(true));
        assertThat(Integer.compareUnsigned(-1, 1) > 0, is(true));
    }
}

