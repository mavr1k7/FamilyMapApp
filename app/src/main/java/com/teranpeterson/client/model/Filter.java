package com.teranpeterson.client.model;

import android.support.annotation.NonNull;

public class Filter {
    private static Filter sFilter;

    private boolean mBirth;
    private boolean mBaptism;
    private boolean mMarriage;
    private boolean mDeath;
    private boolean mFather;
    private boolean mMother;
    private boolean mMale;
    private boolean mFemale;

    private Filter() {
        mBirth = false;
        mBaptism = false;
        mMarriage = false;
        mDeath = false;
        mFather = false;
        mMother = false;
        mMale = false;
        mFemale = false;
    }

    public static Filter get() {
        if (sFilter == null) {
            sFilter = new Filter();
        }
        return sFilter;
    }

    public boolean isBirth() {
        return mBirth;
    }

    public void setBirth(boolean birth) {
        this.mBirth = birth;
    }

    public boolean isBaptism() {
        return mBaptism;
    }

    public void setBaptism(boolean baptism) {
        this.mBaptism = baptism;
    }

    public boolean isMarriage() {
        return mMarriage;
    }

    public void setMarriage(boolean marriage) {
        this.mMarriage = marriage;
    }

    public boolean isDeath() {
        return mDeath;
    }

    public void setDeath(boolean death) {
        this.mDeath = death;
    }

    public boolean isFather() {
        return mFather;
    }

    public void setFather(boolean father) {
        this.mFather = father;
    }

    public boolean isMother() {
        return mMother;
    }

    public void setMother(boolean mother) {
        this.mMother = mother;
    }

    public boolean isMale() {
        return mMale;
    }

    public void setMale(boolean male) {
        this.mMale = male;
    }

    public boolean isFemale() {
        return mFemale;
    }

    public void setFemale(boolean female) {
        this.mFemale = female;
    }

    @NonNull
    @Override
    public String toString() {
        return "Birth: " + mBirth + ", Father: " + mFather + ", Male: " + mMale + "\n";
    }
}
