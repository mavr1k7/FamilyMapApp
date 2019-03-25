package com.teranpeterson.client.request;

/**
 * Contains information about the Person(s) Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class PersonRequest {
    /**
     * ID of the person to load
     */
    private String personID;
    /**
     * Authentication token of the current user (used to find related persons)
     */
    private String authToken;

    /**
     * Creates a person request for ALL persons related to the current user
     *
     * @param authToken Authentication token of the current user
     */
    public PersonRequest(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Creates a person request for the given personID
     *
     * @param personID  ID of the person to load
     * @param authToken Authentication token of the current user
     */
    public PersonRequest(String personID, String authToken) {
        this.personID = personID;
        this.authToken = authToken;
    }

    /**
     * Gets the ID of the person to load
     *
     * @return ID of the person to load
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Sets the ID of the person to load
     *
     * @param personID ID of the person to load
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Gets the user's authentication token
     *
     * @return User's authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the user's authentication token
     *
     * @param authToken User's authentication token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
