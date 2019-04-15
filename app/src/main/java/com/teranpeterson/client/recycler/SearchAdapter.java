package com.teranpeterson.client.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teranpeterson.client.R;
import com.teranpeterson.client.ui.PersonActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> data;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, List<Item> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recycler_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

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
                    Toast.makeText(v.getContext(), "Event ID: " + item.getId(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
