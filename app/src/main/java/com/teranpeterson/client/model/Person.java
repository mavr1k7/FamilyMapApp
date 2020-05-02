package com.teranpeterson.client.model;

/**
 * Persons are relatives and ancestors to users in the system. Each person contains information about their relationships
 * with other persons. The unique personID is also used to connect specific events to each individual. Persons (and events)
 * are not based on real data but are instead randomly generated.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class Person {
    /**
     * Unique id for the person
     */
    private String personID;
    /**
     * First name of the person
     */
    private String firstName;
    /**
     * Last name of the person
     */
    private String lastName;
    /**
     * Gender of the person ('m' or 'f')
     */
    private String gender;
    /**
     * Father of the person (can be null)
     */
    private String father;
    /**
     * Mother of the person (can be null)
     */
    private String mother;
    /**
     * Spouse of the person (can be null)
     */
    private String spouse;
    /**
     * Which side of the family the person is on
     */
    private transient String side;
    /**
     * Child of the person (can be null)
     */
    private transient String child;

    /**
     * Creates a person with the given data
     *
     * @param personID   Person's id
     * @param firstName  First name of the person
     * @param lastName   Last name of the person
     * @param gender     Gender of the person ('m' or 'f')
     * @param father     Father of the person (default null)
     * @param mother     Mother of the person (default null)
     * @param spouse     Spouse of the person (default null)
     */
    public Person(String personID, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * Gets the personID
     *
     * @return The person's id
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Gets the person's first name
     *
     * @return The person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the person's last name
     *
     * @return The person's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the person's gender
     *
     * @return 'm' or 'f'
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the person's gender written out
     *
     * @return 'Male' or 'Female'
     */
    public String getGenderFull() {
        if (gender.equals("m")) return "Male";
        if (gender.equals("f")) return "Female";
        return "";
    }

    /**
     * Returns true if the person is male. False otherwise.
     *
     * @return True if male
     */
    public boolean isMale() {
        if (gender.equals("m")) return true;
        if (gender.equals("f")) return false;
        return false;
    }

    /**
     * Gets the personID for the person's father
     *
     * @return Father's personID
     */
    public String getFather() {
        return father;
    }

    /**
     * Gets the personID for the person's mother
     *
     * @return Mother's personID
     */
    public String getMother() {
        return mother;
    }

    /**
     * Gets the personID for the person's spouse
     *
     * @return Spouse's personID
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Gets the side of the family the person is on (father or mother)
     *
     * @return Person's side of the family (father or mother)
     */
    String getSide() {
        return side;
    }

    /**
     * Sets which side of the family the person is on (father or mother)
     *
     * @param side Person's side of the family (father or mother)
     */
    void setSide(String side) {
        this.side = side;
    }

    /**
     * Gets the ID of the person's child
     *
     * @return ID of the person's child
     */
    public String getChild() {
        return child;
    }

    /**
     * Sets the ID of the person's child
     *
     * @param child ID of the person's child
     */
    void setChild(String child) {
        this.child = child;
    }
}
