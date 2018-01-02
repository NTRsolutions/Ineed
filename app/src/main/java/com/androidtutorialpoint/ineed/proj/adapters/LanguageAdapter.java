package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.LanguageModel;

import java.util.ArrayList;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class LanguageAdapter extends ArrayAdapter<LanguageModel> {
    Context context;
    ArrayList<LanguageModel> models;
    LayoutInflater inflater;
    public LanguageAdapter(@NonNull Context context,  ArrayList<LanguageModel> models) {
        super(context, R.layout.languagerow, models);
        this.context=context;
        this.models=models;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=null;
        Viewholder viewholder=null;
        if(row==null)
        {
            row=inflater.inflate(R.layout.languagerow,parent,false);
            viewholder=new Viewholder(row);
            row.setTag(viewholder);
        }
        else
        {
            viewholder= (Viewholder) row.getTag();
        }
        viewholder.language.setText(models.get(position).getLanguage());
        viewholder.id.setText(models.get(position).getId());
        return row;
    }
    class Viewholder
    {
        TextView id,language;

        public Viewholder(View v) {
            id=(TextView) v.findViewById(R.id.txt_languageid);
            language=(TextView) v.findViewById(R.id.txt_languagename);
        }
    }
}
