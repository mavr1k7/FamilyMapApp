package com.teranpeterson.client.recycler;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class OptionViewHolder extends ChildViewHolder {
    private TextView title;
    private TextView label;
    private Switch enabled;

    public OptionViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.recycler_option_title);
        label = itemView.findViewById(R.id.recycler_option_label);
        enabled = itemView.findViewById(R.id.recycler_option_switch);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public void setEnabled(boolean enabled) {
        this.enabled.setChecked(enabled);
    }

    public Switch getSwitch() { return enabled; }
}
