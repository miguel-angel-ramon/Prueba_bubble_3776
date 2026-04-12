package com.example;

public class ComplexS3776Demo {

    /**
     * @deprecated use newApi instead
     */
    @Deprecated
    public void oldApi() {
        System.out.println("old api");
    }

    public void newApi() {
        System.out.println("new api");
    }

    public String getText(boolean returnNull) {
        if (returnNull) {
            return null;
        }
        return "hello";
    }

    public void test() {
        // Uso de método deprecated → dispara S1874
        oldApi();

        // Posible NullPointerException → dispara S2259
        String text = getText(true);
        System.out.println(text.length());
    }

    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();
        app.test();
    }
}