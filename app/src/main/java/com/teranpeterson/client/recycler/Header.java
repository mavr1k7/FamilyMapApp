package com.teranpeterson.client.recycler;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Header is used by the ExpandableRecyclerAdapter to display a header for each group. See the
 * ExpandableRecyclerAdapter class for more information
 */
public class Header extends ExpandableGroup<Item> {
    /**
     * Creates a header item
     */
    public Header(String title, List<Item> items) {
        super(title, items);
    }
}
