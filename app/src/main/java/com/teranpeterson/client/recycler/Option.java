package com.teranpeterson.client.recycler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Option is used by OptionAdapter as a default item to display in the recycler view. Used to display
 * a list of all the event types in the FamilyTree singleton and their corresponding enabled/disabled
 * state. OptionViewHolder binds options to their views. See OptionAdapter for more information.
 */
public class Option implements Parcelable {
    /**
     * Options title. Used to store an event type (lowercase)
     */
    private String type;
    /**
     * Whether the option is enabled or disabled. Used to store an event filter state
     */
    private boolean enabled;

    /**
     * Constructor for an option. Sets all the information for a new option.
     *
     * @param type Event type (lowercase)
     * @param enabled True if event is enabled. False if disabled
     */
    public Option(String type, boolean enabled) {
        this.type = type;
        this.enabled = enabled;
    }

    ///////////////////////
    // Default constructor and overrides for the Parcelable class. See OptionAdapter for
    // more info
    private Option(Parcel in) {
        Log.d("Item", in.toString()); // Literally only here to suppress a warning
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel in) {
            return new Option(in);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
    // End
    ////////////////

    /**
     * Returns the event type that the option represents
     *
     * @return Event type (lowercase)
     */
    String getType() {
        return type;
    }

    /**
     * Returns the current state of the event filter
     *
     * @return True if event filter is enabled. False if disabled.
     */
    boolean isEnabled() {
        return enabled;
    }
}
