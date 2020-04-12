package com.gc.baseutil;

import com.google.common.collect.ComparisonChain;

public class Person implements Comparable<Person> {

    private String firstName;
    private String lastName;
    private int zipCode;

    public Person(String firstName, String lastName,int zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 一般写法
     * @param other
     * @return
     */
    @Override
    public int compareTo(Person other) {
        int cmp = lastName.compareTo(other.lastName);
        if (cmp != 0) {
            return cmp;
        }
        cmp = firstName.compareTo(other.firstName);
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(zipCode, other.zipCode);
    }

    /**
     * Guava写法
     * @return
     */
    public int compareGuava(Person other){
        return ComparisonChain.start()
                .compare(this.firstName, other.firstName)
                .compare(this.lastName, other.lastName)
                .compare(this.zipCode,other.zipCode)
                .result();
    }
}
