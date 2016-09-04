package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.order.PublicCommentActy;
import com.nannong.mall.response.index.MyPublicTeamBuyResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
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
 * 我发布的拼团
 */
public class MyPublicTeamBuyActivity extends BaseActivity implements View.OnClickListener
{

    private ListView mListView;

    private int page = 1;

    private int loadPage = 1;

    private CommonAdapter<MyPublicTeamBuyResponse.GroupBuyingListBean> lvAdapter;

    private List<MyPublicTeamBuyResponse.GroupBuyingListBean> datas = new ArrayList<>();

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
        mListView = (ListView) findViewById(R.id.zw_lv);
        lvScrollLoadData();
        initAdapter();
    }

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<MyPublicTeamBuyResponse.GroupBuyingListBean>(mContext, datas, R.layout.item_public_team_buy_project)
        {
            @Override
            public void convert(ViewHolder helper, final MyPublicTeamBuyResponse.GroupBuyingListBean item)
            {
                ImageView iv = helper.getView(R.id.ivIcon);
                GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1(), iv, R.drawable.default_bg);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDes, item.getDescription());
                helper.setText(R.id.tvPrice, "¥" + item.getPrice());
                helper.setText(R.id.tvTime, item.getCreateTime());
                helper.getView(R.id.llMore).setVisibility(View.GONE);

                //bnState 1-待审核  2-拼团中  3-已结束 4审批驳回
                if (item.getStatus() == 1)
                {
                    helper.setText(R.id.bnState, "待审核");
                }
                else if (item.getStatus() == 2)
                {
                    helper.setText(R.id.bnState, "拼团中");
                }
                else if (item.getStatus() == 3)
                {
                    helper.setText(R.id.bnState, "已结束");
                    helper.getView(R.id.llMore).setVisibility(View.VISIBLE);
                    //申请退货
                    helper.getView(R.id.bnTui).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                        }
                    });
                    //评价
                    helper.getView(R.id.bnComment).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Global.saveOrderId(item.getRecordID());
                            Intent intent = new Intent(mContext, PublicCommentActy.class);
                            startActivity(intent);
                        }
                    });
                    //查看发货
                    helper.getView(R.id.bnTransport).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                        }
                    });
                }
                else if (item.getStatus() == 4)
                {
                    helper.setText(R.id.bnState, "审批驳回");
                }
            }
        };
        mListView.setAdapter(lvAdapter);
    }

    private void lvScrollLoadData()
    {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == mListView.getCount())
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
        mListView.addFooterView(loadingView);
    }

    private void initData()
    {
        isloading = true;
        llLoading.setVisibility(View.VISIBLE);
        if (loadPage == 1)
        {
            mVaryViewHelper.showLoadingView();
        }
        UserServiceImpl.instance().myPublicTeamBuy(loadPage, MyPublicTeamBuyResponse.class.getName());
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
            if (tag.equals(MyPublicTeamBuyResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    MyPublicTeamBuyResponse orderResponse = GsonHelper.toType(result, MyPublicTeamBuyResponse.class);
                    if (Constants.SUCESS_CODE.equals(orderResponse.getResultCode()))
                    {
                        if (loadPage == 1)
                        {
                            datas.clear();
                        }
                        page = loadPage;
                        tolcount = orderResponse.getTotalCount();
                        datas.addAll(orderResponse.getGroupBuyingList());
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
        headView.setTitleText("我发布的拼团");
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
