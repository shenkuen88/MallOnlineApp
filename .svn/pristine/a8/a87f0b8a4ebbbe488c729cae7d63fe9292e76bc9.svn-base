package com.nannong.mall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.nannong.mall.R;
import com.nannong.mall.response.im.Friend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.V;

/**
 * 朋友
 * Created by jwei on 2016/8/24 0024.
 */
public class FriendFragment extends BaseFragment
{
    private static FriendFragment instance;

    public FriendFragment()
    {
    }

    public static FriendFragment getInstance()
    {
        if (instance == null)
        {
            instance = new FriendFragment();
        }
        return instance;
    }
    private ListView friend_list;
    private List<Friend> friendList=new ArrayList<>();
    private CommonAdapter<Friend> mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.from(getActivity()).inflate(R.layout.fragment_friend, null);
        initAll(v);
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            getfriend();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getUserVisibleHint())
        {
            getfriend();
        }
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public void initView(View view)
    {
        initTitle(view);
        friend_list=V.f(view,R.id.friend_list);
    }

    private void initTitle(View mView)
    {
        View view = mView.findViewById(R.id.common_back);
        setImmerseLayout(view);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setHiddenRight();
        headView.setHiddenLeft();
        headView.setTitleText(SharePref.getString(Constants.initGetZoneName, "朋友"));
    }

    @Override
    public void initViewData()
    {
        mAdapter=new CommonAdapter<Friend>(getActivity(),friendList,R.layout.f_friend_item) {
            @Override
            public void convert(ViewHolder helper, Friend item) {
                helper.setText(R.id.name,item.getName());
                helper.setText(R.id.info,item.getInfo());
                helper.setText(R.id.last_time,item.getLasttime());
                if(item.getId().equals(jqid)){
                    helper.getView(R.id.jg_line).setVisibility(View.VISIBLE);
                }else{
                    helper.getView(R.id.jg_line).setVisibility(View.GONE);
                }
                ImageView pic=helper.getView(R.id.pic);
                if(GeneralUtils.isNotNullOrZeroLenght(item.getPic())) {
                    if (item.getType().equals("2")) {
                        GeneralUtils.setRoundImageViewWithUrl(getActivity(),item.getPic(),pic,R.drawable.default_head);
                    }else{
                        GeneralUtils.setImageViewWithUrl(getActivity(),item.getPic(),pic,R.drawable.default_bg);

                    }
                }

            }
        };
        friend_list.setAdapter(mAdapter);
    }

    @Override
    public void initEvent()
    {

    }
    private String jqid="";
    private void getfriend(){
        friendList.clear();
        for(int i=0;i<10;i++){
            Friend f=null;
            if(i==0||i==1){
                f=new Friend(""+i,"系统客服"+i,"最后一条记录"+i,"http://img3.imgtn.bdimg.com/it/u=3069331078,3601790781&fm=206&gp=0.jpg","下午 4:00","1");
            }else{
                f=new Friend(""+i,"朋友姓名"+i,"最后一条记录"+i,"http://img3.imgtn.bdimg.com/it/u=3069331078,3601790781&fm=206&gp=0.jpg","星期五","2");
            }
            friendList.add(f);
        }
        Collections.sort(friendList, new Comparator<Friend>() {
            public int compare(Friend arg0, Friend arg1) {
                return arg0.getType().compareTo(arg1.getType());
            }
        });
        for(int i=0;i<friendList.size();i++){
            if(friendList.get(i).getType().equals("2")){
                if(i>1) {
                    jqid = friendList.get(i - 1).getId();
                }
                break;
            }
        }
        mAdapter.setData(friendList);
        mAdapter.notifyDataSetChanged();
    }
}
