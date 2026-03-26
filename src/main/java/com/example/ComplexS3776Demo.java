package com.example;

import java.util.*;

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

    public void testAll() {

        // 🔹 Caso 1: API conocida (Thread.stop)
        Thread t = new Thread();
        t.stop();

        // 🔹 Caso 2: Deprecated con Javadoc (debería reemplazar)
        oldApiWithReplacement();

        // 🔹 Caso 3: Deprecated sin reemplazo (debería añadir SuppressWarnings)
        oldApiWithoutReplacement();
    }
    // =========================
    // 5) Demo main
    // =========================
    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();
        app.seedData();
        app.setupRules();

        System.out.println(app.process("u1", "normalize"));
        System.out.println(app.process("u1", "ageCheck"));
        System.out.println(app.process("u1", "modeRule"));
        System.out.println(app.process("u1", "orderSummary"));
        System.out.println(app.process("u3", "activeFlag"));
    }
}
