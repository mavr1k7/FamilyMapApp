package com.teranpeterson.client.result;

import com.teranpeterson.client.model.Person;

import java.util.List;

/**
 * Contains information about the results of a Person(s) Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class PersonResult extends Result {
    /**
     * List of all the people to return
     */
    private List<Person> data;
    /**
     * Person to return
     */
    private Person person;

    /**
     * Gets a list of all the data related to the user
     *
     * @return List of all the data related to the user
     */
    public List<Person> getData() {
        return data;
    }

    /**
     * Sets a list of all the data related to the user
     *
     * @param data List of all the data related to the user
     */
    public void setData(List<Person> data) {
        this.data = data;
    }

    /**
     * Gets the person related to the user
     *
     * @return Person related to the user
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the person related to the user
     *
     * @param person Person related to the user
     */
    public void setPerson(Person person) {
        this.person = person;
    }
}
