package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
 * 充值
 *
 * @author huqing
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener
{

    private TextView tvBalance;

    private GridView gvType;

    private RelativeLayout zfb_rl, wx_rl;

    private CheckBox zhb_cb, wx_cb;

    private Button bnRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initTitle();
        initAll();
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("充值");
    }

    @Override
    public void initView()
    {
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        gvType = (GridView) findViewById(R.id.gvType);
        zfb_rl = (RelativeLayout) findViewById(R.id.zfb_rl);
        wx_rl = (RelativeLayout) findViewById(R.id.wx_rl);
        zhb_cb = (CheckBox) findViewById(R.id.zhb_cb);
        wx_cb = (CheckBox) findViewById(R.id.wx_cb);
        bnRecharge = (Button) findViewById(R.id.bnRecharge);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bnRecharge:
                startActivity(new Intent(mContext,RechargeSuccessActivity.class));
                finish();
                break;
            case R.id.zfb_rl:
                if (zhb_cb.isChecked())
                {
                    zhb_cb.setChecked(false);
                }
                else
                {
                    zhb_cb.setChecked(true);
                }
                break;
            case R.id.wx_rl:
                if (wx_cb.isChecked())
                {
                    wx_cb.setChecked(false);
                }
                else
                {
                    wx_cb.setChecked(true);
                }
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
        bnRecharge.setOnClickListener(this);
        wx_rl.setOnClickListener(this);
        zfb_rl.setOnClickListener(this);

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
