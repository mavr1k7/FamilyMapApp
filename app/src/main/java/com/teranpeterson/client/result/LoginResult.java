package com.teranpeterson.client.result;

/**
 * Contains information about the results of a Register Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class LoginResult extends Result {
    /**
     * Authentication token for the active user session
     */
    private String authToken;
    /**
     * UserName of registered user
     */
    private String userName;
    /**
     * PersonID of person created for the user
     */
    private String personID;

    /**
     * Gets the users new authentication token
     *
     * @return Active authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Gets the personID that corresponds with the user
     *
     * @return User's personID
     */
    public String getPersonID() {
        return personID;
    }
}
