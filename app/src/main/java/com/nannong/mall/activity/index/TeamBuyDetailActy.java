package com.nannong.mall.activity.index;

import android.os.Bundle;
import android.view.View;

import com.nannong.mall.R;
import com.nannong.mall.response.index.ContentDetailResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

public class TeamBuyDetailActy extends BaseActivity implements View.OnClickListener
{


    private String contentID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_buy);

        contentID = getIntent().getStringExtra(IntentCode.contentID);

        initAll();
    }

    @Override
    public void initView()
    {

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.equalsTags(this, NotiTag.TAG_CLOSE_ACTIVITY, tag))
            {
                finish();
            }



        }
        else if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();

            if (tag.equals(ContentDetailResponse.class.getName()))
            {
                ContentDetailResponse mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode()))
                    {
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


}
