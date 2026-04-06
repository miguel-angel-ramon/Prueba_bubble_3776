package com.example;

import java.util.function.Function;

public class ComplexS3776Demo {

    /**
     * @deprecated use newApi instead
     */
    @Deprecated
    public void oldApiWithReplacement() {
        System.out.println("old api with replacement");
    }

    public void newApi() {
        System.out.println("new api");
    }

    @Deprecated
    public void oldApiWithoutReplacement() {
        System.out.println("old api without replacement");
    }

    @SuppressWarnings("java:S1874")
    public void testAll() {
        Thread t = new Thread();
        t.stop();

        oldApiWithReplacement();
        oldApiWithoutReplacement();
    }

    public void processLambdas() {
        Function<String, String> f1 = s -> {
            if (s.isEmpty()) {
                return "EMPTY";
            }
            return s.toUpperCase();
        };

        Function<String, String> f2 = s -> {
            if (s.length() > 3) {
                return "LONG";
            }
            return "SHORT";
        };

        Function<String, String> f3 = s -> {
            if (s.startsWith("A")) {
                return "A";
            }
            return "OTHER";
        };

        System.out.println(f1.apply("abc"));
        System.out.println(f2.apply("abcd"));
        System.out.println(f3.apply("Apple"));
    }

    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();
        app.testAll();
        app.processLambdas();
    }
}