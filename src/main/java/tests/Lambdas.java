package tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.*;
import java.util.function.*;

public class Lambdas {
    @Rule
    public TestName testName = new TestName();

    @Test
    public void comparatorOld() {
        System.out.println(testName.getMethodName());
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

        System.out.println(list.toString());
        System.out.println("---------------------");

    }























    @Test
    public void comparatorLambda() {
        System.out.println(testName.getMethodName());
        List<String> list = new ArrayList<>();
        list.add("abacaba");
        list.add("fasd");
        list.add("jdjjssf");
        list.add("djmxa");
        list.add("aasfcvd");

        Collections.sort(list, (String a, String b) -> {
            return b.compareTo(a);
        });

        System.out.println(list.toString());
        System.out.println("---------------------");

    }



























    @Test
    public void comparatorLambdaShort() {
        System.out.println(testName.getMethodName());

        List<String> list = new ArrayList<>();
        list.add("abacaba");
        list.add("fasd");
        list.add("jdjjssf");
        list.add("djmxa");
        list.add("aasfcvd");

        Collections.sort(list, (a, b) -> b.compareTo(a));

        System.out.println(list.toString());
        System.out.println("---------------------");

    }





























    public static interface CustomLambda {
        int f(int a, int b);
    }










    @Test
    public void customLambdaClass() {
        System.out.println(testName.getMethodName());

        CustomLambda lambda = (a, b) -> Math.max(a, b);

        System.out.println(lambda.f(73, 42));
        System.out.println("---------------------");

    }


    @Test
    public void methodReference() {
        System.out.println(testName.getMethodName());

        CustomLambda lambda = Math::max;

        System.out.println(lambda.f(42, 73));
        System.out.println("---------------------");

    }
















    @Test
    public void libraryLambdaClasses() {
        System.out.println(testName.getMethodName());

        Function<String, Integer> function = String::length;

        System.out.println(function.apply("abacaba"));


        ToIntFunction<String> toIntFunction = String::length;
        System.out.println(toIntFunction.applyAsInt("abacaba"));

        ToIntBiFunction<Integer, Integer> max = Math::max;
        System.out.println(max.applyAsInt(42, 73));
        System.out.println("---------------------");

    }


























































    // :(
    private IntUnaryOperator fib /* = (n) -> n == 0 || n == 1 ? 1 : fib.applyAsInt(n - 1) + fib.applyAsInt(n - 2); */;
    {
        fib = (n) -> n == 0 || n == 1 ? 1 : fib.applyAsInt(n - 1) + fib.applyAsInt(n - 2);
    }
    @Test
    public void testRecursiveLambda() {
        System.out.println(testName.getMethodName());

        System.out.println(fib.applyAsInt(5));
        System.out.println("---------------------");

    }




































    @Test
    public void nonStaticMethodRef() {
        System.out.println(testName.getMethodName());
        ToIntFunction<String> f = "abacaba"::compareTo;
        System.out.println(f.applyAsInt("a"));
        System.out.println(f.applyAsInt("abacaba"));
        System.out.println(f.applyAsInt("b"));
        System.out.println("---------------------");
    }








    @Test
    public void random() {
        System.out.println(testName.getMethodName());
        new Random().ints(5L).mapToObj(Integer::toString).forEach(System.out::println);
        System.out.println("---------------------");
    }
}
