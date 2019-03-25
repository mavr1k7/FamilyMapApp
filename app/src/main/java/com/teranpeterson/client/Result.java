package com.teranpeterson.client;

/**
 * Base class that all result classes extend. Used to provide similar methods to all result objects.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class Result {
    /**
     * True if the request was successful, otherwise false
     */
    protected transient boolean success;
    /**
     * Information about errors that happened while processing the request
     */
    protected String message;

    /**
     * Checks if the request was successful or not
     *
     * @return True if successful, otherwise false
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success or failure of the request
     *
     * @param success True if successful, otherwise false
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the error message that occurred while processing the request
     *
     * @return Information about the error
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message that occurred while processing the request
     *
     * @param message Information about the error
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
