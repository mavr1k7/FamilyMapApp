package com.teranpeterson.client.recycler;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String id;
    private String part1;
    private String part2;
    private boolean person;
    private boolean male;

    public Item(String id, String part1, String part2, boolean person, boolean male) {
        this.id = id;
        this.part1 = part1;
        this.part2 = part2;
        this.person = person;
        this.male = male;
    }

    protected Item(Parcel in) {

    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getId() { return this.id; }

    public String getPart1() {
        return this.part1;
    }

    public String getPart2() {
        return this.part2;
    }

    public boolean isPerson() {
        return this.person;
    }

    public boolean isMale() {
        return this.male;
    }
}
