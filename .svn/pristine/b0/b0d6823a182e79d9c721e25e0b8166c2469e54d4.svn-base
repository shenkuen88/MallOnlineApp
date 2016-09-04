package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.nannong.mall.R;
import com.nannong.mall.fragment.mine.bbs.ReplyFragment;
import com.nannong.mall.fragment.mine.bbs.WriteFragment;
import com.nannong.mall.response.mine.LoginResponse;

import java.util.ArrayList;
import java.util.List;

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
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/19.
 * 我的帖子
 * my_bbs_item 我发布的帖子
 * my_reply_item 回复的
 */
public class MyBBSActy extends BaseActivity
{

    private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private List<String> list_title;                                     //tab名称列表
    private String[] tags;
    private WriteFragment mWriteFragment;              //热门推荐fragment
    private ReplyFragment mReplyFragment;            //热门收藏fragment
    private HeadView headView;

    public HeadView getHeadView() {
        return headView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_list);
        mWriteFragment = new WriteFragment();
        mReplyFragment = new ReplyFragment();
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
    }

    @Override
    public void initViewData() {
        tags = new String[]{WriteFragment.class.getName(), ReplyFragment.class.getName()};
        changeFragment(mWriteFragment);
        initControls();
    }


    private void changeFragment(Fragment targetFragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content_frame, targetFragment, targetFragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setTitleText("我的帖子");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("编辑");
        SharePref.saveString(Constants.community_edit, "编辑");
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.equalsTags(mContext, tag, NotiTag.TAG_DO_RIGHT)) {
                if (mWriteFragment.getDatas().size()==0){
                    return;
                }
                if (SharePref.getString(Constants.community_edit, "").equals("编辑")) {
                    SharePref.saveString(Constants.community_edit, "");
                    headView.setRightText("取消");
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_EDIT_TZ));
                } else {
                    headView.setRightText("编辑");
                    SharePref.saveString(Constants.community_edit, "编辑");
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_EDIT_TZ));
                }
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(LoginResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                    } else {
                        ErrorCode.doCode(mContext, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    /**
     * 初始化各控件
     */
    private void initControls() {
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("我的主帖");
        list_title.add("我的回帖");

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));

        tab_FindFragment_title.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("我的主帖")) {
                    changeFragment(mWriteFragment);
                    headView.setRightText("编辑");
                } else {
                    headView.setRightText("编辑");
                    changeFragment(mReplyFragment);
                }
                SharePref.saveString(Constants.community_edit, "编辑");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
