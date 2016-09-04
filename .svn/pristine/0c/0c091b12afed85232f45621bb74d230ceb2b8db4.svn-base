package com.nannong.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.friend.PublishActivity;
import com.nannong.mall.adapter.community.CommunityItemAdapter;
import com.nannong.mall.response.community.PublicCommunityResponse;
import com.nannong.mall.response.friend.PublicTZResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.ArticleListBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 社区
 * Created by jwei on 2016/8/24 0024.
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener
{
    private static CommunityFragment instance;

    private View mView;

    /**
     * 标题(需要改变具体的标题名)
     */
    private HeadView headView;

    private ListView communityList;

    private int pageNo = 1;

    private int loadPageNo = 1;

    private int pageSize = Constants.LIST_NUM;

    private View loadingView;

    /**
     * 底部正在加载
     */
    private LinearLayout llLoading;

    /**
     * 点击 加载更多
     */
    private TextView tvSeeMoreDate;

    /**
     * 是否可以加载更多。如果上次加载数小于pageSize ,则默认没有更多数据了
     */
    private boolean canSeeMore = true;


    private CommunityItemAdapter adapter;

    /**
     * 总页数
     */
    private int totalCount = 0;


    private List<ArticleListBean> datas = new ArrayList<>();

    private ArrayList<ArticleListBean> datasMore = new ArrayList<>();

    private boolean isLoadingMoreData;

    /**
     * 下拉刷新
     */
    private PtrClassicFrameLayout refreshLayout;

    private boolean isFirst = true;

    private int lastVisibileItem;

    private VaryViewHelper mVaryViewHelper;

    public CommunityFragment()
    {
    }

    public static CommunityFragment getInstance()
    {
        if (instance == null)
        {
            instance = new CommunityFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.from(getActivity()).inflate(R.layout.fragment_community, null);
        initTitle();
        initAll(mView);
        return mView;
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
        if (getUserVisibleHint())
        {
        }
    }

    private void initData()
    {
        isLoadingMoreData = true;
        UserServiceImpl.instance().getCommunityList(getActivity(), loadPageNo, pageSize, PublicCommunityResponse.class.getName());
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName()))
            {
//                Intent intent = new Intent(getActivity(), PublicCommentActy.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
//                startActivity(intent);
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName()))
            {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                startActivity(intent);
//                if (GeneralUtils.isLogin())
//                {
//                    Intent intent = new Intent(getActivity(), PublishActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
//                    startActivity(intent);
//                }
//                else
//                {
//                    startActivity(new Intent(getActivity(), LoginActy.class));
//                }
            }
            else if (NotiTag.TAG_APP_INIT_SUCCESS.equals(tag))
            {
                headView.setTitleText(SharePref.getString(Constants.initGetZoneName, "社区"));
            }
            //点击跳转到社区，当前无数据时，则主动再次加载
            else if (NotiTag.TAG_TO_COMMUNITY.equals(tag) && !isLoadingMoreData && datas.size() == 0)
            {
                mVaryViewHelper.showLoadingView();
                loadPageNo = 1;
                initData();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            llLoading.setVisibility(View.GONE);
            refreshLayout.refreshComplete();
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            //如果有发布成功的帖子，就自动刷新
            if (tag.equals(PublicTZResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    PublicTZResponse mPublicTZResponse = GsonHelper.toType(result, PublicTZResponse.class);
                    if (Constants.SUCESS_CODE.equals(mPublicTZResponse.getResultCode()))
                    {
                        loadPageNo = 1;
                        isLoadingMoreData = true;
                        UserServiceImpl.instance().getCommunityList(getActivity(), loadPageNo, pageSize, PublicCommunityResponse.class.getName());
                    }
                    else
                    {
                        ErrorCode.doCode(getActivity(), mPublicTZResponse.getResultCode(), mPublicTZResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(PublicCommunityResponse.class.getName()))
            {
                mVaryViewHelper.showDataView();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    PublicCommunityResponse mGetCommunityListResponse = GsonHelper.toType(result, PublicCommunityResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetCommunityListResponse.getResultCode()))
                    {
                        //总页数
                        totalCount = mGetCommunityListResponse.getTotalCount();
                        datasMore.clear();
                        datasMore = (ArrayList<ArticleListBean>) mGetCommunityListResponse.getArticleList();

                        if (datasMore == null)
                        {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        }
                        else
                        {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0)
                        { //无记录
                            noData();
                            mVaryViewHelper.showEmptyView();
                        }
                        else
                        {//有记录
                            tvSeeMoreDate.setVisibility(View.VISIBLE);
                            pageNo = loadPageNo;
                            if (loadPageNo == 1)
                            {
                                datas.clear();
                                datas.addAll(datasMore);
                            }
                            adapter.notifyDataSetChanged();
                            if (mGetCommunityListResponse.getTotalCount() <= datas.size())
                            {
                                tvSeeMoreDate.setText("已加载完毕");
                            }
                        }
                    }
                    else
                    {
                        loadPageNo = pageNo;
                        if (datas != null && datas.size() == 0)
                        {
                            noData();
                            mVaryViewHelper.showEmptyView();
                        }
                        ErrorCode.doCode(getActivity(), mGetCommunityListResponse.getResultCode(), mGetCommunityListResponse.getDesc());
                    }
                }
                else
                {
                    loadPageNo = pageNo;
                    if (datas != null && datas.size() == 0)
                    {
                        noData();
                        mVaryViewHelper.showErrorView();
                    }
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public void initView(View mView)
    {
        refreshLayout = (PtrClassicFrameLayout) mView.findViewById(R.id.refreshLayout);
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setResistance(1.7f);
        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshLayout.setDurationToClose(200);
        refreshLayout.setDurationToCloseHeader(1000);
        // default is false
        refreshLayout.setPullToRefresh(false);
        // default is true
        refreshLayout.setKeepHeaderWhenRefresh(true);

        refreshLayout.disableWhenHorizontalMove(true);

        refreshLayout.setPtrHandler(new PtrHandler()
        {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame)
            {
                loadPageNo = 1;
                isLoadingMoreData = true;
                UserServiceImpl.instance().getCommunityList(getActivity(), loadPageNo, pageSize, PublicCommunityResponse.class.getName());
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                refreshLayout.autoRefresh();
                isFirst = false;
            }
        }, 200);
        communityList = (ListView) mView.findViewById(R.id.community_list);
        communityList.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == communityList.getCount())
                {
                    loadMore();
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
        tvSeeMoreDate.setOnClickListener(this);
        llLoading.setVisibility(View.GONE);
        tvSeeMoreDate.setVisibility(View.GONE);
        communityList.addFooterView(loadingView);
        communityList.setAdapter(adapter = new CommunityItemAdapter(getActivity(),
                datas, DisplayUtil.getWidth(getActivity())));

    }

    private void loadMore()
    {
        if (totalCount != 0 && datas.size() < totalCount && tvSeeMoreDate.getText().equals("加载更多"))
        {
            tvSeeMoreDate.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            loadPageNo = 1 + loadPageNo;
            initData();
        }
        else
        {
            tvSeeMoreDate.setText("已加载完毕");
            llLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewData()
    {
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(mView.findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理.
                .setEmptyView(R.mipmap.icon_empty_community, "社区暂无数据")//空页面，图+文字
                .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (GeneralUtils.isNetworkConnected(getActivity()))
                        {
                            mVaryViewHelper.showLoadingView();
                            loadPageNo = 1;
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

    private void initTitle()
    {
        View view = mView.findViewById(R.id.common_back);
        setImmerseLayout(view);
        headView = new HeadView((ViewGroup) view);
        headView.setRightText("发帖");
        headView.setHiddenLeft();
        headView.setTitleText(SharePref.getString(Constants.initGetZoneName, "社区"));
    }


    private void noData()
    {
        isLoadingMoreData = false;
        loadPageNo = pageNo;
        tvSeeMoreDate.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.load_more_tv:
                //当前页数小于总页数
                loadMore();
                break;
            case R.id.loading_test_ll:
                break;
        }
    }


}
