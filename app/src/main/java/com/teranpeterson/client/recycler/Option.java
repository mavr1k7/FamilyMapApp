package com.teranpeterson.client.recycler;

import android.os.Parcel;
import android.os.Parcelable;

public class Option implements Parcelable {
    private String type;
    private boolean enabled;

    public Option(String type, boolean enabled) {
        this.type = type;
        this.enabled = enabled;
    }

    protected Option(Parcel in) {
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

    public String getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
