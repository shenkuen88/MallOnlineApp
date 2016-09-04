package com.nannong.mall.fragment.mine.bbs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.community.ReplyCommuniyActy;
import com.nannong.mall.activity.mine.FeedBackActivity;
import com.nannong.mall.activity.mine.MyBBSActy;
import com.nannong.mall.response.community.DeleteCommunityResponse;
import com.nannong.mall.response.community.GetMyWriteResponse;

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
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenu;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuCreator;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuItem;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuListView;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;
import de.greenrobot.event.EventBus;

/**
 * 我的主帖
 */
public class WriteFragment extends BaseFragment implements View.OnClickListener
{


    private SwipeMenuListView lvAddress;

    private View noAddressView;

    private CommonAdapter<ArticleListBean> mAdapter;

    private View mView;

    private int deletePosition = -1;

    private int pageNo = 1;

    private int loadPageNo = 1;

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
     * 总页数
     */
    private int totalCount = 0;

    private View noCommunity;

    private View llBottom;

    private int lastVisibileItem;

    private List<String> deleteList = new ArrayList<>();


    private VaryViewHelper mVaryViewHelper;

    public List<ArticleListBean> getDatas()
    {
        return datas;
    }

    private List<ArticleListBean> datas = new ArrayList<>();

    private ArrayList<ArticleListBean> datasMore = new ArrayList<>();

    private ArrayList<ArticleListBean> checkItemList = new ArrayList<>();

    /**
     * 底部，全选 或取消
     */
    private TextView tvAll;

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        mView = inflater.inflate(R.layout.fragment_write, null);
        initAll(mView);
        return mView;
    }

    @Override
    public void initView(View view)
    {
        tvAll = (TextView) mView.findViewById(R.id.all_tv);
        tvAll.setOnClickListener(this);
        llBottom = mView.findViewById(R.id.bottom_ll);
        mView.findViewById(R.id.delete_tv).setOnClickListener(this);
        lvAddress = (SwipeMenuListView) mView.findViewById(R.id.address_lv);
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        llLoading.setVisibility(View.GONE);
        tvSeeMoreDate = (TextView) loadingView.findViewById(R.id.load_more_tv);
        tvSeeMoreDate.setOnClickListener(this);
        lvAddress.addFooterView(loadingView);
        lvAddress.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem + 1) == lvAddress.getCount())
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
        initAdapter();
        if (datas.size() == 0)
        {
            initData();
        }
        else
        {
            tvSeeMoreDate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initViewData()
    {
        if (datas.size() == 0)
        {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(mView.findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                    .setEmptyView(R.mipmap.ic_launcher, "您还没有发布任何内容哦")//空页面，图+文字
                    .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                    {
                        @Override
                        public void onClick(View v)
                        {
                        if (GeneralUtils.isNetworkConnected(getActivity())){
                            mVaryViewHelper.showLoadingView();
                            loadPageNo = 1;
                            initData();
                        }else {
                            ToastUtil.showError(getActivity());
                        }
                        }
                    })//错误页点击刷新实现
                    .build(getActivity());
            mVaryViewHelper.showLoadingView();
        }
    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_EDIT_TZ.equals(tag))
            {
                checkItemList.clear();
                mAdapter.notifyDataSetChanged();
                if (SharePref.getString(Constants.community_edit, "").equals("编辑"))
                {
                    llBottom.setVisibility(View.GONE);
                }
                else
                {
                    llBottom.setVisibility(View.VISIBLE);
                    tvAll.setText("全选");
                }
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            llLoading.setVisibility(View.GONE);
            tvSeeMoreDate.setVisibility(View.VISIBLE);
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(GetMyWriteResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    GetMyWriteResponse mGetlvAddressResponse = GsonHelper.toType(result, GetMyWriteResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetlvAddressResponse.getResultCode()))
                    {
                        //总页数
                        totalCount = mGetlvAddressResponse.getTotalCount();
                        datasMore.clear();
                        datasMore = (ArrayList<ArticleListBean>) mGetlvAddressResponse.getArticleList();
                        if (datasMore == null)
                        {
                            loadPageNo = pageNo;
                            datasMore = new ArrayList<>();  //防止加载数据为null
                        }
                        else
                        {
                            if (loadPageNo == 1)
                            {
                                datas.clear();
                            }
                            datas.addAll(datasMore);
                        }
                        if (datas != null && datas.size() == 0)
                        { //无记录
                            loadPageNo = pageNo;
                            mVaryViewHelper.showEmptyView();
                        }
                        else
                        {//有记录
                            mVaryViewHelper.showDataView();
                            pageNo = loadPageNo;
                            mAdapter.notifyDataSetChanged();
                            if (datas.size() >= totalCount)
                            {
                                llLoading.setVisibility(View.GONE);
                                tvSeeMoreDate.setVisibility(View.VISIBLE);
                                tvSeeMoreDate.setText("已加载完毕");
                            }
                        }
                    }
                    else
                    {
                        loadPageNo = pageNo;
                        if (datas != null && datas.size() == 0)
                        { //无记录
                            mVaryViewHelper.showEmptyView();
                        }
                        ErrorCode.doCode(getActivity(), mGetlvAddressResponse.getResultCode(), mGetlvAddressResponse.getDesc());
                    }
                }
                else
                {
                    loadPageNo = pageNo;
                    if (datas != null && datas.size() == 0)
                    { //无记录
                        mVaryViewHelper.showErrorView();
                    }
                    ToastUtil.showError(getActivity());
                }
            }
            else if (tag.equals(DeleteCommunityResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    DeleteCommunityResponse mDeleteAddressResponse = GsonHelper.toType(result, DeleteCommunityResponse.class);
                    if (Constants.SUCESS_CODE.equals(mDeleteAddressResponse.getResultCode()))
                    {
                        ToastUtil.makeText(getActivity(), "删除成功!");
                        if (deleteList.size() == 1 && deletePosition != -1)
                        {
                            deleteList.clear();
                            checkItemList.remove(datas.get(deletePosition));
                            datas.remove(deletePosition);
                            deletePosition = -1;
                            mAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            loadPageNo = 1;
                            initData();
                            deleteList.clear();
                        }
                    }
                    else
                    {
                        deleteList.clear();
                        ErrorCode.doCode(getActivity(), mDeleteAddressResponse.getResultCode(), mDeleteAddressResponse.getDesc());
                    }
                }
                else
                {
                    deleteList.clear();
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }


    private void loadMore()
    {
        if (totalCount != 0 && datas.size() < totalCount && tvSeeMoreDate.getText().equals("加载更多"))
        {
            loadPageNo = 1 + loadPageNo;
            llLoading.setVisibility(View.VISIBLE);
            tvSeeMoreDate.setVisibility(View.GONE);
            initData();
        }
        else
        {
            llLoading.setVisibility(View.GONE);
            tvSeeMoreDate.setVisibility(View.VISIBLE);
            tvSeeMoreDate.setText("已加载完毕");
        }
    }

    private void initAdapter()
    {
        mAdapter =
                new CommonAdapter<ArticleListBean>(getActivity(), datas, R.layout.item_write)
                {
                    @Override
                    public void convert(ViewHolder helper, final ArticleListBean item)
                    {
                        helper.setText(R.id.title_tv, item.getTitle());
                        helper.setText(R.id.time_tv, item.getCreateTime());
                        helper.setText(R.id.community_tv, item.getCommunityName());
                        final CheckBox cb = helper.getView(R.id.show);
                        helper.getView(R.id.item_rl).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (SharePref.getString(Constants.community_edit, "").equals("编辑"))
                                {
                                    Intent intent = new Intent(mContext, ReplyCommuniyActy.class);
                                    intent.putExtra(Constants.ARTICLE_ITEM_CONETNT, item.getArticleID());
                                    mContext.startActivity(intent);
                                }
                                else
                                {
                                    if (cb.isChecked())
                                    {
                                        cb.setChecked(false);
                                    }
                                    else
                                    {
                                        cb.setChecked(true);
                                    }
                                }
                            }
                        });

                        if (SharePref.getString(Constants.community_edit, "").equals("编辑"))
                        {
                            cb.setVisibility(View.GONE);
                        }
                        else
                        {
                            if (checkItemList.contains(item))
                            {
                                cb.setChecked(true);
                            }
                            else
                            {
                                cb.setChecked(false);
                            }
                            cb.setVisibility(View.VISIBLE);
                        }
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                        {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                            {
                                if (isChecked)
                                {
                                    if (!checkItemList.contains(item))
                                    {
                                        checkItemList.add(item);
                                    }
                                }
                                else
                                {
                                    if (checkItemList.contains(item))
                                    {
                                        checkItemList.remove(item);
                                    }
                                }
//                                CMLog.e(Constants.HTTP_TAG, "选中个数：" + checkItemList.size());
                            }
                        });
                    }
                };
        lvAddress.setAdapter(mAdapter);
        initLeftSlideList(lvAddress);
    }

    //初始化向左滑动出现删除按钮
    private void initLeftSlideList(SwipeMenuListView listview)
    {
        SwipeMenuCreator creator = new SwipeMenuCreator()
        {

            @Override
            public void create(SwipeMenu menu)
            {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.app_color)));
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(getActivity(), 60));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index)
            {
                NetLoadingDialog.getInstance().loading(getActivity());
                deletePosition = position;
                UserServiceImpl.instance().deleteCommunity(datas.get(position).getArticleID(),
                        DeleteCommunityResponse.class.getName());
                return false;
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.delete_tv:
                if (checkItemList.size() > 0)
                {
                    for (int i = 0; i < checkItemList.size(); i++)
                    {
                        if (datas.contains(checkItemList.get(i)))
                        {
                            deletePosition = datas.indexOf(checkItemList.get(i));
                            deleteList.add(checkItemList.get(i).getArticleID());
                        }
                    }
                    if (deleteList.size() > 0)
                    {
                        UserServiceImpl.instance().deleteCommunity(deleteList,
                                DeleteCommunityResponse.class.getName());
                    }
                }
                ((MyBBSActy) getActivity()).getHeadView().setRightText("编辑");
                SharePref.saveString(Constants.community_edit, "编辑");
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_EDIT_TZ));
                break;
            case R.id.all_tv:
                if (tvAll.getText().toString().equals("全选"))
                {
                    checkItemList.clear();
                    for (int i = 0; i < datas.size(); i++)
                    {
                        checkItemList.add(datas.get(i));
                    }
                    tvAll.setText("取消全选");
                }
                else
                {
                    checkItemList.clear();
                    tvAll.setText("全选");
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.suggest_rl:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.load_more_tv:
                //当前页数小于总页数
                loadMore();
                break;

        }
    }

    private void initData()
    {
        llLoading.setVisibility(View.VISIBLE);
        UserServiceImpl.instance().getMyCommunityList(getActivity(), loadPageNo, GetMyWriteResponse.class.getName());
    }
}
