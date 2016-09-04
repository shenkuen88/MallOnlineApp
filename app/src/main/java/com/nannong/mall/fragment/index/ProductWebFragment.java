package com.nannong.mall.fragment.index;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nannong.mall.R;
import com.nannong.mall.adapter.index.CouponFragmentAdapter;
import com.nannong.mall.response.index.ContentDetailResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;

//产品详情webview
public class ProductWebFragment extends BaseFragment
{
    private View mView;

    private TabLayout tab_product_title;
    private ArrayList<String> list_title;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private ProductBottomWebFragment mProductBottomWebFragment;
    private ProductBottomWebFragment mProductBottomParamsFragment;
//    private DecorateCommentFragment mCommentFragment;
    private FragmentPagerAdapter fAdapter;
    private ViewPager vp_FindFragment_pager;


    private View tvTitle;

    public static void setType(String type1) {
        type = type1;
    }

    private static String type = "1";

    public static ProductWebFragment newInstance(String type) {
        ProductWebFragment fragment = new ProductWebFragment();
        setType(type);
        return fragment;
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_web, null);
        return mView;
    }

    @Override
    public void initView(View view)
    {

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initError();
    }

    private void init() {
        tvTitle = (View)mView.findViewById(R.id.line_view);
//        if (!DisplayUtil.checkDeviceHasNavigationBar(getActivity())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                tvTitle.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//            }
//        }
        vp_FindFragment_pager = (ViewPager) mView.findViewById(R.id.vp_FindFragment_pager);
        tab_product_title = (TabLayout) mView.findViewById(R.id.tab_product_title);
        list_title = new ArrayList<>();
        list_title.add("图文详情");
        if (type.equals("1")) {
            list_title.add("商品参数");
        } else {
            list_title.add("评论");
        }

        //设置TabLayout的模式
        tab_product_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_product_title.addTab(tab_product_title.newTab().setText(list_title.get(0)));
        tab_product_title.addTab(tab_product_title.newTab().setText(list_title.get(1)));

        //初始化各fragment
        mProductBottomParamsFragment = new ProductBottomWebFragment();
        mProductBottomWebFragment = new ProductBottomWebFragment();
//        mCommentFragment = new DecorateCommentFragment();

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(mProductBottomWebFragment);
        if (type.equals("1")){
            list_fragment.add(mProductBottomParamsFragment);
        }else {
//            list_fragment.add(mCommentFragment);
        }

        fAdapter = new CouponFragmentAdapter(getActivity().getSupportFragmentManager(), list_fragment, list_title);

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager

        tab_product_title.setupWithViewPager(vp_FindFragment_pager);

    }

    private void initError() {

    }

    @Override
    public void onEventMainThread(BaseResponse event) {

        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(ContentDetailResponse.class.getName())) {
                ContentDetailResponse mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode())) {
                        if (mProductBottomWebFragment!=null){
                            mProductBottomWebFragment.loadUrl(  mContentDetailResponse.getContent().getSpecificationLink());
                        }
                        if (mProductBottomParamsFragment!=null){
                            mProductBottomParamsFragment.loadUrl(mContentDetailResponse.getContent().getDescriptionLink());
                        }
                    } else {
//                        ErrorCode.doCode(getActivity(), mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
//                    ToastUtil.showError(getActivity());
                }
            }

        }
    }


}
