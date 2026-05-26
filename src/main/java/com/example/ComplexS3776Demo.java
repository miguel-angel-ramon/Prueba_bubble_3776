package com.example;

public class ComplexS3776Demo {

    private String USER_NAME = "Miguel"; // S116
    private String _user_email = "miguel@email.com"; // S116
    private int user_age = 25; // S116
    private String UserAddress = "Sevilla"; // S116
    private String USER_PHONE_NUMBER = "123456789"; // S116

    public void testDirectFieldAccess() {
        System.out.println(USER_NAME); // S116
        System.out.println(_user_email); // S116
    }

    public void testThisFieldAccess() {
        System.out.println(this.USER_NAME); // S116
        System.out.println(this._user_email); // S116
    }

    public void testReturnInvalidField() {
        System.out.println(getUserAddress());
    }

    public String getUserAddress() {
        return UserAddress; // S116
    }

    public int getUserAge() {
        return user_age; // S116
    }

    public void testAssignments() {
        USER_NAME = "Carlos"; // S116
        _user_email = "carlos@email.com"; // S116
        user_age = 30; // S116
    }

    public void testMultipleUsages() {
        System.out.println(USER_NAME); // S116
        System.out.println(USER_NAME.toLowerCase()); // S116
        System.out.println(USER_NAME.length()); // S116
    }

    public void testNestedBlock() {
        {
            System.out.println(_user_email); // S116
        }
    }

    public void testConditionalFlow(boolean flag) {
        if (flag) {
            USER_PHONE_NUMBER = "999999999"; // S116
        }

        System.out.println(USER_PHONE_NUMBER); // S116
    }

    public void testAlreadyValidField() {
        String localValue = "ok";
        System.out.println(localValue);
    }

    public void testUppercaseOnlyField() {
        String result = USER_NAME + UserAddress; // S116
        System.out.println(result);
    }

    public void testFieldAccessInsideMethodCall() {
        printValue(USER_NAME); // S116
        printValue(_user_email); // S116
    }

    public void printValue(String value) {
        System.out.println(value);
    }

    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();

        app.testDirectFieldAccess();
        app.testThisFieldAccess();

        System.out.println(app.getUserAddress());
        System.out.println(app.getUserAge());

        app.testAssignments();
        app.testMultipleUsages();
        app.testNestedBlock();

        app.testConditionalFlow(true);

        app.testAlreadyValidField();
        app.testUppercaseOnlyField();
        app.testFieldAccessInsideMethodCall();
    }
}