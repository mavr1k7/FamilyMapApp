package com.teranpeterson.client.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teranpeterson.client.R;
import com.teranpeterson.client.ui.EventActivity;
import com.teranpeterson.client.ui.PersonActivity;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * ExpandableRecyclerAdapter is the main class used by the Person Activity to display an Expandable
 * Recycler view. The Header and HeaderViewHolder classes make up each section header. The Item and
 * ItemViewHolder classes make up each individual item in the dropdown. This class uses a third party
 * resource made by ThoughtBot found at https://github.com/thoughtbot/expandable-recycler-view.
 */
public class ExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<HeaderViewHolder, ItemViewHolder> {
    /**
     * Constructor used to set each group with a header and a list of sub items
     */
    public ExpandableRecyclerAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    /**
     * Sets the header layout to recycler_header and enables the HeaderViewHolder
     */
    @Override
    public HeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header, parent, false);
        return new HeaderViewHolder(view);
    }

    /**
     * Sets the item layout to recycler_item and enables the ItemViewHolder
     */
    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    /**
     * Binds each view to an item. Each view is given values pulled from that item and an onClick event
     * is enabled. Clicking on a person redirects to a person activity. Clicking on an event redirects
     * to an event activity (aka, map fragment with a bundle value).
     */
    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Item item = (Item) group.getItems().get(childIndex);
        holder.setText1(item.getPart1());
        holder.setText2(item.getPart2());
        holder.setIcon(item.isPerson(), item.isMale());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isPerson()) {
                    v.getContext().startActivity(PersonActivity.newIntent(v.getContext(), item.getId()));
                } else {
                    v.getContext().startActivity(EventActivity.newIntent(v.getContext(), item.getId()));
                }
            }
        });
    }

    /**
     * Sets the groups title
     */
    @Override
    public void onBindGroupViewHolder(HeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
    }
}
