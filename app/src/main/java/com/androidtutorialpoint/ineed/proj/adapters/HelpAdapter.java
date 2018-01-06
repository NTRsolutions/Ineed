package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.HelpCategoryModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    List<HelpCategoryModel.ResponseBean.HelpcatDataBean> data;
    Context context;
    private Clicklisten clickli;
    public interface Clicklisten
    {
        void itemclick(View v,int pos);
    }
    public HelpAdapter(Context context,List<HelpCategoryModel.ResponseBean.HelpcatDataBean> data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.help_categoryrow,parent,false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load("http://www.ineed.biz/upload/"+data.get(position).getHelpcat_icon()).into(holder.img);
        holder.txttype.setText(data.get(position).getHelpcat_language_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void seclick(Clicklisten clicklis)
    {
        clickli=clicklis;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView txttype;
        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_helpcat);
            txttype=itemView.findViewById(R.id.txt_helpcat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickli!=null)
            {
                clickli.itemclick(v,getAdapterPosition());
            }
        }
    }
}
