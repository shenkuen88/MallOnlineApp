package com.nannong.mall.activity.index;

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
import com.nannong.mall.response.index.AppraiseListBean;
import com.nannong.mall.response.index.ProductCommentResponse;

import java.util.ArrayList;

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
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * Created by qing on 2016/7/24.
 * 商品评论列表
 */
public class ProductCommentActy extends BaseActivity
{

    private ListView comment_lv;
    private int pageNo = 1;
    private int loadPageNo = 1;
    private int pageSize = Constants.LIST_NUM;
    private LinearLayout llLoading;
    private TextView tvLoadMore;
    private boolean isLoadingMoreData;
    private int pageTotal = 0;
    /**
     * 适配器
     */
    private CommonAdapter<AppraiseListBean> adapter;
    /**
     * 存放数据记录
     */
    private ArrayList<AppraiseListBean> datas = new ArrayList<>();
    /**
     * 加载更多 数据
     */
    private ArrayList<AppraiseListBean> datasMore;
    /**
     * 下拉刷新
     */
    private String contentId = "1";
    private View loadingView;
    private int lastVisibileItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_comment);
        initTitle();
        initAll();
        initAdapter();
        initData(1);

    }

    private void initAdapter() {
        adapter = new CommonAdapter<AppraiseListBean>(mContext, datas, R.layout.item_product_comment) {
            @Override
            public void convert(ViewHolder helper, final AppraiseListBean item) {
                ImageView ivHead = helper.getView(R.id.comment_head_iv);
                GeneralUtils.setRoundImageViewWithUrl(mContext, item.getUserPortrait(), ivHead, R.drawable.default_head);
                helper.setText(R.id.comment_name_tv, item.getUserNickName());
                helper.setText(R.id.comment_time_tv, item.getCreateTime());
                helper.setText(R.id.comment_content_tv, item.getText());
                if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl1())) {
                    ImageView iv1 = helper.getView(R.id.iv1);
                    ImageView iv2 = helper.getView(R.id.iv2);
                    ImageView iv3 = helper.getView(R.id.iv3);
                    ImageView iv4 = helper.getView(R.id.iv4);
                    helper.getView(R.id.image_ll).setVisibility(View.VISIBLE);
                    GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl1(), iv1, R.drawable.default_bg);
                    if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl2())) {
                        GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl2(), iv2, R.drawable.default_bg);
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl3())) {
                            GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl3(), iv3, R.drawable.default_bg);
                            if (GeneralUtils.isNotNullOrZeroLenght(item.getPicUrl4())) {
                                GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl4(), iv4, R.drawable.default_bg);
                            } else {
                                iv4.setVisibility(View.INVISIBLE);
                                return;
                            }
                        } else {
                            iv3.setVisibility(View.INVISIBLE);
                            return;
                        }
                    } else {
                        iv2.setVisibility(View.INVISIBLE);
                        return;
                    }
                } else {
                    helper.getView(R.id.image_ll).setVisibility(View.GONE);
                    return;
                }
            }
        };
        comment_lv.setAdapter(adapter);
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
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
            tvLoadMore.setVisibility(View.VISIBLE);
            llLoading.setVisibility(View.GONE);
            //获取评论列表
            if (tag.equals(ProductCommentResponse.class.getName())) {
                llLoading.setVisibility(View.GONE);
                tvLoadMore.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ProductCommentResponse mGetCommonCommentResponse = GsonHelper.toType(result, ProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetCommonCommentResponse.getResultCode())) {
                        pageTotal = mGetCommonCommentResponse.getTotalCount();
                        if (loadPageNo == 1) {
                            datas = new ArrayList<>();
                            datasMore = new ArrayList<>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<AppraiseListBean>) mGetCommonCommentResponse.getAppraiseList();
                        if (datasMore == null) {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        } else {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0) { //无记录
                            comment_lv.setVisibility(View.GONE);
                        } else {//有记录
                            adapter.setData(datas);
                            adapter.notifyDataSetChanged();
                            if (isLoadingMoreData) {
                                pageNo = loadPageNo;
                                isLoadingMoreData = false;
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mGetCommonCommentResponse.getResultCode(), mGetCommonCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("评论");
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        comment_lv = (ListView) findViewById(R.id.comment_lv);
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        tvLoadMore = (TextView) loadingView.findViewById(R.id.load_more_tv);
        comment_lv.addFooterView(loadingView);
        llLoading.setVisibility(View.VISIBLE);
        tvLoadMore.setVisibility(View.GONE);

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
        if (pageTotal != 0 && pageNo < pageTotal && tvLoadMore.getText().equals("加载更多")) {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
            UserServiceImpl.instance().getProductCommentList(contentId, loadPageNo, ProductCommentResponse.class.getName());
        } else {
            tvLoadMore.setText("已加载完毕");
        }
    }

    private void initData(int loadPageNo) {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getProductCommentList(contentId, loadPageNo, ProductCommentResponse.class.getName());

    }


    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }
}
