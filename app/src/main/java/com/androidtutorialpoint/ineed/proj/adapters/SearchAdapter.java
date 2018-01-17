package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder> implements Filterable
{
    public Context context;
    List<SearchModel.ProfileListBean> searchlist=new ArrayList();
    List<SearchModel.ProfileListBean> searchlistFilterd=new ArrayList();

    Clickitem clickitem;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String gen=constraint.toString();
                if(gen.isEmpty())
                {
                    searchlistFilterd=searchlist;
                }
                else
                {
                    List<SearchModel.ProfileListBean> fillist=new ArrayList<>();
                    for(SearchModel.ProfileListBean row:searchlist)
                    {
                        String mygen=row.getUser_gender();
                        if(mygen.equalsIgnoreCase(gen.toLowerCase()))
                        {
                            fillist.add(row);
                        }
                    }
                    searchlistFilterd=fillist;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=searchlistFilterd;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            searchlistFilterd= (List<SearchModel.ProfileListBean>) results.values;
            notifyDataSetChanged();
            }
        };
    }

    public interface Clickitem
    {
        void itemclick(View v,int position);
    }
    public SearchAdapter(Context context, List<SearchModel.ProfileListBean> searchlist) {
        this.context = context;
        this.searchlist = searchlist;
        this.searchlistFilterd=searchlist;
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.txt_tech.setText(searchlistFilterd.get(position).getDesignation());
        String fi=searchlistFilterd.get(position).getTotalExperienceyear();
        String se=searchlistFilterd.get(position).getTotalExperiencemonths();
        if(fi==null) {
                fi = "0";
        }
        if(se==null) {
                se = "0";
        }
        holder.txt_exper.setText(fi+"."+se+" Year");
        holder.txt_age.setText(searchlistFilterd.get(position).getUser_age()+" Year");
      //  holder.txt_country.setText(searchlistFilterd.get(position).getUser_nationality());
        String ctc=searchlistFilterd.get(position).getUser_ctc();
        if(ctc==null)
        {
            ctc="0";
        }
        holder.txt_ctc.setText(ctc);
        holder.txt_gender.setText(searchlistFilterd.get(position).getUser_gender());
        Log.d(TAG, "onBindViewHolder: "+ ApiList.IMG_BASE+searchlistFilterd.get(position).getUser_image());
        if (searchlist.get(position).getUser_image()!=null&&searchlistFilterd.get(position).getUser_image().length()>0){
            Glide.with(context).load(ApiList.IMG_BASE+searchlistFilterd.get(position).getUser_image())
                    .apply(RequestOptions.circleCropTransform()).into(holder.profimg);
        } else {
            Glide.with(context).load(R.drawable.gfgf)
                    .apply(RequestOptions.circleCropTransform()).into(holder.profimg);
        }
    }

    @Override
    public int getItemCount() {
        return searchlistFilterd.size();
    }
    public void setclick(Clickitem clickitem)
    {
        this.clickitem=clickitem;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profimg;
        TextView txt_tech,txt_exper,txt_age,txt_country,txtDegree,txt_gender,txt_ctc;

        public Viewholder(View itemView) {
            super(itemView);
            profimg=itemView.findViewById(R.id.img_profile);
            txt_tech=itemView.findViewById(R.id.txtName);
            txt_exper=itemView.findViewById(R.id.txtExp);
            txt_age=itemView.findViewById(R.id.txtAge);
            txtDegree=itemView.findViewById(R.id.txtDegree);
            txt_ctc=itemView.findViewById(R.id.txt_salary);
            txt_gender=itemView.findViewById(R.id.txt_gender);
            txt_gender.setVisibility(View.VISIBLE);
            txtDegree.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickitem.itemclick(itemView,getAdapterPosition());
        }
    }
}
