package com.globalspace.miljonsales.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.globalspace.miljonsales.model.CategoryInfo;

import java.util.List;

import com.globalspace.miljonsales.R;


public class prdwisediscategoryfilteradapter extends RecyclerView.Adapter<prdwisediscategoryfilteradapter.CategoryVH> {
    private AppCompatActivity activity;
    private List<CategoryInfo> catList;
    private String CompanyId;
    private OnCategorySelectedListener mListener;


    public interface OnCategorySelectedListener {
        void onCategorySelected(String categoryId);
    }

    public void setonItemClickListener(OnCategorySelectedListener listener) {
        mListener = listener;
    }


    public prdwisediscategoryfilteradapter(AppCompatActivity activity, List<CategoryInfo> catList, String CompanyId ){
        this.activity = activity;
        this.catList = catList;
        this.CompanyId = CompanyId;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itembrandsadapter,viewGroup,false);
        return new CategoryVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH categoryVH, @SuppressLint("RecyclerView") final int i) {
        if(CompanyId.equals(catList.get(i).getCategoryId())) {
            categoryVH.tv_brands.setTextColor(activity.getResources().getColor(R.color.floatingbutton));
        } else {
            categoryVH.tv_brands.setTextColor(activity.getResources().getColor(R.color.darkGrey));
        }

        categoryVH.tv_brands.setText(catList.get(i).getProdCategory());
        categoryVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected(catList.get(i).getCategoryId());
            }
        });
    }




    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class CategoryVH extends RecyclerView.ViewHolder{

        private TextView tv_brands;
        public CategoryVH(@NonNull View itemView) {
            super(itemView);

            tv_brands = (TextView) itemView.findViewById(R.id.tv_brands);
        }
    }
}
