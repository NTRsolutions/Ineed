package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.SearchModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder>
{
    public Context context;
    List<SearchModel.ProfileListBean> searchlist=new ArrayList();
    Clickitem clickitem;
    public interface Clickitem
    {
        void itemclick(View v,int position);
    }
    public SearchAdapter(Context context, List<SearchModel.ProfileListBean> searchlist) {
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
        holder.txt_tech.setText(searchlist.get(position).getDesignation());
        holder.txt_exper.setText(searchlist.get(position).getTotalExperience()+" Year");
        holder.txt_age.setText(searchlist.get(position).getUser_age()+" Year");
        holder.txt_country.setText(searchlist.get(position).getUser_nationality());
        Log.d(TAG, "onBindViewHolder: "+ ApiList.IMG_BASE+searchlist.get(position).getUser_image());
        if (searchlist.get(position).getUser_image()!=null&&searchlist.get(position).getUser_image().length()>0){
            Glide.with(context).load(ApiList.IMG_BASE+searchlist.get(position).getUser_image())
                    .apply(RequestOptions.circleCropTransform()).into(holder.profimg);
        } else {
            Glide.with(context).load(R.drawable.gfgf)
                    .apply(RequestOptions.circleCropTransform()).into(holder.profimg);
        }
    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }
    public void setclick(Clickitem clickitem)
    {
        this.clickitem=clickitem;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profimg;
        TextView txt_tech,txt_exper,txt_age,txt_country,txtDegree;

        public Viewholder(View itemView) {
            super(itemView);
            profimg=itemView.findViewById(R.id.img_profile);
            txt_tech=itemView.findViewById(R.id.txtName);
            txt_exper=itemView.findViewById(R.id.txtExp);
            txt_age=itemView.findViewById(R.id.txtAge);
            txtDegree=itemView.findViewById(R.id.txtDegree);
            txt_country=itemView.findViewById(R.id.txtNationality);
            txtDegree.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickitem.itemclick(itemView,getAdapterPosition());
        }
    }
}
