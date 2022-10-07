package com.globalspace.miljonsales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalspace.miljonsales.model.ManufactureInfo;

import java.util.List;
import com.globalspace.miljonsales.R;


public class prdwisediscCompanyadapter extends RecyclerView.Adapter<prdwisediscCompanyadapter.CompanyVH> {

    private Context activity;
    private List<ManufactureInfo> compList;
    private LinearLayout mainLL,ll_cardDetails;
    private String CompanyId;
    private onItemSelected mListener;

    public interface onItemSelected {
        void onItemSelected(String companyId);
    }

    public void setonItemClickListener(onItemSelected listener) {
        mListener = listener;
    }


    public prdwisediscCompanyadapter(Context activity, List<ManufactureInfo> compList, String CompanyId ){
        this.activity = activity;
        this.compList = compList;
        this.CompanyId = CompanyId;
    }

    @NonNull
    @Override
    public CompanyVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itembrandsadapter, viewGroup, false);
        return new CompanyVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyVH companyVH, final int i) {
        if(CompanyId.equals(compList.get(i).getManufctrId())) {
            companyVH.tv_brands.setTextColor(activity.getResources().getColor(R.color.floatingbutton));
        } else {
            companyVH.tv_brands.setTextColor(activity.getResources().getColor(R.color.darkGrey));
        }

        companyVH.tv_brands.setText(compList.get(i).getCompanyName());
        companyVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemSelected(compList.get(i).getManufctrId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return compList.size();
    }

    public class CompanyVH extends RecyclerView.ViewHolder{

        private TextView tv_brands;
        public CompanyVH(@NonNull View itemView) {
            super(itemView);

            tv_brands = (TextView) itemView.findViewById(R.id.tv_brands);
        }
    }

}
