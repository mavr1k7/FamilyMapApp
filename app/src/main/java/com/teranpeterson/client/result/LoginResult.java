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
     * Creates a failing register result with the given error message
     *
     * @param message Description of the error
     */
    public LoginResult(String message) {
        super.message = message;
        super.success = false;
    }

    /**
     * Creates a successful register result with users session information
     *
     * @param token    Authentication token for the active user session
     * @param userName UserName of registered user
     * @param id       PersonID of person created for the user
     */
    public LoginResult(String token, String userName, String id) {
        this.authToken = token;
        this.userName = userName;
        this.personID = id;
        super.success = true;
    }

    /**
     * Gets the users new authentication token
     *
     * @return Active authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets a new authentication token for the user
     *
     * @param authToken User's authentication token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Gets the user's userName
     *
     * @return User's userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's userName
     *
     * @param userName User's userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the personID that corresponds with the user
     *
     * @return User's personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Sets the personID that corresponds with the user
     *
     * @param personID User's personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
