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
     * Creates a successful person result with information about ALL the data
     *
     * @param data Stores data
     */
    public PersonResult(List<Person> data) {
        this.data = data;
        super.success = true;
    }

    /**
     * Creates a successful person result with information about the person
     *
     * @param person Stores person
     */
    public PersonResult(Person person) {
        this.person = person;
        super.success = true;
    }

    /**
     * Creates a failing person result with the given error message
     *
     * @param message Description of the error
     */
    public PersonResult(String message) {
        super.message = message;
        super.success = false;
    }

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
     * Adds a person to the list of data related to the user
     *
     * @param person Person related to the user
     */
    public void addData(Person person) {
        this.data.add(person);
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
