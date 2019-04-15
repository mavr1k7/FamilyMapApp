package com.teranpeterson.client.recycler;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Header extends ExpandableGroup<Item> {
    public Header(String title, List<Item> items) {
        super(title, items);
    }
}
