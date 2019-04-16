package com.teranpeterson.client.result;

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
    private boolean success;
    /**
     * Information about errors that happened while processing the request
     */
    private String message;

    /**
     * Checks if the request was successful or not
     *
     * @return True if successful, otherwise false
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the error message that occurred while processing the request
     *
     * @return Information about the error
     */
    public String getMessage() {
        return message;
    }
}
