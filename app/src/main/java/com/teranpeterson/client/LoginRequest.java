package com.teranpeterson.client;

/**
 * Contains information about the Login Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class LoginRequest extends Request {
    /**
     * User's userName
     */
    private String userName;
    /**
     * Password to authenticate with
     */
    private String password;

    public LoginRequest() {

    }

    /**
     * Creates a login request with the given information
     *
     * @param userName User's userName
     * @param password Password to authenticate with
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
     * Gets the password to authenticate with
     *
     * @return Password to authenticate with
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password to authenticate with
     *
     * @param password Password to authenticate with
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
