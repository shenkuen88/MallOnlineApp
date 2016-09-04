package com.nannong.mall.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.response.index.IndexTeamBuyListResponse;

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
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;


/**
 * 共享拼团
 */
public class TeamBuyListActivity extends BaseActivity implements View.OnClickListener
{


    private ListView lvTeamBuy;

    private int page = 1;

    private int loadPage = 1;

    private CommonAdapter<IndexTeamBuyListResponse.GroupBuyingListBean> lvAdapter;

    private List<IndexTeamBuyListResponse.GroupBuyingListBean> datas = new ArrayList<IndexTeamBuyListResponse.GroupBuyingListBean>();

    private int tolcount = 0;

    private boolean isloading;

    private int lastVisibileItem;

    private View loadingView;

    private LinearLayout llLoading;

    private TextView tvSeeMoreDate;

    private VaryViewHelper mVaryViewHelper;

    private Button bnPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_buy_list);
        initAll();
        initData();
    }

    @Override
    public void initView()
    {
        initTitle();
        lvTeamBuy = (ListView) findViewById(R.id.lvBuy);
        bnPublic = (Button) findViewById(R.id.bnPublic);
        lvScrollLoadData();
        initAdapter();
    }

    private void initAdapter()
    {
        lvAdapter = new CommonAdapter<IndexTeamBuyListResponse.GroupBuyingListBean>(mContext, datas, R.layout.item_index)
        {
            @Override
            public void convert(ViewHolder helper, final IndexTeamBuyListResponse.GroupBuyingListBean item)
            {
                ImageView ivIcon = helper.getView(R.id.ivIcon);
                GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1(), ivIcon, R.drawable.default_bg);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvTime, item.getCreateTime());
                helper.setText(R.id.tvDes, item.getDescription());
                helper.setText(R.id.tvInfo, "已拍/上线:");
                helper.setText(R.id.tvPrice, "¥" + item.getPrice());
                if (item.getComments() != null && item.getComments().length() > 0 && GeneralUtils.isNotNullOrZeroLenght(item.getComments()))
                {
                    helper.getView(R.id.llComment).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tvComment, item.getComments());
                }
                else
                {
                    helper.getView(R.id.llComment).setVisibility(View.GONE);
                }
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
        UserServiceImpl.instance().groupTeamBuyList("", 2, loadPage, IndexTeamBuyListResponse.class.getName());
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
        bnPublic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(mContext, PublicTeamBuyActy.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                    startActivity(intent);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }

            }
        });
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
            if (tag.equals(IndexTeamBuyListResponse.class.getName()))
            {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    IndexTeamBuyListResponse IndexTeamBuyListResponse = GsonHelper.toType(result, IndexTeamBuyListResponse.class);
                    if (Constants.SUCESS_CODE.equals(IndexTeamBuyListResponse.getResultCode()))
                    {
                        if (loadPage == 1)
                        {
                            datas.clear();
                        }
                        page = loadPage;
                        tolcount = IndexTeamBuyListResponse.getTotalCount();
                        datas.addAll(IndexTeamBuyListResponse.getGroupBuyingList());
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
                        ErrorCode.doCode(mContext, IndexTeamBuyListResponse.getResultCode(), IndexTeamBuyListResponse.getDesc());
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
        headView.setTitleText("共享拼团");
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
