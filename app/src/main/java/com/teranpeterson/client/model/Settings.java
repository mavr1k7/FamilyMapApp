package com.teranpeterson.client.model;

import android.graphics.Color;

public class Settings {
    private static Settings sSettings;

    private boolean mLifeStoryLines;
    private boolean mFamilyTreeLines;
    private boolean mSpouseLines;

    private String mLifeStoryLinesColor;
    private String mFamilyTreeLinesColor;
    private String mSpouseLinesColor;
    private String mMapType;

    private String[] colorOptions;
    private String[] mapOptions;

    private Settings() {
        mLifeStoryLines = true;
        mFamilyTreeLines = true;
        mSpouseLines = true;
        mLifeStoryLinesColor = "Black";
        mFamilyTreeLinesColor = "Blue";
        mSpouseLinesColor = "Red";
        mMapType = "Normal";
        colorOptions = new String[] {
                "Red",
                "Yellow",
                "Green",
                "Blue",
                "Black"
        };
        mapOptions = new String[] {
                "Normal",
                "Hybrid",
                "Satellite",
                "Terrain"
        };
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

    public int getLifeStoryLinesColorValue() {
        return colorValue(mLifeStoryLinesColor);
    }

    public void setLifeStoryLinesColor(String mLifeStoryLinesColor) {
        this.mLifeStoryLinesColor = mLifeStoryLinesColor;
    }

    public String getFamilyTreeLinesColor() {
        return mFamilyTreeLinesColor;
    }

    public int getFamilyTreeLinesColorValue() {
        return colorValue(mFamilyTreeLinesColor);
    }

    public void setFamilyTreeLinesColor(String mFamilyTreeLinesColor) {
        this.mFamilyTreeLinesColor = mFamilyTreeLinesColor;
    }

    public String getSpouseLinesColor() {
        return mSpouseLinesColor;
    }

    public int getSpouseLinesColorValue() {
        return colorValue(mSpouseLinesColor);
    }

    public void setSpouseLinesColor(String mSpouseLinesColor) {
        this.mSpouseLinesColor = mSpouseLinesColor;
    }

    public String[] getColorOptions() {
        return this.colorOptions;
    }

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

    public String getMapType() {
        return mMapType;
    }

    public String[] getMapOptions() {
        return this.mapOptions;
    }

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

    public void setMapType(String mMapType) {
        this.mMapType = mMapType;
    }
}
