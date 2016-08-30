package com.xu.java8.lambda;

/**
 * @author Benjamin Winterberg
 */
public class Person {
    public String firstName;
    public String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString(){
        return firstName+lastName;
    }
}