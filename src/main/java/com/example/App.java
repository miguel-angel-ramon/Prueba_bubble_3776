package com.example;

/**
 * Hello world!
 *
 */
public class App 
{
    interface Fn {
        String apply(String v);
    }

    private Fn f1;
    private Fn f2;
    private Fn f3;

    public void setup(String mode) {

        f1 = (String v) -> {
            if (v == null) {
                return "NULL";
            } else {
                return v.toLowerCase();
            }
        };

        f2 = (String v) -> {
            if (v.length() < 3) {
                return "SHORT";
            } else if (v.length() < 6) {
                return "MEDIUM";
            } else {
                return "LONG";
            }
        };

        f3 = (String v) -> {
            if ("A".equals(mode)) {
                if (v == null) return "A_NULL";
                return "A_" + v;
            } else {
                if (v == null) return "B_NULL";
                return "B_" + v;
            }
        };
    }

    public String runAll(String value) {
        return f1.apply(value) + "|" +
               f2.apply(value) + "|" +
               f3.apply(value);
    }}
