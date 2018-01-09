package com.androidtutorialpoint.ineed.proj.viewholders;

import android.view.View;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HelpViewDetailHolder extends ChildViewHolder {
    TextView helpdetail;
    public HelpViewDetailHolder(View itemView) {
        super(itemView);
        helpdetail=(TextView)itemView.findViewById(R.id.txt_helpdetail);
    }
    public void SetHelpdetail(String helptext)
    {
        helpdetail.setText(helptext);
    }
}
