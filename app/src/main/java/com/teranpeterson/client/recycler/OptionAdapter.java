package com.teranpeterson.client.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;

import java.util.List;

/**
 * OptionAdapter is the main class used by the Filter Activity to display a list of all the event types
 * and their corresponding states. Each item in the recycler view is made with the Option and
 * OptionViewHolder classes. This uses the default Recycler View made by Google. For more information
 * go to https://developer.android.com/reference/android/support/v7/widget/RecyclerView.
 */
public class OptionAdapter extends RecyclerView.Adapter<OptionViewHolder> {
    /**
     * List of Event types to display in the recycler view
     */
    private List<Option> data;
    /**
     * Inflater used in various methods to load layouts
     */
    private LayoutInflater inflater;

    /**
     * Constructor used to set the data and inflater
     */
    public OptionAdapter(Context context, List<Option> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * Sets the option layout to recycler_option and enables the OptionViewHolder
     */
    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recycler_option, viewGroup, false);
        return new OptionViewHolder(view);
    }

    /**
     * Binds each view to an option. Each view is given values pulled from the option and a
     * OnCheckedChangeListener event is enabled. Clicking on a switch changes the state of that
     * event filter in the Filter singleton.
     */
    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder viewHolder, int i) {
        final Option option = data.get(i);
        viewHolder.setTitle(option.getType());
        viewHolder.setLabel("FILTER BY " + option.getType().toUpperCase() + " EVENTS");
        viewHolder.setEnabled(option.isEnabled());

        viewHolder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FamilyTree.get().updateEventEnabled(option.getType(), isChecked);
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
