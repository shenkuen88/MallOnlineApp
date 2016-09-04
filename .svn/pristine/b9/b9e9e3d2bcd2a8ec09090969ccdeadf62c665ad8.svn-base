package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.CheckYZMResponse;
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
 * 找回密码 判断输入的验证码是否正确
 */
public class FindPasswordCodeActy extends BaseActivity implements View.OnClickListener {

    private Button registBn;
    private ClearEditText etPhone;
    private String formerPhone;


    private TextView tvAggreement;
    /**
     * 验证码按钮
     */
    private Button codeBn;
    private ClearEditText etCode;

    private boolean change;
    private MyTime myTime;
    private boolean hasGetCode;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password_code);
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("找回密码");
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
        codeBn = (Button) findViewById(R.id.app_code_bn);
        codeBn.setOnClickListener(this);
        etCode = (ClearEditText) findViewById(R.id.app_code_et);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (GeneralUtils.isNotNullOrZeroLenght(etCode.getText().toString())) {
                    registBn.setEnabled(true);
                }
            }
        });

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
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();

            //发送短信验证码
            if (tag.equals(YZMResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    YZMResponse mCheck = GsonHelper.toType(result, YZMResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCheck.getResultCode())) {
                        //成功发送验证码，
                        startTime();
                    } else {
                        ErrorCode.doCode(mContext, mCheck.getResultCode(), mCheck.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            //检验短信验证码
            if (tag.equals(CheckYZMResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CheckYZMResponse mCheck = GsonHelper.toType(result, CheckYZMResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCheck.getResultCode())) {
                        //验证码正确 跳转到设置密码页面
                        Intent intent = new Intent(mContext, FindAndSetPasswordActy.class);
                        intent.putExtra(IntentCode.REGISTER_PHONE, phoneNum);
                        intent.putExtra(IntentCode.EDIT_PASSWORD_IS_FORGET, "1");
                        startActivity(intent);
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mCheck.getResultCode(), mCheck.getDesc());
                        NetLoadingDialog.getInstance().dismissDialog();
                    }
                } else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
            }

        }

    }


    /**
     * 倒计时
     */
    private class MyTime extends CountDownTimer {
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            change = false;
            codeBn.setClickable(true);
            codeBn.setText(getString(R.string.register_code_sms_get));
            codeBn.setBackgroundResource(R.drawable.white_rec);
            codeBn.setTextColor(getResources().getColor(R.color.app_register_gray));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            change = true;
            codeBn.setClickable(false);
            codeBn.setBackgroundResource(R.drawable.gray_rec);
            codeBn.setText(getResources().getString(R.string.register_code_second,
                    (millisUntilFinished / 1000)));
            codeBn.setTextColor(getResources().getColor(R.color.register_text));
        }
    }

    private void startTime() {
        cancelTime();
        myTime = new MyTime(Constants.Countdown_start, Constants.Countdown_end);
        myTime.start();
    }

    private void cancelTime() {
        if (myTime != null) {
            myTime.cancel();
            myTime = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_code_bn:
                if (codeBn.getText().toString().trim().equals("获取验证码")&&GeneralUtils.isNotNullOrZeroLenght(etPhone.getText().toString())&&etPhone.getText().toString().length()>=11) {
                    NetLoadingDialog.getInstance().loading(this);
                    hasGetCode = true;
                    UserServiceImpl.instance().getYZMCode("3", etPhone.getText().toString(), YZMResponse.class.getName());
                }else {
                    ToastUtil.makeText(mContext,"请输入手机号");
                }
                break;
            case R.id.app_register_next_bn:
                if (!hasGetCode) {
                    ToastUtil.makeText(mContext, "请获取最新的验证码！");
                } else if (GeneralUtils.isNotNullOrZeroLenght(etCode.getText().toString()) &&
                        GeneralUtils.isNotNullOrZeroLenght(etPhone.getText().toString())
                        ) {
                    //验证短信验证码
                    phoneNum = etPhone.getText().toString();
                    UserServiceImpl.instance().checkYZMCode("3", phoneNum, etCode.getText().toString(), CheckYZMResponse.class.getName());

                }
                break;

        }
    }

}
