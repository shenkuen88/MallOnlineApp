package com.nannong.mall.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.index.BannerListBean;
import com.nannong.mall.response.index.ColumnListResponse;
import com.nannong.mall.response.index.ShopBannerResponse;
import com.nannong.mall.response.index.ShopBean;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.CommonWebViewActivity;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.MyListView;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.demo.NetworkImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;
import cn.nj.www.my_module.view.banner.listener.OnItemClickListener;

public class OfflineShopActy extends BaseActivity implements View.OnClickListener
{


    private String mShopId = "6";

    private MyListView lvFood;

    private TextView tvName, tvType, tvAddress, tvCall, tvTelNum;

    private ShopBean shopBean;

    private CommonAdapter<FoodBean> lvAdapter;

    private ConvenientBanner mBanner;

    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_shop);
        initAll();
        initData();
        UserServiceImpl.instance().getBannerList(mContext, "2", ShopBannerResponse.class.getName());

    }

    private void initData()
    {
        UserServiceImpl.instance().getShopDetail(mShopId, ColumnListResponse.class.getName());
    }

    @Override
    public void initView()
    {
        tvName = (TextView) findViewById(R.id.tvName);
        tvType = (TextView) findViewById(R.id.tvType);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvTelNum = (TextView) findViewById(R.id.tvTelNum);
        tvCall = (TextView) findViewById(R.id.tvCall);
        lvFood = (MyListView) findViewById(R.id.lvFood);
        bannerFirstInit();
    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {
        tvCall.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.equalsTags(this, NotiTag.TAG_CLOSE_ACTIVITY, tag))
            {
                finish();
            }


        }
        else if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(ShopBannerResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    ShopBannerResponse mIndexBannerResponse = GsonHelper.toType(result, ShopBannerResponse.class);
                    if (Constants.SUCESS_CODE.equals(mIndexBannerResponse.getResultCode()))
                    {
                        initBanner(mIndexBannerResponse.getBannerList());
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mIndexBannerResponse.getResultCode(), mIndexBannerResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(ColumnListResponse.class.getName()))
            {
                ColumnListResponse mColumnListResponse = GsonHelper.toType(result, ColumnListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mColumnListResponse.getResultCode()))
                    {
                        shopBean = mColumnListResponse.getShopBean();
                        tvName.setText(shopBean.getShopName());
                        tvAddress.setText(shopBean.getAddress());
                        tvTelNum.setText(shopBean.getPhone());
                        tvType.setText(shopBean.getCategory());
                        String[] sourceStrArray = shopBean.getTop().split(",");
                        for (int i = 0; i < sourceStrArray.length; i++)
                        {
                            FoodBean bean = new FoodBean(sourceStrArray[i]);
                            datas.add(bean);
                        }
                        if (datas.size() > 0)
                        {
                            initAdapter();
                            lvFood.setAdapter(lvAdapter);
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mColumnListResponse.getResultCode(), mColumnListResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvCall:
                if (shopBean != null)
                {
                    GeneralUtils.setTel(mContext, shopBean.getPhone());
                }
                break;
        }
    }


    class FoodBean
    {
        String name;

        public FoodBean(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }

    private List<FoodBean> datas = new ArrayList<>();

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<FoodBean>(mContext, datas, R.layout.item_food_name)
        {
            @Override
            public void convert(ViewHolder helper, FoodBean item)
            {
                helper.setText(R.id.tvName, item.getName().trim());
            }
        };
    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit()
    {
        //第一次展示默认本地图片
        localImages.add(R.drawable.default_bg);//默认图片
        localImages.add(R.drawable.default_head);//默认图片
        mBanner = (ConvenientBanner) findViewById(R.id.product_banner);
        mBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>()
                {
                    @Override
                    public LocalImageHolderView createHolder()
                    {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator_focused, R.drawable.ic_page_indicator});
    }

    /**
     * Banner展示网络数据
     *
     * @param ad
     */
    private synchronized void initBanner(final List<BannerListBean> ad)
    {
        if (ad == null || ad.size() < 1)
        {
            return;
        }
        networkImages.clear();
        for (int i = 0; i < ad.size(); i++)
        {
            if (!networkImages.contains(ad.get(i).getThumCover()) && GeneralUtils.isNotNullOrZeroLenght(ad.get(i).getCover()))
            {
                networkImages.add(ad.get(i).getThumCover());
            }
        }
        mBanner.stopTurning();
        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>()
        {
            @Override
            public NetworkImageHolderView createHolder()
            {
                return new NetworkImageHolderView();
            }
        }, networkImages).setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                BannerListBean bean = ad.get(position);
                if (bean != null && GeneralUtils.isNotNullOrZeroLenght(bean.getLink()))
                {
                    if (bean.getType() == 1 || bean.getType() == 2)
                    {
                        Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, bean.getTitle());
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
                        startActivity(intent);
                    }
                }

            }
        });
    }


    // 停止自动翻页
    @Override
    public void onPause()
    {
        super.onPause();
        //停止翻页
        mBanner.stopTurning();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        mBanner.startTurning(Constants.BANNER_TURN_TIME);
    }
}
