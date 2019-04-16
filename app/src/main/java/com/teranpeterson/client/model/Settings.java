package com.teranpeterson.client.model;

import android.graphics.Color;

/**
 * Settings is a singleton that is used to store the settings options set by the user. THe user has
 * the option to change and enable/disable lines and their colors, change the map type, resync all the
 * data from the server, and logout. Settings are controlled by the settings activity.
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class Settings {
    /**
     * Singleton object
     */
    private static Settings sSettings;
    /**
     * Life Story Lines option
     */
    private boolean mLifeStoryLines;
    /**
     * Family Tree Lines option
     */
    private boolean mFamilyTreeLines;
    /**
     * Spouse Lines option
     */
    private boolean mSpouseLines;
    /**
     * Color for Life Story Lines
     */
    private String mLifeStoryLinesColor;
    /**
     * Color for Family Tree Lines
     */
    private String mFamilyTreeLinesColor;
    /**
     * Color for Spouse Lines
     */
    private String mSpouseLinesColor;
    /**
     * Map type (Normal, Hybrid, Terrain, Satellite)
     */
    private String mMapType;

    /**
     * Constructor for singleton. Private so that access must be through the get() method. Sets default
     * settings.
     */
    private Settings() {
        mLifeStoryLines = true;
        mFamilyTreeLines = true;
        mSpouseLines = true;
        mLifeStoryLinesColor = "Black";
        mFamilyTreeLinesColor = "Blue";
        mSpouseLinesColor = "Red";
        mMapType = "Normal";
    }

    /**
     * Returns the Settings singleton with all the current settings.
     *
     * @return Settings singleton
     */
    public static Settings get() {
        if (sSettings == null) {
            sSettings = new Settings();
        }
        return sSettings;
    }

    /**
     * Returns the current state of the Life Story Lines setting
     *
     * @return True if life story lines should be displayed. False otherwise
     */
    public boolean isLifeStoryLines() {
        return mLifeStoryLines;
    }

    /**
     * Updates the current state of the Life Story Lines setting
     *
     * @param lifeStoryLines True if life story lines should be displayed. False otherwise
     */
    public void setLifeStoryLines(boolean lifeStoryLines) {
        this.mLifeStoryLines = lifeStoryLines;
    }

    /**
     * Returns the current state of the Family Tree Lines setting
     *
     * @return True if family tree lines should be displayed. False otherwise
     */
    public boolean isFamilyTreeLines() {
        return mFamilyTreeLines;
    }

    /**
     * Updates the current state of the Family Tree Lines setting
     *
     * @param familyTreeLines True if family tree lines should be displayed. False otherwise
     */
    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.mFamilyTreeLines = familyTreeLines;
    }

    /**
     * Returns the current state of the Spouse Lines setting
     *
     * @return True if spouse lines should be displayed. False otherwise
     */
    public boolean isSpouseLines() {
        return mSpouseLines;
    }

    /**
     * Updates the current state of the Spouse Lines setting
     *
     * @param spouseLines True if spouse lines should be displayed. False otherwise
     */
    public void setSpouseLines(boolean spouseLines) {
        this.mSpouseLines = spouseLines;
    }

    /**
     * Returns the color to use for Life Story Lines on the map
     *
     * @return Color name
     */
    public String getLifeStoryLinesColor() {
        return mLifeStoryLinesColor;
    }

    /**
     * Returns the color value to use for Life Story Lines on the map
     *
     * @return Color value
     */
    public int getLifeStoryLinesColorValue() {
        return colorValue(mLifeStoryLinesColor);
    }

    /**
     * Updates the color to use for Life Story Liens on the map
     *
     * @param lifeStoryLinesColor Color name
     */
    public void setLifeStoryLinesColor(String lifeStoryLinesColor) {
        this.mLifeStoryLinesColor = lifeStoryLinesColor;
    }

    /**
     * Returns the color to use for Family Tree Lines on the map
     *
     * @return Color name
     */
    public String getFamilyTreeLinesColor() {
        return mFamilyTreeLinesColor;
    }

    /**
     * Returns the color value to use for Family Tree Lines on the map
     *
     * @return Color value
     */
    public int getFamilyTreeLinesColorValue() {
        return colorValue(mFamilyTreeLinesColor);
    }

    /**
     * Updates the color to use for Family Tree Liens on the map
     *
     * @param familyTreeLinesColor Color name
     */
    public void setFamilyTreeLinesColor(String familyTreeLinesColor) {
        this.mFamilyTreeLinesColor = familyTreeLinesColor;
    }

    /**
     * Returns the color to use for Spouse Lines on the map
     *
     * @return Color name
     */
    public String getSpouseLinesColor() {
        return mSpouseLinesColor;
    }

    /**
     * Returns the color value to use for Spouse Lines on the map
     *
     * @return Color value
     */
    public int getSpouseLinesColorValue() {
        return colorValue(mSpouseLinesColor);
    }

    /**
     * Updates the color to use for Spouse Lines on the map
     *
     * @param spouseLinesColor Color name
     */
    public void setSpouseLinesColor(String spouseLinesColor) {
        this.mSpouseLinesColor = spouseLinesColor;
    }

    /**
     * Returns the type of style to display the map with
     *
     * @return Map type (Normal, Hybrid, Terrain, Satellite)
     */
    public String getMapType() {
        return mMapType;
    }

    /**
     * Sets the type of style to display the map with
     *
     * @param mMapType Map type (Normal, Hybrid, Terrain, Satellite)
     */
    public void setMapType(String mMapType) {
        this.mMapType = mMapType;
    }

    /**
     * Returns the position value of a given color in the dropdown menu on the UI
     *
     * @param color Color name
     * @return Color position
     */
    public int getColorInt(String color) {
        switch (color) {
            case "Red":
                return 0;
            case "Yellow":
                return 1;
            case "Green":
                return 2;
            case "Blue":
                return 3;
            case "Black":
                return 4;
            default:
                return 0;
        }
    }

    /**
     * Returns the position value of the current map type in the dropdown menu on the UI
     *
     * @return Map type position
     */
    public int getMapTypeInt() {
        switch (mMapType) {
            case "Normal":
                return 0;
            case "Hybrid":
                return 1;
            case "Satellite":
                return 2;
            case "Terrain":
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Converts the name of a color to its corresponding value to display on the map
     *
     * @param color Color name
     * @return Color value
     */
    private int colorValue(String color) {
        switch (color) {
            case "Red":
                return Color.RED;
            case "Yellow":
                return Color.YELLOW;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            case "Black":
                return Color.BLACK;
            default:
                return Color.TRANSPARENT;
        }
    }
}
