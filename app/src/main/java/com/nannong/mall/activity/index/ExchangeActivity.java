package com.nannong.mall.activity.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.index.NoticeListResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;


/**
 * 兑换记录
 */
public class ExchangeActivity extends BaseActivity implements View.OnClickListener
{


    private ListView lvZhengWu;

    private int page = 1;

    private int loadPage = 1;

    private CommonAdapter<NoticeListResponse.NoticeListBean> lvAdapter;

    private List<NoticeListResponse.NoticeListBean> datas = new ArrayList<NoticeListResponse.NoticeListBean>();

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
        setContentView(R.layout.activity_zhengwu_list);
        initAll();
        initData();
    }

    @Override
    public void initView()
    {
        initTitle();
        lvZhengWu = (ListView) findViewById(R.id.zw_lv);
        lvScrollLoadData();
        initAdapter();
    }

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<NoticeListResponse.NoticeListBean>(mContext, datas, R.layout.item_zhengwu)
        {
            @Override
            public void convert(ViewHolder helper, final NoticeListResponse.NoticeListBean item)
            {

            }
        };
        lvZhengWu.setAdapter(lvAdapter);
    }

    private void lvScrollLoadData()
    {
        lvZhengWu.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == lvZhengWu.getCount())
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
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.ef_loading_foot, null);
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
        lvZhengWu.addFooterView(loadingView);
    }

    private void initData()
    {
        isloading = true;
        llLoading.setVisibility(View.VISIBLE);
        if (loadPage == 1)
        {
            mVaryViewHelper.showLoadingView();
        }
        UserServiceImpl.instance().getNotice(loadPage, NoticeListResponse.class.getName());
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
            llLoading.setVisibility(View.GONE);
            tvSeeMoreDate.setVisibility(View.VISIBLE);
            mVaryViewHelper.showDataView();
            if (tag.equals(NoticeListResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    NoticeListResponse orderResponse = GsonHelper.toType(result, NoticeListResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderResponse.getResultCode()))
                    {
                        if (loadPage == 1)
                        {
                            datas.clear();
                        }
                        page = loadPage;
                        tolcount = orderResponse.getTotalCount();
                        datas.addAll(orderResponse.getNoticeList());
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
        headView.setTitleText("政务");
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
}
