package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.EmpPackage;
import com.androidtutorialpoint.ineed.proj.models.JobSeekerPackage;

import java.util.List;

/**
 * Created by ask on 12/6/2017.
 */

public class EmpPackageAdapter extends RecyclerView.Adapter<EmpPackageAdapter.MyViewHolder> {
    Context context;
    List<EmpPackage.ResponseBean.EmployerDataBean> jobSeekerPackage;
    private Clicklistner clicklistner;

    public interface Clicklistner
    {
        void itemclick(View v, int post);
    }

    public EmpPackageAdapter(Context context, List<EmpPackage.ResponseBean.EmployerDataBean> jobSeekerPackage) {
        this.context = context;
        this.jobSeekerPackage = jobSeekerPackage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (jobSeekerPackage!=null){
            String pckgStartDate = jobSeekerPackage.get(position).getEmployers_package_start_date();
            String pckgExpireDate = jobSeekerPackage.get(position).getEmployers_package_expire_date();

            holder.txtPrice.setText("$"+jobSeekerPackage.get(position).getEmployers_package_prize());
            holder.txtValidity.setText("Valid for "+jobSeekerPackage.get(position).getEmployers_package_time_frame()+" months");
            holder.txtPckgType.setText(jobSeekerPackage.get(position).getEmployers_package_name().toUpperCase());
            holder.txtCredit.setText(jobSeekerPackage.get(position).getEmployers_package_credit() + " Credits");

        }

    }
    public void setclick(Clicklistner clicklistner1)
    {
        clicklistner=clicklistner1;
    }

    @Override
    public int getItemCount() {
        return jobSeekerPackage.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtPckgType, txtValidity, txtPrice , txtCredit;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.pckg_price);
            txtPckgType = (TextView) itemView.findViewById(R.id.pckg_name);
            txtValidity = (TextView) itemView.findViewById(R.id.pckg_valid);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_pckg);
            txtCredit = (TextView) itemView.findViewById(R.id.pckg_credits);
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
