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
import com.androidtutorialpoint.ineed.proj.models.ViewedProflie;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ask on 12/6/2017.
 */

public class ProfileViewdAdapter extends RecyclerView.Adapter<ProfileViewdAdapter.MyViewHolder> {
    Context context;
    List<ViewedProflie.ProfileListBean> viewedProflie;
    private Clicklistner clicklistner;

    public interface Clicklistner
    {
        void itemclick(View v, int post);
    }

    public ProfileViewdAdapter(Context context, List<ViewedProflie.ProfileListBean> viewedProflie) {
        this.context = context;
        this.viewedProflie = viewedProflie;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (viewedProflie !=null){
          holder.txtAge.setText(viewedProflie.get(position).getUser_age());
          holder.txtCurrentLocation.setText(viewedProflie.get(position).getUser_permitcountry());
          holder.txtDegree.setText(viewedProflie.get(position).getDesignation());
          holder.txtExp.setText(viewedProflie.get(position).getTotalExperience()+" year");
          holder.txtName.setText(viewedProflie.get(position).getUser_name());


            Log.d(TAG, "onBindViewHolder: "+ApiList.IMG_BASE+viewedProflie.get(position).getUser_image());
          if (viewedProflie.get(position).getUser_image()!=null&&viewedProflie.get(position).getUser_image().length()>0){
              Glide.with(context).load(ApiList.IMG_BASE+viewedProflie.get(position).getUser_image())
                      .apply(RequestOptions.circleCropTransform()).into(holder.imgProfile);
          } else {
              Glide.with(context).load(R.drawable.gfgf)
                      .apply(RequestOptions.circleCropTransform()).into(holder.imgProfile);
          }
        }

    }
    public void setclick(Clicklistner clicklistner1)
    {
        clicklistner=clicklistner1;
    }

    @Override
    public int getItemCount() {
        return viewedProflie.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtExp, txtDegree , txtCurrentLocation, txtAge;
        ImageView imgProfile;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtExp = (TextView) itemView.findViewById(R.id.txtExp);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDegree = (TextView) itemView.findViewById(R.id.txtDegree);
            txtCurrentLocation = (TextView) itemView.findViewById(R.id.txtNationality);
            txtAge = itemView.findViewById(R.id.txtAge);
            imgProfile = itemView.findViewById(R.id.img_profile);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(clicklistner!=null)
            {
//                checkBox.setButtonDrawable(R.drawable.radioselector);
                clicklistner.itemclick(itemView,getAdapterPosition());
            }
        }
        }

}
