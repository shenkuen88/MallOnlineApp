package com.nannong.mall.fragment.mine.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.community.ReplyCommuniyActy;
import com.nannong.mall.activity.mine.MyBBSActy;
import com.nannong.mall.response.community.DeleteCommentResponse;
import com.nannong.mall.response.community.GetMyCommentResponse;

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
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;
import de.greenrobot.event.EventBus;

/**
 * 我的回帖
 */
public class ReplyFragment extends BaseFragment implements View.OnClickListener
{

    private ListView lvAddress;

    private View noAddressView;

    private CommonAdapter<GetMyCommentResponse.CommentListBean> mAdapter;

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

    private List<GetMyCommentResponse.CommentListBean> datas = new ArrayList<>();

    private ArrayList<GetMyCommentResponse.CommentListBean> datasMore = new ArrayList<>();

    private int lastVisibileItem;

    private View llBottom;

    private TextView tvAll;

    private ArrayList<GetMyCommentResponse.CommentListBean> checkItemList = new ArrayList<>();

    private List<String> deleteList = new ArrayList<>();

    private VaryViewHelper mVaryViewHelper;

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        mView = inflater.inflate(R.layout.fragment_reply, null);
        initAll(mView);
        return mView;
    }

    @Override
    public void initView(View view)
    {
        llBottom = mView.findViewById(R.id.bottom_ll);
        mView.findViewById(R.id.delete_tv).setOnClickListener(this);
        tvAll = (TextView) mView.findViewById(R.id.all_tv);
        tvAll.setOnClickListener(this);
        lvAddress = (ListView) mView.findViewById(R.id.address_lv);
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_foot, null);
        llLoading = (LinearLayout) loadingView.findViewById(R.id.loading_test_ll);
        llLoading.setVisibility(View.GONE);
        tvSeeMoreDate = (TextView) loadingView.findViewById(R.id.load_more_tv);
        tvSeeMoreDate.setOnClickListener(this);
        lvAddress.addFooterView(loadingView);
        initAdapter();
        lvAddress.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState)
            {
                //当前页数小于总页数
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
                    .setEmptyView(R.mipmap.icon_empty_community, "您还没有发布任何内容哦")//空页面，图+文字
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
            if (tag.equals(DeleteCommentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    DeleteCommentResponse mDeleteAddressResponse = GsonHelper.toType(result, DeleteCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mDeleteAddressResponse.getResultCode()))
                    {
                        ToastUtil.makeText(getActivity(), "删除成功!");
                        CMLog.e(Constants.HTTP_TAG, result);
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
            if (tag.equals(GetMyCommentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    mVaryViewHelper.showDataView();
                    GetMyCommentResponse mGetCommunityListResponse = GsonHelper.toType(result, GetMyCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGetCommunityListResponse.getResultCode()))
                    {
                        CMLog.e(Constants.HTTP_TAG, result);
                        //总页数
                        totalCount = mGetCommunityListResponse.getTotalCount();
                        datasMore.clear();
                        datasMore = (ArrayList<GetMyCommentResponse.CommentListBean>) mGetCommunityListResponse.getCommentList();
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
                            pageNo = loadPageNo;
                            mAdapter.notifyDataSetChanged();
                            if (datas.size() > totalCount)
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
                        ErrorCode.doCode(getActivity(), mGetCommunityListResponse.getResultCode(), mGetCommunityListResponse.getDesc());
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
                new CommonAdapter<GetMyCommentResponse.CommentListBean>(getActivity(), datas, R.layout.item_reply)
                {
                    @Override
                    public void convert(ViewHolder helper, final GetMyCommentResponse.CommentListBean item)
                    {
                        final CheckBox cb = helper.getView(R.id.show);
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
                            }
                        });
                        helper.setText(R.id.title_tv, "原帖" + item.getArticleTitle());
                        helper.setText(R.id.content_tv, "回复" + item.getText());
                        helper.setText(R.id.communnity_tv, item.getCommunityName());
                        helper.setText(R.id.time_tv, item.getCreateTimeShow());
                        helper.getView(R.id.item_rl).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (SharePref.getString(Constants.community_edit, "").equals("编辑"))
                                {
                                    Intent intent = new Intent(mContext, ReplyCommuniyActy.class);
                                    intent.putExtra(Constants.ARTICLE_ITEM_CONETNT, item.getObjectID());
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
                    }
                };
        lvAddress.setAdapter(mAdapter);
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
            case R.id.delete_tv:
                if (checkItemList.size() > 0)
                {
                    for (int i = 0; i < checkItemList.size(); i++)
                    {
                        if (datas.contains(checkItemList.get(i)))
                        {
                            deletePosition = datas.indexOf(checkItemList.get(i));
                            deleteList.add(checkItemList.get(i).getObjectID());
                        }
                    }
                    if (deleteList.size() > 0)
                    {
                        UserServiceImpl.instance().deleteComment(deleteList,
                                DeleteCommentResponse.class.getName());
                    }

                }
                ((MyBBSActy) getActivity()).getHeadView().setRightText("编辑");
                SharePref.saveString(Constants.community_edit, "编辑");
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_EDIT_TZ));
                break;
        }
    }

    private void initData()
    {
        llLoading.setVisibility(View.VISIBLE);
        UserServiceImpl.instance().getMyCommentList(getActivity(), loadPageNo, GetMyCommentResponse.class.getName());
    }
}
