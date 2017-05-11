package com.xu.java8.lambda;

public class Person {
    public String firstName;
    public String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName,String cc) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void add(String firstName){

    }

    public String toString(){
        return firstName+lastName;
    }
}