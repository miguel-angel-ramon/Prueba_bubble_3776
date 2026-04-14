package com.example;

public class ComplexS3776Demo {

    public String getText(boolean returnNull) {
        if (returnNull) {
            return null;
        }
        return "hello";
    }

    public String getAlwaysNullText() {
        return null;
    }

    public void testExpressionStmt() {
        String text = getText(true);
        System.out.println(text.length()); // S2259
    }

    public int testVariableDeclaration() {
        String text = getAlwaysNullText();
        int len = text.length(); // S2259
        return len;
    }

    public int testReturnStmt() {
        String text = getAlwaysNullText();
        return text.length(); // S2259
    }

    public void testAlreadyGuarded() {
        String text = getAlwaysNullText();

        if (text != null) {
            System.out.println(text.length()); // ya protegido, no debería tocarse
        }
    }

    public void testChainedCall() {
        System.out.println(getAlwaysNullText().length()); // chained, no debería tocarse
    }

    public void testUppercaseVariable() {
        String TEXT = getAlwaysNullText();
        System.out.println(TEXT.length()); // uppercase, tu fixer actual lo ignora
    }

    public void testMultipleStatements() {
        String text = getAlwaysNullText();

        System.out.println(text.length()); // S2259
        System.out.println(text.length()); // S2259
    }

    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();

        app.testExpressionStmt();

        System.out.println(app.testVariableDeclaration());
        System.out.println(app.testReturnStmt());

        app.testAlreadyGuarded();
        app.testChainedCall();
        app.testUppercaseVariable();
        app.testMultipleStatements();
    }
}