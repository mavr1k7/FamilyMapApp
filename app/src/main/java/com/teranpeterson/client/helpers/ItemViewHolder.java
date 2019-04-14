package com.teranpeterson.client.helpers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ItemViewHolder extends ChildViewHolder {
    private TextView text1;
    private TextView text2;
    private ImageView icon;

    public ItemViewHolder(View itemView) {
        super(itemView);
        text1 = itemView.findViewById(R.id.recycler_item_text1);
        text2 = itemView.findViewById(R.id.recycler_item_text2);
        icon = itemView.findViewById(R.id.recycler_item_icon);
    }

    public void setText1(String text) {
        text1.setText(text);
    }

    public void setText2(String text) {
        text2.setText(text);
    }

    public void setIcon(boolean person, boolean male) {
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
