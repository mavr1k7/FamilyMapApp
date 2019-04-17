package com.teranpeterson.client.model;

import java.util.Map;

/**
 * Filter is a singleton that is used to store the filter options set by the user. The user has the
 * option to enable/disable which events are shown throughout the system. These filters include filtering
 * by event type, side of the family, and gender. Filters are controlled by the filter activity. Event
 * types are generated and stored by the FamilyTree singleton.
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class Filter {
    /**
     * Singleton object
     */
    private static Filter sFilter;
    /**
     * Father's side filter option
     */
    private boolean mFather;
    /**
     * Mother's side filter option
     */
    private boolean mMother;
    /**
     * Male filter option
     */
    private boolean mMale;
    /**
     * Female filter option
     */
    private boolean mFemale;

    /**
     * Constructor for singleton. Private so that access must be through the get() method
     */
    private Filter() {
        mFather = true;
        mMother = true;
        mMale = true;
        mFemale = true;
    }

    /**
     * Returns the Filter singleton with all of the current options.
     *
     * @return Filter singleton
     */
    public static Filter get() {
        if (sFilter == null) {
            sFilter = new Filter();
        }
        return sFilter;
    }

    /**
     * Returns the current state of the father's side filter
     *
     * @return True if enabled. False if disabled.
     */
    public boolean isFather() {
        return mFather;
    }

    /**
     * Updates the state of the father's side filter
     *
     * @param father True if enabled. False if disabled.
     */
    public void setFather(boolean father) {
        this.mFather = father;
    }

    /**
     * Returns the current state of the mother's side filter
     *
     * @return True if enabled. False if disabled.
     */
    public boolean isMother() {
        return mMother;
    }

    /**
     * Updates the state of the mother's side filter
     *
     * @param mother True if enabled. False if disabled.
     */
    public void setMother(boolean mother) {
        this.mMother = mother;
    }

    /**
     * Returns the current state of the male filter
     *
     * @return True if enabled. False if disabled.
     */
    public boolean isMale() {
        return mMale;
    }

    /**
     * Updates the state of the male filter
     *
     * @param male True if enabled. False if disabled.
     */
    public void setMale(boolean male) {
        this.mMale = male;
    }

    /**
     * Returns the current state of the female filter
     *
     * @return True if enabled. False if disabled.
     */
    public boolean isFemale() {
        return mFemale;
    }

    /**
     * Updates the state of the female filter
     *
     * @param female True if enabled. False if disabled.
     */
    public void setFemale(boolean female) {
        this.mFemale = female;
    }

    /**
     * Checks an event against the current filter options and returns True if the event should be displayed,
     * False otherwise. Each event is first checked by event type, then side, then gender. If it fails
     * any check, the method returns false. If it passes all checks, the method returns true;
     *
     * @param event Event to check against filter options
     * @return True if the event should be displayed. False otherwise.
     */
    boolean filter(Event event) {
        Person person = FamilyTree.get().getPerson(event.getPersonID());

        // Filter by event type using the mEventTypes map in the FamilyTree singleton
        for (Map.Entry entry : FamilyTree.get().getEventTypes().entrySet()) {
            if (entry.getKey().equals(event.getEventType().toLowerCase())) {
                if (!((boolean) entry.getValue())) {
                    return false;
                }
            }
        }

        // Filter by side of the family (based on the root user)
        if (person.getSide() != null) {
            if (person.getSide().equals("father") && !mFather) return false;
            if (person.getSide().equals("mother") && !mMother) return false;
        }

        // Filter by gender
        if (person.getGender() != null) {
            if (person.getGender().equals("m") && !mMale) return false;
            if (person.getGender().equals("f") && !mFemale) return false;
        }
        return true;
    }

    /**
     * Reset all the filters in the system
     */
    void reset() {
        for (Map.Entry entry : FamilyTree.get().getEventTypes().entrySet()) {
            FamilyTree.get().updateEventEnabled((String) entry.getKey(), true);
        }
        mFather = true;
        mMother = true;
        mMale = true;
        mFemale = true;
    }
}
