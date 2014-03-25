package com.dbobrov.jdk8demo;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class Streams {
    @Ignore
    static class User {
        private int id;
        private String firstName;
        private String lastName;
        private int groupId;

        //region boilerplate
        User(int id, String firstName, String lastName, int groupId) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.groupId = groupId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
//endregion
    }

    @Test
    public void oldStyle() {
        List<User> users = asList(
                new User(1, "Tywin", "Lannister", 1),
                new User(2, "Joffrey", "Baratheon", 1),
                new User(3, "Edward", "Stark", 2),
                new User(4, "John", "Snow", 2),
                new User(5, "Cersei", "Lannister", 1));

        Map<Integer, List<User>> byGroup = new HashMap<>();
        for (User user : users) {
            if (!byGroup.containsKey(user.getGroupId())) {
                byGroup.put(user.getGroupId(), new ArrayList<>());
            }
            byGroup.get(user.getGroupId()).add(user);
        }
        for (List<User> list : byGroup.values()) {
            list.sort(new Comparator<User>() {
                @Override
                public int compare(User a, User b) {
                    int c1 = a.getLastName()
                            .compareTo(b.getLastName());
                    if (c1 != 0) return c1;
                    return a.getFirstName()
                            .compareTo(b.getFirstName());
                }
            });
        }
        assertThat(byGroup.size(), is(2));
        assertThat(byGroup.get(1).size(), is(3));
        assertThat(byGroup.get(2).size(), is(2));
        assertThat(byGroup.get(1).get(0).getId(), is(2));
        assertThat(byGroup.get(1).get(1).getId(), is(5));
        assertThat(byGroup.get(1).get(2).getId(), is(1));
    }

    @Test
    public void streamStyle() {
        List<User> users = asList(
                new User(1, "Tywin", "Lannister", 1),
                new User(2, "Joffrey", "Baratheon", 1),
                new User(3, "Edward", "Stark", 2),
                new User(4, "John", "Snow", 2),
                new User(5, "Cersei", "Lannister", 1));

        Map<Integer, List<User>> byGroup = users
                .stream()
                .collect(groupingBy(User::getGroupId));

        byGroup.forEach((k, v) -> v.sort(
                comparing(User::getLastName)
                        .thenComparing(User::getFirstName)
        ));

        assertThat(byGroup.size(), is(2));
        assertThat(byGroup.get(1).size(), is(3));
        assertThat(byGroup.get(2).size(), is(2));
        assertThat(byGroup.get(1).get(0).getId(), is(2));
        assertThat(byGroup.get(1).get(1).getId(), is(5));
        assertThat(byGroup.get(1).get(2).getId(), is(1));
    }


    @Test
    public void join() {
        List<Integer> ints = asList(1, 2, 3, 4, 5, 6, 7);

        String s = ints.stream().map(String::valueOf)
                .collect(Collectors.joining(", "));
        assertThat(s, is("1, 2, 3, 4, 5, 6, 7"));
    }

    @Test
    public void test0() {
        List<String> strings = asList("a", "aa", "aaa", "aaaa", "aaaaa");

        OptionalInt any = strings
                .stream()
                .filter(s -> s.length() % 2 == 0)
                .mapToInt(String::length).max();
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


        int[] cur = new int[]{0};
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


    @Test
    public void randomLib() {
        new Random()
                .ints(5L)
                .mapToObj(Integer::toString)
                .forEach(System.out::println);
    }

    @Test
    public void builder() {
        IntStream.Builder builder = IntStream.builder();
        for (int i = 0; i < 10; i++) {
            builder.add(i);
        }
        IntStream stream = builder.build();
        assertThat(stream.sum(), Is.is(10 * 9 / 2));
    }
}
