package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.YZMResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.ClearEditText;


/**
 * 注册
 */
public class RegistCodeActy extends BaseActivity implements View.OnClickListener {


    private Button registBn;
    private ClearEditText etPhone;
    private String formerPhone;


    private TextView tvAggreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("手机快速注册");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView() {
        initTitle();
        etPhone = (ClearEditText) findViewById(R.id.app_phone_et);
        registBn = (Button) findViewById(R.id.app_register_next_bn);
        tvAggreement = (TextView) findViewById(R.id.app_aggrement_tv);
        tvAggreement.setOnClickListener(this);
        registBn.setOnClickListener(this);

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
    public void onEventMainThread(BaseResponse event) {
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
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(YZMResponse.class.getName())&&BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    YZMResponse mYZMResponse = GsonHelper.toType(result, YZMResponse.class);
                    if (Constants.SUCESS_CODE.equals(mYZMResponse.getResultCode())) {
                        //获取验证码成功后，跳转到注册页面
                        Intent intent = new Intent(mContext,RegistSetPasswordActy.class);
                        intent.putExtra(IntentCode.REGISTER_PHONE,formerPhone);
                        startActivity(intent);
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mYZMResponse.getResultCode(), mYZMResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_register_next_bn:
                formerPhone = etPhone.getText().toString().trim();
                if (formerPhone.length() >0) {
                    NetLoadingDialog.getInstance().loading(this);
                    UserServiceImpl.instance().getYZMCode( "1", formerPhone, YZMResponse.class.getName());
                } else {
                    ToastUtil.makeText(mContext, getString(R.string.app_input_error));
                }
                break;
            case R.id.app_aggrement_tv:

        }
    }


}
