package com.globalspace.miljonsales.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by aravind on 9/10/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

       /* switch (position) {
            case 0:
                Approved_Order Approved = new Approved_Order();
                return Approved;
            case 1:
                Pending_Orders Pending = new Pending_Orders();
                return Pending;
            case 2:
                Decline_Orders Decline = new Decline_Orders();
                return Decline;
            default:
                return null;
        }*/
       return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
