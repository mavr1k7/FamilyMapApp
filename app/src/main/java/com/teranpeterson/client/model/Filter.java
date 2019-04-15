package com.teranpeterson.client.model;

import java.util.Map;

public class Filter {
    private static Filter sFilter;

    private boolean mFather;
    private boolean mMother;
    private boolean mMale;
    private boolean mFemale;

    private Filter() {
        mFather = true;
        mMother = true;
        mMale = true;
        mFemale = true;
    }

    public static Filter get() {
        if (sFilter == null) {
            sFilter = new Filter();
        }
        return sFilter;
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

    public boolean filter(Event event) {
        Person person = FamilyTree.get().getPerson(event.getPersonID());
        for (Map.Entry entry : FamilyTree.get().getEventTypes().entrySet()) {
            if (entry.getKey().equals(event.getEventType().toLowerCase())) {
                if (!((boolean) entry.getValue())) {
                    return false;
                }
            }
        }
        if (person.getSide() != null) {
            if (person.getSide().equals("father") && !mFather) return false;
            if (person.getSide().equals("mother") && !mMother) return false;
        }
        if (person.getGender() != null) {
            if (person.getGender().equals("m") && !mMale) return false;
            if (person.getGender().equals("f") && !mFemale) return false;
        }
        return true;
    }
}
