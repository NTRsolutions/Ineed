package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;

import java.util.ArrayList;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder>
{
    public Context context;
    ArrayList searchlist=new ArrayList();

    public SearchAdapter(Context context, ArrayList searchlist) {
        this.context = context;
        this.searchlist = searchlist;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView profimg;
        TextView txt_tech,txt_exper,txt_wrkpermit,txt_country;

        public Viewholder(View itemView) {
            super(itemView);
            profimg=itemView.findViewById(R.id.img_profile);
            txt_tech=itemView.findViewById(R.id.txtName);
            txt_exper=itemView.findViewById(R.id.txtExp);
            txt_wrkpermit=itemView.findViewById(R.id.txtAge);
            txt_country=itemView.findViewById(R.id.txtNationality);
        }
    }
}
