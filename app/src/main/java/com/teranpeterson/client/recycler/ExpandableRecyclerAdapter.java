package com.teranpeterson.client.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.Event;
import com.teranpeterson.client.recycler.HeaderViewHolder;
import com.teranpeterson.client.recycler.Item;
import com.teranpeterson.client.recycler.ItemViewHolder;
import com.teranpeterson.client.ui.EventActivity;
import com.teranpeterson.client.ui.PersonActivity;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<HeaderViewHolder, ItemViewHolder> {
    public ExpandableRecyclerAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public HeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

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

    @Override
    public void onBindGroupViewHolder(HeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
    }
}
