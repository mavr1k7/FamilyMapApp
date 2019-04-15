package com.teranpeterson.client.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.Filter;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionViewHolder> {
    private List<Option> data;
    private LayoutInflater inflater;

    public OptionAdapter(Context context, List<Option> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recycler_option, viewGroup, false);
        return new OptionViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder viewHolder, int i) {
        final Option option = data.get(i);
        viewHolder.setTitle(option.getType());
        viewHolder.setLabel("FILTER BY " + option.getType().toUpperCase() + " EVENTS");
        viewHolder.setEnabled(option.isEnabled());

        viewHolder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().updateEventEnabled(option.getType(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
