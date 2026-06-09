package com.example;

public class ComplexS3776Demo {

```
public void testUnusedVariableInitializer() {
    String text = null; // S1854
    text = "Miguel";
    System.out.println(text);
}

public void testUnusedAssignmentExpression() {
    String text;

    text = null; // S1854
    text = "Miguel";

    System.out.println(text);
}

public void testMultipleUnusedAssignments() {
    String name = null; // S1854

    name = "Carlos";

    int age = 0; // S1854
    age = 25;

    System.out.println(name);
    System.out.println(age);
}

public void testInsideConditional() {
    String value;

    if (true) {
        value = null; // S1854
        value = "OK";
    }

    System.out.println(value);
}

public void testValidAssignmentShouldRemain() {
    String value = "Miguel";

    System.out.println(value);
}

public void testAssignmentUsedLaterShouldRemain() {
    String value;

    value = "Carlos";
    System.out.println(value);
}

public static void main(String[] args) {
    ComplexS3776Demo app = new ComplexS3776Demo();

    app.testUnusedVariableInitializer();
    app.testUnusedAssignmentExpression();
    app.testMultipleUnusedAssignments();
    app.testInsideConditional();
    app.testValidAssignmentShouldRemain();
    app.testAssignmentUsedLaterShouldRemain();
}
```

}
