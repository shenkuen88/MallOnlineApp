package com.nannong.mall.activity.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.response.community.AddZanResponse;
import com.nannong.mall.response.community.CommentListBean;
import com.nannong.mall.response.community.CommentResponse;
import com.nannong.mall.response.community.DetailResponse;
import com.nannong.mall.response.community.GetCommonCommentResponse;
import com.nannong.mall.view.chooseimage.CommunityImageZoomActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.ArticleListBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
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
import cn.nj.www.my_module.view.MyListView;
import cn.nj.www.my_module.view.goodview.GoodView;
import cn.nj.www.my_module.view.nine_image.ImageBean;
import cn.nj.www.my_module.view.nine_image.NineGridAdapter;
import cn.nj.www.my_module.view.nine_image.NineGridlayout;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;

/**
 * Created by huqing on 2016/7/4.
 * 发帖子
 */
public class ReplyCommuniyActy extends BaseActivity implements View.OnClickListener
{

    private Button commentBn;

    private EditText commentEt;

    private ArticleListBean article;

    private TextView tvZan;

    private TextView tvName;

    private TextView tvTime;

    private TextView tvContent;

    private TextView tvComment;

    private MyListView lvComment;

    private ImageView ivHead;

    private int pageNo = 1;

    private int loadPageNo = 1;

    private int pageSize = Constants.LIST_NUM;

    /**
     * 适配器
     */
    private CommonAdapter<CommentListBean> adapter;

    /**
     * 存放数据记录
     */
    private ArrayList<CommentListBean> datas = new ArrayList<>();

    /**
     * 加载更多 数据
     */
    private ArrayList<CommentListBean> datasMore;

    private boolean isLoadingMoreData;

    private LinearLayout llLoading;

    private TextView tvLoadMore;

    private ScrollView scrollView;

    private int pageTotal = 0;

    private View loadingView;

    private String articleId;

    /**
     * 九宫格
     */
    private NineGridlayout ivMore;

    /**
     * 九宫格的适配器
     */
    private DefineNinePicAdapter mImageAdapter;

    private VaryViewHelper mVaryViewHelper;

//    private FloatingText normalFloatingText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community_reply);
        initTitle();
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("详情");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.head_iv:
                if (GeneralUtils.isNotNullOrZeroLenght(article.getUserPortrait()))
                {
                    List<ImageBean> image = new ArrayList<>();
                    image.add(new ImageBean(article.getUserPortrait(), 250, 250));
                    Intent intent = new Intent(mContext, CommunityImageZoomActivity.class);
                    intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION, 0);
                    intent.putExtra(IntentCode.COMMUNITY_IMAGE_DATA, (Serializable) image);
                    mContext.startActivity(intent);
                }

                break;
            case R.id.comment_bn:
                if (!GeneralUtils.isLogin())
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                else if (GeneralUtils.isNotNullOrZeroLenght(commentEt.getText().toString()))
                {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().addComment(
                            "1", articleId, commentEt.getText().toString(),
                            CommentResponse.class.getName());
                }
                else
                {
                    ToastUtil.makeText(mContext, "请输入内容");
                }
                break;
            case R.id.zan_tv:

                if (!GeneralUtils.isLogin())
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                if (!GeneralUtils.isNetworkConnected(mContext))
                {
                    ToastUtil.showError(mContext);
                }
                else
                {
                    UserServiceImpl.instance().addZan("1", articleId, AddZanResponse.class.getName());
                }

                break;
            case R.id.comment_tv:
                if (!GeneralUtils.isLogin())
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                else
                {
                    commentEt.setFocusable(true);
                    commentEt.setFocusableInTouchMode(true);
                    commentEt.requestFocus();
                    InputMethodManager inputManager =
                            (InputMethodManager) commentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(commentEt, 0);
                }

                break;
        }
    }

    /**
     * 最后一个可见的item
     */
    private int lastVisibileItem;

    @Override
    public void initView()
    {
//        normalFloatingText = new FloatingText.FloatingTextBuilder(ReplyCommuniyActy.this)
//                .textColor(Color.RED)
//                .textSize(200)
//                .textContent("+1")
//                .offsetX(300)
//                .build();
//        normalFloatingText.attach2Window();
        ivMore = (NineGridlayout) findViewById(R.id.pic_gv);
        scrollView = (ScrollView) findViewById(R.id.reply_scrollView);
        scrollView.setVisibility(View.GONE);
        commentBn = (Button) findViewById(R.id.comment_bn);
        commentEt = (EditText) findViewById(R.id.comment_et);
        ivHead = (ImageView) findViewById(R.id.head_iv);
        tvName = (TextView) findViewById(R.id.name_tv);
        tvTime = (TextView) findViewById(R.id.time_tv);
        tvContent = (TextView) findViewById(R.id.content_tv);
        tvComment = (TextView) findViewById(R.id.comment_tv);
        tvZan = (TextView) findViewById(R.id.zan_tv);
        tvComment.setOnClickListener(this);

        lvComment = (MyListView) findViewById(R.id.comment_lv);
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        tvLoadMore = (TextView) loadingView.findViewById(R.id.load_more_tv);
        llLoading.setVisibility(View.VISIBLE);
        tvLoadMore.setVisibility(View.GONE);
        lvComment.addFooterView(loadingView);
        lvComment.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == lvComment.getCount())
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
        tvLoadMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadMore();
            }
        });
        initAdapter();
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                .setEmptyView(R.mipmap.icon_empty_community, "您还没有发布任何内容哦")//空页面，图+文字
                .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (GeneralUtils.isNetworkConnected(mContext))
                        {
                            mVaryViewHelper.showLoadingView();
                            loadPageNo = 1;
                            UserServiceImpl.instance().getCommentBean(articleId, DetailResponse.class.getName());
                            initCommentList();
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

    private void loadMore()
    {
        if (pageTotal != 0 && pageNo < pageTotal && tvLoadMore.getText().equals("加载更多"))
        {
            tvLoadMore.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
            isLoadingMoreData = true;
            loadPageNo++;
            initCommentList();
        }
        else
        {
            tvLoadMore.setText("已加载完毕");
            llLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewData()
    {
        articleId = getIntent().getStringExtra(Constants.ARTICLE_ITEM_CONETNT);
        UserServiceImpl.instance().getCommentBean(articleId, DetailResponse.class.getName());
        initCommentList();
    }

    private void initCommentList()
    {
        UserServiceImpl.instance().getCommonCommentList(mContext, "1", articleId, loadPageNo, pageSize, GetCommonCommentResponse.class.getName());
    }

    @Override
    public void initEvent()
    {
        tvZan.setOnClickListener(this);
        commentBn.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag))
            {
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            //评论成功
            if (tag.equals(CommentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CommentResponse mCommentResponse = GsonHelper.toType(result, CommentResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mCommentResponse.getResultCode()))
                    {
                        commentEt.setText("");
                        if (datas.size() == 0)
                        {
                            pageNo = 1;
                            loadPageNo = 1;
                            initCommentList();
                        }
                        else
                        {
                            CommentListBean bean = mCommentResponse.getComment();
                            if (GeneralUtils.isNullOrZeroLenght(bean.getUserNickName()))
                            {
                                bean.setUserNickName(Global.getNickName());
                            }
                            if (GeneralUtils.isNullOrZeroLenght(bean.getThumPortrait()))
                            {
                                CMLog.e(Constants.HTTP_TAG, Global.getThumUserHeadUrl());
                                bean.setThumPortrait(Global.getThumUserHeadUrl());
                            }
                            if (GeneralUtils.isNullOrZeroLenght(bean.getCreateTime()))
                            {
                                bean.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
                            }
                            datas.add(0, bean);//加到头部   datas.add(bean);//在列表最末尾
                            adapter.notifyDataSetChanged();
//                            ToastUtil.makeText(mContext, "评论成功！");
                            tvComment.setText("评论(" + datas.size() + ")");
                            scrollView.scrollTo(0, 0);
                            GoodView goodView = new GoodView(this);
                            goodView.setTextInfo("+1", R.color.app_color, 14);
                            goodView.show(tvComment);
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mCommentResponse.getResultCode(), mCommentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            //点赞成功
            if (tag.equals(AddZanResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    AddZanResponse mAddZanResponse = GsonHelper.toType(result, AddZanResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mAddZanResponse.getResultCode()))
                    {
                        tvZan.setText("赞(" + (article.getGoodList().length() + 1) + ")");
                        GoodView goodView = new GoodView(this);
                        goodView.setTextInfo("+1", R.color.app_color, 14);
                        goodView.show(tvZan);
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mAddZanResponse.getResultCode(), mAddZanResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            //头部内容
            if (NotiTag.equalsTags(mContext, tag, DetailResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    DetailResponse mDetailResponse = GsonHelper.toType(result, DetailResponse.class);
                    if (Constants.SUCESS_CODE.equals(mDetailResponse.getResultCode()))
                    {
                        if (mDetailResponse.getCommentList().size() == 0)
                        {
                            loadingView.setVisibility(View.GONE);
                        }
                        scrollView.setVisibility(View.VISIBLE);
                        article = mDetailResponse.getArticle();
                        tvName.setText(article.getUserNickName());
                        tvTime.setText(article.getCreateTime());
                        tvContent.setText(article.getContent());
                        if (article.getGoodList().length() > 0)
                        {
                            tvZan.setText("赞(" + article.getGoodList().length() + ")");
                        }
                        else
                        {
                            tvZan.setText("赞(0)");
                        }
                        if (article.getThumPicUrlList().isEmpty())
                        {
                            ivMore.setVisibility(View.GONE);
                        }
                        else
                        {
                            ivMore.setVisibility(View.VISIBLE);
                            List<ImageBean> imageList = new ArrayList<>();
                            List<ImageBean> imageListALL = new ArrayList<>();
                            ivMore.setVisibility(View.VISIBLE);
                            if (article.getThumPicUrlList().size() == 0)
                            {
                                ivMore.setVisibility(View.GONE);
                            }
                            else if (article.getThumPicUrlList().size() == 1)
                            {
                                imageList.add(new ImageBean(article.getThumPicUrlList().get(0), 640, 960));
                                imageListALL.add(new ImageBean(article.getPicUrlList().get(0), 640, 960));
                                handlerOneImage(imageList, imageListALL);
                            }
                            else
                            {
                                for (int i = 0; i < article.getThumPicUrlList().size(); i++)
                                {
                                    imageList.add(new ImageBean(article.getThumPicUrlList().get(i), 250, 250));
                                    imageListALL.add(new ImageBean(article.getPicUrlList().get(i), 640, 960));
                                }
                                handlerOneImage(imageList, imageListALL);
                            }
                        }
                        GeneralUtils.setRoundImageViewWithUrl(mContext, article.getUserPortrait(), ivHead, R.drawable.default_head);
                        ivHead.setOnClickListener(this);
                        mVaryViewHelper.showDataView();
                    }
                    else
                    {
                        mVaryViewHelper.showErrorView();
                        ErrorCode.doCode(mContext, mDetailResponse.getResultCode(), mDetailResponse.getDesc());
                    }
                }
                else
                {
                    mVaryViewHelper.showErrorView();
                    ToastUtil.showError(mContext);
                }
            }
            //获取评论列表
            if (tag.equals(GetCommonCommentResponse.class.getName()))
            {
                llLoading.setVisibility(View.GONE);
                tvLoadMore.setVisibility(View.VISIBLE);
                NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    GetCommonCommentResponse mGetCommonCommentResponse = GsonHelper.toType(result, GetCommonCommentResponse.class);
                    CMLog.e(Constants.HTTP_TAG, result);
                    if (Constants.SUCESS_CODE.equals(mGetCommonCommentResponse.getResultCode()))
                    {
                        pageTotal = mGetCommonCommentResponse.getTotalCount();
                        if (loadPageNo == 1)
                        {
                            datas = new ArrayList<CommentListBean>();
                            datasMore = new ArrayList<CommentListBean>();
                        }
                        datasMore.clear();
                        datasMore = (ArrayList<CommentListBean>) mGetCommonCommentResponse.getCommentList();
                        if (datasMore == null)
                        {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<CommentListBean>();  //防止加载数据为null
                        }
                        else
                        {
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0)
                        { //无记录
//                            lvComment.setVisibility(View.GONE);
                            tvLoadMore.setVisibility(View.VISIBLE);
                            tvLoadMore.setText("已加载完毕");
                        }
                        else
                        {//有记录
                            adapter.setData(datas);
                            adapter.notifyDataSetChanged();
                            if (isLoadingMoreData)
                            {
                                pageNo = loadPageNo;
                                llLoading.setVisibility(View.GONE);
                                isLoadingMoreData = false;
                            }
                            if (mGetCommonCommentResponse.getTotalCount() > 0)
                            {
                                tvComment.setText("评论(" + mGetCommonCommentResponse.getTotalCount() + ")");
                            }
                            else
                            {
                                tvComment.setText("评论(0)");
                            }
                            if (datas.size() >= pageTotal)
                            {
                                tvLoadMore.setText("已加载完毕");
                            }
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mGetCommonCommentResponse.getResultCode(), mGetCommonCommentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void initAdapter()
    {
        adapter = new CommonAdapter<CommentListBean>(mContext, datas, R.layout.community_reply_item)
        {
            @Override
            public void convert(ViewHolder helper, final CommentListBean item)
            {
                ImageView imageView = (ImageView) helper.getView(R.id.head_iv);
                GeneralUtils.setRoundImageViewWithUrl(mContext, item.getThumPortrait(), imageView, R.drawable.default_head);
                helper.setText(R.id.content_tv, item.getText());
                helper.setText(R.id.time_tv, item.getCreateTime());
                helper.setText(R.id.name_tv, item.getUserNickName());
                List<ImageBean> image = new ArrayList<>();
                imageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (GeneralUtils.isNotNullOrZeroLenght(item.getUserPortrait()))
                        {
                            List<ImageBean> image = new ArrayList<>();
                            image.add(new ImageBean(item.getUserPortrait(), 250, 250));
                            Intent intent = new Intent(mContext, CommunityImageZoomActivity.class);
                            intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION, 0);
                            intent.putExtra(IntentCode.COMMUNITY_IMAGE_DATA, (Serializable) image);
                            mContext.startActivity(intent);
                        }

                    }
                });

            }
        };
        lvComment.setAdapter(adapter);
    }


    private void handlerOneImage(final List<ImageBean> image, final List<ImageBean> image1)
    {

        mImageAdapter = new DefineNinePicAdapter(mContext, image);
        ivMore.setAdapter(mImageAdapter);
        ivMore.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if (mContext != null)
                {
                    Intent intent = new Intent(mContext, CommunityImageZoomActivity.class);
                    intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION, position);
                    intent.putExtra(IntentCode.COMMUNITY_IMAGE_DATA, (Serializable) image1);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    class DefineNinePicAdapter extends NineGridAdapter
    {

        public DefineNinePicAdapter(Context context, List list)
        {
            super(context, list);
        }

        @Override
        public int getCount()
        {
            return (list == null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position)
        {
            return getItem(position) == null ? null : ((ImageBean) getItem(position)).getUrl();
        }

        @Override
        public Object getItem(int position)
        {
            return (list == null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View view)
        {

            ImageView iv = null;
            if (view != null && view instanceof ImageView)
            {
                iv = (ImageView) view;
            }
            else
            {
                iv = new ImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url = getUrl(position);
            GeneralUtils.setImageViewWithUrl(context, getUrl(position), iv, R.drawable.default_bg);
            if (!TextUtils.isEmpty(url))
            {
                iv.setTag(url);
            }
            return iv;
        }
    }
}
