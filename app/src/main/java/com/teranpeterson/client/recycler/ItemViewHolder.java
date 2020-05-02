package com.teranpeterson.client.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * ItemViewHolder is used by ExpandableRecyclerAdapter and SearchAdapter to link recycler views with
 * Items. Each Item has an icon, a top section, a bottom section, and an id. This information is set
 * in the onBindChildViewHolder() and onBindViewHolder() methods. See the adapters for more information
 */
class ItemViewHolder extends ChildViewHolder {
    /**
     * Link to the top section in the recycler_item layout
     */
    private TextView text1;
    /**
     * Link to the bottom section in the recycler_item layout
     */
    private TextView text2;
    /**
     * Link to the icon in the recycler_item layout
     */
    private ImageView icon;

    /**
     * Constructor for the ItemViewHolder
     */
    ItemViewHolder(View itemView) {
        super(itemView);
        text1 = itemView.findViewById(R.id.recycler_item_text1);
        text2 = itemView.findViewById(R.id.recycler_item_text2);
        icon = itemView.findViewById(R.id.recycler_item_icon);
    }

    /**
     * Sets the top section to info stored in an Item
     */
    void setText1(String text) {
        text1.setText(text);
    }

    /**
     * Sets the bottom section to info stored in an Item
     */
    void setText2(String text) {
        text2.setText(text);
    }

    /**
     * Sets an icon to a male, female, or event based on the given info in an Item
     */
    void setIcon(boolean person, boolean male) {
        if (person) {
            if (male) {
                icon.setImageResource(R.drawable.boy);
            } else {
                icon.setImageResource(R.drawable.girl);
            }
        } else {
            icon.setImageResource(R.drawable.location);
        }
    }
}
