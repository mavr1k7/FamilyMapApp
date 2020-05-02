package com.teranpeterson.client.recycler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Item is used by ExpandableRecyclerAdapter and SearchAdapter as the default item to display in the
 * recycler view. Item and ItemViewHolder work together to bind items to their views. SearchAdapter
 * uses Items in a basic recycler view. ExpandableRecyclerAdapter uses it in a more complex expandable
 * recycler view. For more information, see the adapter classes.
 */
public class Item implements Parcelable {
    /**
     * This is the id for the corresponding event or person. Used by onClick listener to create an
     * event view or a person view respectively.
     */
    private String id;
    /**
     * Part 1 is the top layer of an item. Shows either a person's name or detailed event information
     */
    private String part1;
    /**
     * Part 2 is the bottom layer of an item. Shows either a person's name or a person's gender
     */
    private String part2;
    /**
     * True if the item is a person. False otherwise. Used to choose an icon
     */
    private boolean person;
    /**
     * True if the person is male. False otherwise. Used to choose an icon
     */
    private boolean male;

    /**
     * Constructor for an item. Sets all the information for a new item.
     *
     * @param id Person or Event ID
     * @param part1 Information to display in the top half
     * @param part2 Information to display in the botton half
     * @param person Whether or not the item corresponds to a person
     * @param male Whether or not the item corresponds to a male or female
     */
    public Item(String id, String part1, String part2, boolean person, boolean male) {
        this.id = id;
        this.part1 = part1;
        this.part2 = part2;
        this.person = person;
        this.male = male;
    }

    ///////////////////////
    // Default constructor and overrides for the Parcelable class. See ExpandableRecyclerAdapter for
    // more info
    private Item(Parcel in) {
        Log.d("Item", in.toString()); // Literally only here to suppress a warning
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
    // End
    ////////////////

    /**
     * Returns the ID for the corresponding person or event
     * @return Person or Event ID
     */
    public String getId() { return this.id; }

    /**
     * Returns part 1 of the item
     *
     * @return Information from the top part of the item
     */
    String getPart1() {
        return this.part1;
    }

    /**
     * Returns part 2 of the item
     *
     * @return Information from the bottom part of the item
     */
    String getPart2() {
        return this.part2;
    }

    /**
     * Returns true if the item corresponds to a person. False if it corresponds to an event.
     *
     * @return True if person. False if event.
     */
    public boolean isPerson() {
        return this.person;
    }

    /**
     * Returns true if the item corresponds to a male. False if it corresponds to a female.
     *
     * @return True if male. False if female.
     */
    boolean isMale() {
        return this.male;
    }
}
