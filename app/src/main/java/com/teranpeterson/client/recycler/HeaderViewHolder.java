package com.teranpeterson.client.recycler;

import android.view.View;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class HeaderViewHolder extends GroupViewHolder {
    private TextView title;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.recycler_header_title);
    }

    public void setTitle(ExpandableGroup group) {
        title.setText(group.getTitle());
    }
}
