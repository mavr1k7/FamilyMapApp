package com.teranpeterson.client.recycler;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * OptionViewHolder is used by OptionAdapter to link recycler views with Options. Each Option has a
 * name, a description, and a switch. This information is set in the onBindViewHolder() method. See
 * the OptionAdapter for more information.
 */
class OptionViewHolder extends ChildViewHolder {
    /**
     * Link to the title of the recycler_option layout
     */
    private TextView title;
    /**
     * Link to the label of the recycler_option layout
     */
    private TextView label;
    /**
     * Link to the switch of the recycler_option layout
     */
    private Switch enabled;

    /**
     * Constructor for the OptionViewHolder
     */
    OptionViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.recycler_option_title);
        label = itemView.findViewById(R.id.recycler_option_label);
        enabled = itemView.findViewById(R.id.recycler_option_switch);
    }

    /**
     * Sets the title to information stored in an Option. In this case, an event type
     */
    void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * Sets the label to information stored in an Option. In this case, default text with an event type
     */
    void setLabel(String label) {
        this.label.setText(label);
    }

    /**
     * Sets the switch to enabled/disabled based on information stored in an option.
     */
    void setEnabled(boolean enabled) {
        this.enabled.setChecked(enabled);
    }

    /**
     * Returns the switch object. Used to enable a setOnCheckedChangeListener
     */
    Switch getSwitch() { return enabled; }
}
