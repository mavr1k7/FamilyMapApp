package com.teranpeterson.client.request;

/**
 * Contains information about the Register Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class RegisterRequest extends Request {
    /**
     * User's userName
     */
    private String userName;
    /**
     * User's password
     */
    private String password;
    /**
     * User's email
     */
    private String email;
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's last name
     */
    private String lastName;
    /**
     * User's gender ('m' or 'f')
     */
    private String gender;

    /**
     * Creates a register request with the given information
     *
     * @param userName  User's userName
     * @param password  User's password
     * @param email     User's email
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param gender    User's gender
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    /**
     * Gets the user's email
     *
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email
     *
     * @param email User's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's first name
     *
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name
     *
     * @param firstName User's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name
     *
     * @return User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name
     *
     * @param lastName User's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's gender ('m' or 'f')
     *
     * @return User's gender ('m' or 'f')
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender ('m' or 'f')
     *
     * @param gender User's gender ('m' or 'f')
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}
