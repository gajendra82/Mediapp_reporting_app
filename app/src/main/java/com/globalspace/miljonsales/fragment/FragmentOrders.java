package com.globalspace.miljonsales.fragment;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globalspace.miljonsales.R;

/**
 * Created by Steffi 14/03/2019
 */
public class FragmentOrders extends Fragment {

    private View v;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public FragmentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);

        return v;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private  FragmentManager fm;
        private int tabCount;

        public ViewPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.fm = fm;
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            /*switch (position){
                case 0:
            }*/
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        @Override
        public int getCount() {
            return tabCount;
        }
    }

}
