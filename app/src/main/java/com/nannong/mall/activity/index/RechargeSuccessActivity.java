package com.nannong.mall.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.RechargeActivity;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.NetLoadingDialog;


/**
 * 充值成功
 *
 * @author huqing
 */
public class RechargeSuccessActivity extends BaseActivity implements View.OnClickListener
{


    private Button bnContinue, bnBack;

    private TextView tvNum, tvRecharge, tvLeft;

    /**
     * 查看更多充值记录
     */
    private TextView tvMoreRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_success);
        initTitle();
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("充值成功");
    }

    @Override
    public void initView()
    {
        bnContinue = (Button) findViewById(R.id.bnContinue);
        bnBack = (Button) findViewById(R.id.bnBack);
        tvRecharge = (TextView) findViewById(R.id.tvRecharge);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvMoreRecord = (TextView) findViewById(R.id.tvMoreRecord);
        tvNum = (TextView) findViewById(R.id.tvNum);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bnContinue:
                startActivity(new Intent(mContext, RechargeActivity.class));
                break;
            case R.id.bnBack:
                finish();
                break;
            case R.id.tvMoreRecord:
                finish();
                break;

        }

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {
        bnContinue.setOnClickListener(this);
        bnBack.setOnClickListener(this);
        tvMoreRecord.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
        }

    }


}
