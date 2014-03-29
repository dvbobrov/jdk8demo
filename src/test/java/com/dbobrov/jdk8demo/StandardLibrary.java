package com.dbobrov.jdk8demo;

import org.junit.Test;
import sun.print.CUPSPrinter;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

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
        System.out.println(instant.atZone(ZoneId.of("Europe/Moscow")));

        clock = Clock.system(ZoneId.of("Asia/Yekaterinburg"));
        System.out.println(clock.instant());

        local = LocalDateTime.now(clock);
        System.out.println(local);
    }

    @Test
    public void dateTimeOps() {
        LocalDateTime local = LocalDateTime.now();

        System.out.println(local);
        System.out.println(local.plus(5, ChronoUnit.MINUTES));
        System.out.println(local.plusDays(10));
        System.out.println(local.truncatedTo(ChronoUnit.HOURS));
    }


    @Test
    public void dateTimeConv() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.toInstant());

        Date date = new Date();
        System.out.println(date.toInstant());

    }
}
