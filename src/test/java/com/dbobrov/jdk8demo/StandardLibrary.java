package com.dbobrov.jdk8demo;

import org.junit.Test;

import java.time.*;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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






















    @Test
    public void dateTime() {
        LocalDateTime local = LocalDateTime.now();

        ZonedDateTime zoned = local.atZone(ZoneId.of("UTC"));

        System.out.println(local);
        System.out.println(zoned);

        Clock clock = Clock.systemUTC();
        Instant instant = Instant.now(clock);

        System.out.println(instant);

        clock = Clock.system(ZoneId.of("Asia/Yekaterinburg"));
        System.out.println(clock.instant());

        local = LocalDateTime.now(clock);
        System.out.println(local);


    }
}
