package com.nannong.mall.activity.order.zfb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.order.NoticeListResponse;

import java.util.ArrayList;

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
import cn.nj.www.my_module.tools.ToastUtil;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 商品的消息
 * item_ststem_notice
 */
public class NoticeListActivity extends BaseActivity implements View.OnClickListener {

    private ListView comment_lv;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private int pageSize = Constants.LIST_NUM;
    private boolean isLoadingMoreData;

    /**
     * 适配器
     */
    private CommonAdapter<NoticeListResponse.NoticeListBean> adapter;
    /**
     * 存放数据记录
     */
    private ArrayList<NoticeListResponse.NoticeListBean> datas = new ArrayList<>();
    /**
     * 加载更多 数据
     */
    private ArrayList<NoticeListResponse.NoticeListBean> datasMore;
    /**
     * 下拉刷新
     */
    private PtrClassicFrameLayout refreshLayout;
    private NoticeListResponse mSystemMessageResponse;
    private View loadingView;
    private int lastVisibileItem;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private View loadView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_notice);
        initTitle();
        initAll();
        initAdapter();
        initData(1);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<NoticeListResponse.NoticeListBean>(mContext, datas, R.layout.item_notice) {
            @Override
            public void convert(ViewHolder helper, final NoticeListResponse.NoticeListBean item) {
                helper.setText(R.id.title_tv, item.getTitle());
                helper.setText(R.id.content_tv, item.getContent());
                helper.getView(R.id.item_ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentExplain = new Intent(mContext, CommonWebViewActivity.class);
                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, item.getTitle());
                        intentExplain.putExtra(IntentCode.COMMON_WEB_VIEW_URL, item.getLink());
                        startActivity(intentExplain);
                    }
                });
            }
        };
        comment_lv.setAdapter(adapter);
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("通知");
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        loadView = findViewById(R.id.loading_test);
        llLoading = (LinearLayout) findViewById(R.id.loading_test_ll);//正在加载
        tvLoadMore = (TextView) findViewById(R.id.load_more_tv);//加载更多
        llLoading.setVisibility(View.GONE);
        tvLoadMore.setVisibility(View.GONE);
        refreshLayout = (PtrClassicFrameLayout) findViewById(R.id.refreshLayout);
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

        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadPageNo = 1;
                initData(loadPageNo);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        comment_lv = (ListView) findViewById(R.id.comment_lv);
        comment_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == comment_lv.getCount())
                    loadMore();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibileItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });

    }

    private void loadMore() {
        if (mSystemMessageResponse != null && mSystemMessageResponse.getTotalCount() > datas.size() && tvLoadMore.getText().equals("加载更多")) {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
            initData(loadPageNo);
        } else {
            tvLoadMore.setText("已加载完毕");
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    private void initData(int loadPageNo) {
        NetLoadingDialog.getInstance().loading(mContext);
        if (loadPageNo != 1) {
            NetLoadingDialog.getInstance().loading(mContext);
        }
        UserServiceImpl.instance().getNoticeList(loadPageNo, NoticeListResponse.class.getName());

    }


    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            refreshLayout.refreshComplete();
            NetLoadingDialog.getInstance().dismissDialog();
            llLoading.setVisibility(View.GONE);
            //获取评论列表
            if (tag.equals(NoticeListResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    mSystemMessageResponse = GsonHelper.toType(result, NoticeListResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mSystemMessageResponse.getResultCode())) {
                        if (loadPageNo == 1) {
                            datas = new ArrayList<>();
                            datasMore = new ArrayList<>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<NoticeListResponse.NoticeListBean>) mSystemMessageResponse.getNoticeList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            comment_lv.setVisibility(View.GONE);
                            tvLoadMore.setVisibility(View.GONE);
                        } else {//有记录
                            comment_lv.setVisibility(View.VISIBLE);
                            adapter.setData(datas);
                            adapter.notifyDataSetChanged();
                            loadView.setVisibility(View.VISIBLE);
                            if (datas.size() >= mSystemMessageResponse.getTotalCount()) {
                                tvLoadMore.setText("已加载完毕");
                                tvLoadMore.setVisibility(View.GONE);
                            }else {
                                tvLoadMore.setVisibility(View.VISIBLE);
                            }
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                                isLoadingMoreData = false;
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mSystemMessageResponse.getResultCode(), mSystemMessageResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
