package com.teranpeterson.client.recycler;

import android.view.View;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * HeaderViewHolder is used by the ExpandableRecyclerAdapter to link a header to a view. Each header
 * only has a title and it is set in the onBindGroupViewHolder() method. See the ExpandableRecyclerAdapter
 * for more information
 */
class HeaderViewHolder extends GroupViewHolder {
    /**
     * Link to the title TextView in the recycler_header layout
     */
    private TextView title;

    /**
     * Constructor for HeaderViewHolder
     */
    HeaderViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.recycler_header_title);
    }

    /**
     * Sets the header for each section based on the value stored in the header object
     */
    void setTitle(ExpandableGroup group) {
        title.setText(group.getTitle());
    }
}
