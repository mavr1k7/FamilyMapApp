package com.teranpeterson.client.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teranpeterson.client.R;
import com.teranpeterson.client.ui.EventActivity;
import com.teranpeterson.client.ui.PersonActivity;

import java.util.List;

/**
 * SearchAdapter is the main class used by the Search Activity to display a list of all the search
 * results. It uses the Item and ItemViewHolder classes to display persons and events that match the
 * given criteria. This uses the default Recycler View made by Google. For more information
 * go to https://developer.android.com/reference/android/support/v7/widget/RecyclerView.
 */
public class SearchAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    /**
     * List of Items (Persons or Events) to display in the recycler view
     */
    private List<Item> data;
    /**
     * Inflater used in various methods to load layouts
     */
    private LayoutInflater inflater;

    /**
     * Constructor used to set the data and inflater
     */
    public SearchAdapter(Context context, List<Item> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }


    /**
     * Sets the item layout to recycler_item and enables the ItemViewHolder
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recycler_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    /**
     * Binds each view to an item. Each view is given values pulled from that item and an onClick event
     * is enabled. Clicking on a person redirects to a person activity. Clicking on an event redirects
     * to an event activity (aka, map fragment with a bundle value).
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        final Item item = data.get(i);
        viewHolder.setText1(item.getPart1());
        viewHolder.setText2(item.getPart2());
        viewHolder.setIcon(item.isPerson(), item.isMale());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
     * Returns the total number of items to display
     */
    @Override
    public int getItemCount() {
        return data.size();
    }
}
