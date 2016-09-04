package com.nannong.mall.activity.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.nannong.mall.R;
import com.nannong.mall.fragment.order.MyOrderFragment;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.V;


/**
 * Created by jwei on 2016/7/7.
 * 订单列表 包含 全部 待付款 待发货 待收货 待评价 和 特殊处理的退款
 */
public class OrderListActivity extends BaseActivity {
    private TabLayout mTabs;
    private ViewPager mContainer;
    int orderstate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        orderstate=getIntent().getIntExtra("orderstate",0);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        mTabs = V.f(OrderListActivity.this, R.id.mTabs);
        // Set up the ViewPager with the sections adapter.
        mContainer = V.f(OrderListActivity.this, R.id.mContainer);
    }

    @Override
    public void initViewData() {
        if(orderstate!=5){
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mContainer.setAdapter(sectionsPagerAdapter);
            mContainer.setOffscreenPageLimit(1);
            mContainer.setCurrentItem(orderstate);
            mTabs.setupWithViewPager(mContainer);
            mTabs.setVisibility(View.VISIBLE);
        }else{
            SectionsPager2Adapter sectionsPager2Adapter= new SectionsPager2Adapter(getSupportFragmentManager());
            mContainer.setAdapter(sectionsPager2Adapter);
            mTabs.setVisibility(View.GONE);
        }


    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        if(orderstate!=5){
            headView.setTitleText("我的订单");
            headView.setRightImage(R.mipmap.search);
        }else{
            headView.setTitleText("退款/售后");
            headView.setRightImage(R.mipmap.btn_more);
        }
    }

    //订单列表fragment的adapter
    public class SectionsPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new MyOrderFragment(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "全部";
                case 1:
                    return "待付款";
                case 2:
                    return "待发货";
                case 3:
                    return "待收货";
                case 4:
                    return "待评价";
            }
            return null;
        }
    }

    public class SectionsPager2Adapter extends android.support.v4.app.FragmentPagerAdapter {

        public SectionsPager2Adapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new MyOrderFragment(5);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {

            }
        }
    }
}
