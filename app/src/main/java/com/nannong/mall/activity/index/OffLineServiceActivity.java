package com.nannong.mall.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.index.BannerListBean;
import com.nannong.mall.response.index.IndexBannerResponse;
import com.nannong.mall.response.index.OnlineShopListResponse;
import com.nannong.mall.response.index.OrderResponse;

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
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.CommonWebViewActivity;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.demo.NetworkImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;
import cn.nj.www.my_module.view.banner.listener.OnItemClickListener;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;


/**
 * 线下服务
 */
public class OffLineServiceActivity extends BaseActivity implements View.OnClickListener
{


    private ListView lvTeamBuy;

    private int page = 1;

    private int loadPage = 1;

    private CommonAdapter<OrderResponse.OrderListBean> lvAdapter;

    private List<OrderResponse.OrderListBean> datas = new ArrayList<OrderResponse.OrderListBean>();

    private int tolcount = 0;

    private boolean isloading;

    private int lastVisibileItem;

    private View loadingView;

    private LinearLayout llLoading;

    private TextView tvSeeMoreDate;

    private VaryViewHelper mVaryViewHelper;

    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();

    private ConvenientBanner mBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_show);
        initAll();
        initData();
        //线下Banner
        UserServiceImpl.instance().getBannerList(mContext, "2", IndexBannerResponse.class.getName());
    }

    @Override
    public void initView()
    {
        initTitle();
        lvTeamBuy = (ListView) findViewById(R.id.lvBuy);
        lvScrollLoadData();
        initAdapter();
        bannerFirstInit();
    }

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<OrderResponse.OrderListBean>(mContext, datas, R.layout.item_offline_service)
        {
            @Override
            public void convert(ViewHolder helper, final OrderResponse.OrderListBean item)
            {

            }
        };
        lvTeamBuy.setAdapter(lvAdapter);
    }

    private void lvScrollLoadData()
    {
        lvTeamBuy.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == lvTeamBuy.getCount())
                {
                    if (!isloading)
                    {
                        if (tolcount > page * Constants.LIST_NUM)
                        {
                            loadPage++;
                            initData();
                        }
                        else
                        {
//                            ToastUtil.makeText(getActivity(), "当前是最后一页");
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        tvSeeMoreDate = (TextView) loadingView.findViewById(R.id.load_more_tv);
        tvSeeMoreDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (tolcount > page * Constants.LIST_NUM)
                {
                    loadPage++;
                    initData();
                }
                else
                {
                    ToastUtil.makeText(mContext, "当前是最后一页");
                    tvSeeMoreDate.setVisibility(View.GONE);
                }
            }
        });
        tvSeeMoreDate.setVisibility(View.GONE);
        lvTeamBuy.addFooterView(loadingView);
    }

    private void initData()
    {
        isloading = true;
        llLoading.setVisibility(View.VISIBLE);
        if (loadPage == 1)
        {
            mVaryViewHelper.showLoadingView();
        }
        UserServiceImpl.instance().getShopList("2", loadPage, OnlineShopListResponse.class.getName());
    }

    @Override
    public void initViewData()
    {
        if (datas.size() == 0)
        {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                    .setEmptyView(R.mipmap.ic_launcher, "暂无")//空页面，图+文字
                    .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (GeneralUtils.isNetworkConnected(mContext))
                            {
                                mVaryViewHelper.showLoadingView();
                                loadPage = 1;
                                initData();
                            }
                            else
                            {
                                ToastUtil.showError(mContext);
                            }
                        }
                    })//错误页点击刷新实现
                    .build(mContext);
            mVaryViewHelper.showLoadingView();
        }
    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            mVaryViewHelper.showDataView();
            if (tag.equals(IndexBannerResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    IndexBannerResponse mIndexBannerResponse = GsonHelper.toType(result, IndexBannerResponse.class);
                    if (Constants.SUCESS_CODE.equals(mIndexBannerResponse.getResultCode()))
                    {
                        CMLog.e(Constants.LOCAL_TAG, result);
                        SharePref.saveString(Constants.HOME_BANNER_RESULT, result);
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

            if (tag.equals(OrderResponse.class.getName()))
            {
                llLoading.setVisibility(View.GONE);
                tvSeeMoreDate.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    OrderResponse orderResponse = GsonHelper.toType(result, OrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderResponse.getResultCode()))
                    {
                        if (loadPage == 1)
                        {
                            datas.clear();
                        }
                        page = loadPage;
                        tolcount = orderResponse.getTotalCount();
                        datas.addAll(orderResponse.getOrderList());
                        lvAdapter.setData(datas);
                        lvAdapter.notifyDataSetChanged();
                        isloading = false;
                        if (tolcount <= page * Constants.LIST_NUM)
                        {
                            tvSeeMoreDate.setText("已加载完毕");
                        }
                        if (datas.size() == 0)
                        {
                            mVaryViewHelper.showEmptyView();
                        }
                    }
                    else
                    {
                        page = loadPage;
                        if (datas.size() == 0)
                        {
                            mVaryViewHelper.showEmptyView();
                        }
                        ErrorCode.doCode(mContext, orderResponse.getResultCode(), orderResponse.getDesc());
                    }
                }
                else
                {
                    page = loadPage;
                    if (datas.size() == 0)
                    {
                        mVaryViewHelper.showErrorView();
                    }
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("线下服务");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
        }
    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit()
    {
        //第一次展示默认本地图片
        localImages.add(R.drawable.default_bg);//默认图片
        localImages.add(R.drawable.default_head);//默认图片
        mBanner = (ConvenientBanner) findViewById(R.id.index_banner);
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
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        //显示上次的内容
        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.HOME_BANNER_RESULT, "")))
        {
            IndexBannerResponse mIndexBannerResponse = GsonHelper.toType(SharePref.getString(Constants.HOME_BANNER_RESULT, ""),
                    IndexBannerResponse.class);
            initBanner(mIndexBannerResponse.getBannerList());
        }
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
