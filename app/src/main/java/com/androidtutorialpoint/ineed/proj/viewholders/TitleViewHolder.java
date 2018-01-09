package com.androidtutorialpoint.ineed.proj.viewholders;

import android.view.View;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class TitleViewHolder extends GroupViewHolder {
   TextView titletext;
    public TitleViewHolder(View itemView) {
        super(itemView);
        titletext=(TextView)itemView.findViewById(R.id.txt_title);

    }
    public void setTitletext(String titletext1)
    {
        titletext.setText(titletext1);
    }
}
