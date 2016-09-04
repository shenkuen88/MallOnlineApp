package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.nannong.mall.R;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.NetLoadingDialog;


/**
 * 
 * 关于我们
 * 
 * @author  huqing
 */
public class AboutUsActivity extends BaseActivity
{
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initTitle();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("关于我们");
    }
    @Override
    public void initView() {

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

    @Override
    public void onEventMainThread(BaseResponse event) throws Exception {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
        }
    }
    
}
