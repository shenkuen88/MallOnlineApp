package com.nannong.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.OffLineServiceActivity;
import com.nannong.mall.activity.index.OnLineServiceActivity;
import com.nannong.mall.activity.index.TeamBuyListActivity;
import com.nannong.mall.activity.index.ZhengWuActivity;
import com.nannong.mall.activity.index.barcode.BarCodeActy;
import com.nannong.mall.response.index.BannerListBean;
import com.nannong.mall.response.index.GetRecommendShopListResponse;
import com.nannong.mall.response.index.IndexBannerResponse;
import com.nannong.mall.tool.AppUtil;

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
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.main.base.CommonWebViewActivity;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.MyListView;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.demo.NetworkImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;
import cn.nj.www.my_module.view.banner.listener.OnItemClickListener;

/**
 * 首页
 * Created by jwei on 2016/8/24 0024.
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener
{
    private static IndexFragment instance;

    private LinearLayout mLlScan;

    private LinearLayout mLlPay;

    private LinearLayout mLlCoupon;

    private LinearLayout mllJfMall;

    private LinearLayout mLlrecondmend;

    private LinearLayout mLlOnlineService;

    private LinearLayout mLlOfflineService;

    private LinearLayout mLlSee;

    private LinearLayout mLlTeamBuy;

    private LinearLayout mLlReputation;


    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();

    private ConvenientBanner mBanner;

    private MyListView mLvIndex;

    private int page = 1;

    private int loadPage = 1;

    private CommonAdapter<GetRecommendShopListResponse.ContentListBean> lvAdapter;

    private List<GetRecommendShopListResponse.ContentListBean> datas = new ArrayList<GetRecommendShopListResponse.ContentListBean>();

    private int tolcount = 0;

    private boolean isloading;

    private int lastVisibileItem;

    private View loadingView;

    private LinearLayout llLoading;

    private TextView tvSeeMoreDate;

    public IndexFragment()
    {
    }

    public static IndexFragment getInstance()
    {
        if (instance == null)
        {
            instance = new IndexFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        initAll(v);
        UserServiceImpl.instance().getBannerList(getActivity(), "0", IndexBannerResponse.class.getName());
        initData();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mBanner.startTurning(Constants.BANNER_TURN_TIME);
        if (getUserVisibleHint())
        {

        }
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
            if (tag.equals(IndexBannerResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CMLog.e(Constants.LOCAL_TAG, result);

                    IndexBannerResponse mIndexBannerResponse = GsonHelper.toType(result, IndexBannerResponse.class);

                    if (Constants.SUCESS_CODE.equals(mIndexBannerResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.HOME_BANNER_RESULT, result);
                        initBanner(mIndexBannerResponse.getBannerList());
                    }
                    else
                    {
                        ErrorCode.doCode(getActivity(), mIndexBannerResponse.getResultCode(), mIndexBannerResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(getActivity());
                }
            }

            if (tag.equals(GetRecommendShopListResponse.class.getName()))
            {
                llLoading.setVisibility(View.GONE);
                tvSeeMoreDate.setVisibility(View.VISIBLE);
                CMLog.e(Constants.LOCAL_TAG, result);
                String a = result;
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {

                    GetRecommendShopListResponse orderResponse = GsonHelper.toType(result, GetRecommendShopListResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderResponse.getResultCode()))
                    {
                        if (loadPage == 1)
                        {
                            datas.clear();
                        }
                        page = loadPage;
                        datas.addAll(orderResponse.getContentList());
                        lvAdapter.setData(datas);
                        lvAdapter.notifyDataSetChanged();
                        isloading = false;
                        if (tolcount <= page * Constants.LIST_NUM)
                        {
                            tvSeeMoreDate.setText("已加载完毕");
                        }
                    }
                    else
                    {
                        page = loadPage;
                        ErrorCode.doCode(getActivity(), orderResponse.getResultCode(), orderResponse.getDesc());
                    }
                }
                else
                {
                    page = loadPage;
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public void initView(View view)
    {
        mLlScan = (LinearLayout) view.findViewById(R.id.llScan);
        mLlPay = (LinearLayout) view.findViewById(R.id.llPay);
        mLlCoupon = (LinearLayout) view.findViewById(R.id.llCoupon);
        mllJfMall = (LinearLayout) view.findViewById(R.id.llJfMall);
        mLlrecondmend = (LinearLayout) view.findViewById(R.id.llrecondmend);
        mLlOnlineService = (LinearLayout) view.findViewById(R.id.llOnlineService);
        mLlOfflineService = (LinearLayout) view.findViewById(R.id.llOfflineService);
        mLlSee = (LinearLayout) view.findViewById(R.id.llSee);
        mLlTeamBuy = (LinearLayout) view.findViewById(R.id.llTeamBuy);
        mLlReputation = (LinearLayout) view.findViewById(R.id.llReputation);
        mLvIndex = (MyListView) view.findViewById(R.id.lvIndex);
        bannerFirstInit(view);
        lvScrollLoadData();
        initAdapter();
    }

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<GetRecommendShopListResponse.ContentListBean>(getActivity(), datas, R.layout.item_index_lv)
        {
            @Override
            public void convert(ViewHolder helper, final GetRecommendShopListResponse.ContentListBean item)
            {
                ImageView ivIcon = helper.getView(R.id.ivIcon);
                GeneralUtils.setImageViewWithUrl(getActivity(),item.getThumPicUrl1(),ivIcon,R.drawable.default_bg);
                helper.setText(R.id.tvTitle,item.getShopName());
                helper.setText(R.id.tvDes,item.getDescription());
                helper.setText(R.id.tvPrice,"¥"+item.getPrice());
                //
                helper.setText(R.id.tvTime,"已售"+item.getAppraiseCount());
            }
        };
        mLvIndex.setAdapter(lvAdapter);
    }

    private void lvScrollLoadData()
    {
        mLvIndex.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == mLvIndex.getCount())
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
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_foot, null);
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
//                    ToastUtil.makeText(getActivity(), "当前是最后一页");
                }
            }
        });
        tvSeeMoreDate.setVisibility(View.GONE);
        mLvIndex.addFooterView(loadingView);
    }

    private void initData()
    {
        isloading = true;
        llLoading.setVisibility(View.VISIBLE);
        UserServiceImpl.instance().getRecommendShopList(getActivity(), GetRecommendShopListResponse.class.getName());

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.llScan:
                AppUtil.jumpToActivity(getActivity(), BarCodeActy.class);
                break;
            case R.id.llTeamBuy:
                AppUtil.jumpToActivity(getActivity(), TeamBuyListActivity.class);
                break;
            case R.id.llOnlineService:
                AppUtil.jumpToActivity(getActivity(), OnLineServiceActivity.class);
                break;
            case R.id.llOfflineService:
                AppUtil.jumpToActivity(getActivity(), OffLineServiceActivity.class);
                break;
            case R.id.llJfMall:
                AppUtil.jumpToActivity(getActivity(), OffLineServiceActivity.class);
                break;
            case R.id.llSee:
                AppUtil.jumpToActivity(getActivity(), ZhengWuActivity.class);
                break;
        }
    }


    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {
        mllJfMall.setOnClickListener(this);
        mLlScan.setOnClickListener(this);
        mLlPay.setOnClickListener(this);
        mLlCoupon.setOnClickListener(this);
        mLlrecondmend.setOnClickListener(this);
        mLlOnlineService.setOnClickListener(this);
        mLlOfflineService.setOnClickListener(this);
        mLlSee.setOnClickListener(this);
        mLlTeamBuy.setOnClickListener(this);
        mLlReputation.setOnClickListener(this);
    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit(View mView)
    {
        //第一次展示默认本地图片
        localImages.add(R.drawable.default_bg);//默认图片
        localImages.add(R.drawable.default_head);//默认图片
        mBanner = (ConvenientBanner) mView.findViewById(R.id.index_banner);
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
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator_focused, R.mipmap.index_banner_purple_icon});
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
                        Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, bean.getTitle());
                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
                        getActivity().startActivity(intent);
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

}
