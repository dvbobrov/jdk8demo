package com.dbobrov.jdk8demo;


import org.junit.Assert;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Membars {
    int a, b, c, d;


    Unsafe unsafe;
    {
        try {

            Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singleoneInstanceField.setAccessible(true);
            unsafe = (Unsafe) singleoneInstanceField.get(null);

        } catch (Exception ignore) {
        }
    }

    @Test
    public void membars() {
        Assert.assertNotNull(unsafe);

        a = 1;

        unsafe.loadFence(); // Loads before this can't be reordered with
                            // loads and stores after

        b = 2;
        System.out.println(c);

        unsafe.storeFence(); // Loads before this can't be reordered with
                             // loads and stores after

        a = 3;
        System.out.println(d);

        unsafe.fullFence();

        c = 5;

    }
}
