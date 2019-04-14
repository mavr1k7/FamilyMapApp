package com.teranpeterson.client.model;

public class Settings {
    private static Settings sSettings;

    private boolean mLifeStoryLines;
    private boolean mFamilyTreeLines;
    private boolean mSpouseLines;

    private String mLifeStoryLinesColor;
    private String mFamilyTreeLinesColor;
    private String mSpouseLinesColor;
    private String mMapType;

    private Settings() {
        mLifeStoryLines = true;
        mFamilyTreeLines = true;
        mSpouseLines = true;
        mLifeStoryLinesColor = "Red";
        mFamilyTreeLinesColor = "White";
        mSpouseLinesColor = "Blue";
        mMapType = "Normal";
    }

    public static Settings get() {
        if (sSettings == null) {
            sSettings = new Settings();
        }
        return sSettings;
    }

    public boolean isLifeStoryLines() {
        return mLifeStoryLines;
    }

    public void setLifeStoryLines(boolean mLifeStoryLines) {
        this.mLifeStoryLines = mLifeStoryLines;
    }

    public boolean isFamilyTreeLines() {
        return mFamilyTreeLines;
    }

    public void setFamilyTreeLines(boolean mFamilyTreeLines) {
        this.mFamilyTreeLines = mFamilyTreeLines;
    }

    public boolean isSpouseLines() {
        return mSpouseLines;
    }

    public void setSpouseLines(boolean mSpouseLines) {
        this.mSpouseLines = mSpouseLines;
    }

    public String getLifeStoryLinesColor() {
        return mLifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(String mLifeStoryLinesColor) {
        this.mLifeStoryLinesColor = mLifeStoryLinesColor;
    }

    public String getFamilyTreeLinesColor() {
        return mFamilyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(String mFamilyTreeLinesColor) {
        this.mFamilyTreeLinesColor = mFamilyTreeLinesColor;
    }

    public String getSpouseLinesColor() {
        return mSpouseLinesColor;
    }

    public void setSpouseLinesColor(String mSpouseLinesColor) {
        this.mSpouseLinesColor = mSpouseLinesColor;
    }

    public String getMapType() {
        return mMapType;
    }

    public void setMapType(String mMapType) {
        this.mMapType = mMapType;
    }
}
