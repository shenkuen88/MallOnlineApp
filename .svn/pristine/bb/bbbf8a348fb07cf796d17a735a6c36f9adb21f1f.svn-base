package com.nannong.mall.fragment.index;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.response.index.CollectContentResponse;
import com.nannong.mall.response.index.ColumnListBean;
import com.nannong.mall.response.index.ColumnListResponse;
import com.nannong.mall.response.index.ShopBean;
import com.nannong.mall.view.home_view.FourViewLinearLayout;
import com.nannong.mall.view.home_view.OneViewImageView;
import com.nannong.mall.view.home_view.SixViewLinearLayout;
import com.nannong.mall.view.home_view.ThreeViewLinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;

/**
 * 首页
 * Created by jwei on 2016/8/24 0024.
 */
public class OnlineShopIndexFragment extends BaseFragment implements View.OnClickListener
{
    private static OnlineShopIndexFragment instance;

    private static String mShopId;

    private VaryViewHelper mVaryViewHelper;

    private View mView;

    private LinearLayout allContentll;

    private List<ColumnListBean> columnList = new ArrayList<>();

    private boolean collectFlag = false;


    private Button bnFavourite;

    private ImageView ivIcon;

    private TextView tvNum, tvName;

    private ShopBean shopBean;

    public OnlineShopIndexFragment()
    {
    }

    public static OnlineShopIndexFragment getInstance(String shopId)
    {
        if (instance == null)
        {
            mShopId = shopId;
            instance = new OnlineShopIndexFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.from(getActivity()).inflate(R.layout.fragment_online_shop_index, null);
        initAll(mView);
        initData();
        return mView;
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
        }

        if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            mVaryViewHelper.showDataView();
            //收藏
            if (NotiTag.equalsTags(getActivity(), tag, CollectContentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CollectContentResponse mCollectContentResponse = GsonHelper.toType(result, CollectContentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCollectContentResponse.getResultCode()))
                    {
                        collectFlag = true;
                        bnFavourite.setBackgroundResource(R.drawable.app_yellow_bn);
                        bnFavourite.setTextColor(Color.WHITE);
                        ToastUtil.makeText(getActivity(), "恭喜，收藏成功");
                    }
                    else
                    {
                        ErrorCode.doCode(getActivity(), mCollectContentResponse.getResultCode(), mCollectContentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(ColumnListResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    ColumnListResponse mColumnListResponse = GsonHelper.toType(result, ColumnListResponse.class);

                    if (Constants.SUCESS_CODE.equals(mColumnListResponse.getResultCode()))
                    {

                        columnList = mColumnListResponse.getColumnList();
                        if (columnList != null && columnList.size() > 0)
                        {
                            showList();
                        }
                        else
                        {
                            mVaryViewHelper.showEmptyView();
                        }
                        shopBean = mColumnListResponse.getShopBean();
                        CMLog.e(Constants.HTTP_TAG,shopBean.toString());
                        tvName.setText(shopBean.getShopName()+"");
                        if (GeneralUtils.isNotNullOrZeroLenght(shopBean.getShopID()))
                        {
                            tvNum.setText("店铺号:" + shopBean.getShopID());
                        }
                        else
                        {
                            tvNum.setVisibility(View.GONE);
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(shopBean.getPicUrl()))
                        {
                            GeneralUtils.setImageViewWithUrl(getActivity(), mColumnListResponse.getShopBean().getPicUrl() + "", ivIcon, R.drawable.default_bg);
                        }

                    }
                    else
                    {
                        mVaryViewHelper.showEmptyView();
                        ErrorCode.doCode(getActivity(), mColumnListResponse.getResultCode(), mColumnListResponse.getDesc());
                    }
                }
                else
                {
                    mVaryViewHelper.showErrorView();
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public void initView(View view)
    {
        allContentll = (LinearLayout) mView.findViewById(R.id.allContentll);
        bnFavourite = (Button) mView.findViewById(R.id.bnFavourite);
        ivIcon = (ImageView) mView.findViewById(R.id.ivIcon1);
        tvName = (TextView) mView.findViewById(R.id.tvName1);
        tvNum = (TextView) mView.findViewById(R.id.tvNum1);
        bnFavourite.setOnClickListener(this);
    }


    private void initData()
    {
        UserServiceImpl.instance().getShopDetail(mShopId, ColumnListResponse.class.getName());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bnFavourite:
                shouCang();
                break;
        }
    }


    @Override
    public void initViewData()
    {
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(allContentll)//放数据的父布局，逻辑处理在该Activity中处理.
                .setEmptyView(R.mipmap.icon_empty_community, "暂无该商家信息")//空页面，图+文字
                .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (GeneralUtils.isNetworkConnected(getActivity()))
                        {
                            mVaryViewHelper.showLoadingView();
                            initData();
                        }
                        else
                        {
                            ToastUtil.showError(getActivity());
                        }
                    }
                })//错误页点击刷新实现
                .build(getActivity());
        mVaryViewHelper.showLoadingView();
    }

    @Override
    public void initEvent()
    {
    }

    private void showList()
    {
        allContentll.removeAllViews();
        for (int i = 0; i < columnList.size(); i++)
        {
            ColumnListBean columnBean = columnList.get(i);
            int count = columnList.get(i).getListCount();
            if (count == 1)
            {
                OneViewImageView oneView = new OneViewImageView(getActivity());
                allContentll.addView(oneView);
                oneView.setData(columnList.get(i), columnBean.getColumnName());
            }
            else if (count == 3)
            {
                ThreeViewLinearLayout threeView = new ThreeViewLinearLayout(getActivity());
                allContentll.addView(threeView);
                threeView.setData(columnBean, columnBean.getColumnName(), columnBean.getIcon());
            }
            else if (count == 4)
            {
                FourViewLinearLayout fourView = new FourViewLinearLayout(getActivity());
                allContentll.addView(fourView);
                fourView.setData(columnBean, columnBean.getColumnName(), columnBean.getIcon());
            }
            else if (count == 6)
            {
                SixViewLinearLayout sixView = new SixViewLinearLayout(getActivity());
                allContentll.addView(sixView);
                sixView.setData(columnBean, columnBean.getColumnName(), columnBean.getIcon());
            }
        }
    }


    public void shouCang()
    {
        if (collectFlag)
        {
            return;
        }
        else if (GeneralUtils.isLogin())
        {
            UserServiceImpl.instance().collectContent(2 + "", mShopId,
                    CollectContentResponse.class.getName());
        }
        else
        {
            startActivity(new Intent(getActivity(), LoginActy.class));
        }
    }
}
