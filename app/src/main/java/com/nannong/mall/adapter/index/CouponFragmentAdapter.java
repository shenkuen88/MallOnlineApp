package com.nannong.mall.adapter.index;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * Created by huqing on 2016/7/7.
 */


/**
 * Created by cg on 2016/7/7.
 */
public class CouponFragmentAdapter extends FragmentPagerAdapter
{

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表


    public CouponFragmentAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position % list_Title.size());
    }
}
