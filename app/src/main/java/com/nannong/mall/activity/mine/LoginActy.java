package com.nannong.mall.activity.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.MainActivity;
import com.nannong.mall.response.mine.LoginResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.StringEncrypt;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.ClearEditText;
import cn.nj.www.my_module.view.ClearPasswordEditText;
import de.greenrobot.event.EventBus;

public class LoginActy extends BaseActivity implements View.OnClickListener {

    private Button commitBn;
    private ClearEditText nameET;
    private ClearPasswordEditText psdET;
    private TextView forgetTv, registTv;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(mContext);
        setContentView(R.layout.activity_login_acty);
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("登录");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        initTitle();
        nameET = (ClearEditText) findViewById(R.id.app_login_name_et);
        psdET = (ClearPasswordEditText) findViewById(R.id.app_login_psd_et);
        commitBn = (Button) findViewById(R.id.app_login_bn);
        registTv = (TextView) findViewById(R.id.app_register_tv);
        forgetTv = (TextView) findViewById(R.id.app_forget_tv);

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        commitBn.setOnClickListener(this);
        registTv.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
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
            }if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag)){
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName())) {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        Global.savePassword(psdET.getText().toString());
                        Global.saveLoginName(nameET.getText().toString());
                        ToastUtil.makeText(mContext, "登录成功");
                        //发个通知，让其他页面知道已经退出了
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                        MainActivity.getUpLoadImageUrl();
                        finish();
                    } else {
                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_login_bn:
                if (GeneralUtils.isNotNullOrZeroLenght(psdET.getText().toString())) {
                    if (GeneralUtils.isNotNullOrZeroLenght(nameET.getText().toString())) {
                        NetLoadingDialog.getInstance().loading(mContext);
//                        if(EMClient.getInstance().isLoggedInBefore()){
//                            //enter to main activity directly if you logged in before.
//                            UserServiceImpl.instance().login(nameET.getText().toString().trim(), StringEncrypt.Encrypt(psdET.getText().toString().trim()),
//                                    LoginResponse.class.getName());
//                            AppUtil.initEMConnectListener(this);
//                        }else{
//                            EMClient.getInstance().login(Global.getLoginName(), Global.getLoginName(), new EMCallBack() {
//
//                                @Override
//                                public void onSuccess() {
                                    UserServiceImpl.instance().login(nameET.getText().toString().trim(), StringEncrypt.Encrypt(psdET.getText().toString().trim()),
                                            LoginResponse.class.getName());
//                                    EMClient.getInstance().chatManager().loadAllConversations();
//                                    EMClient.getInstance().groupManager().loadAllGroups();
//                                    AppUtil.initEMConnectListener(LoginActy.this);
//                                }
//
//                                @Override
//                                public void onProgress(int progress, String status) {
//
//                                }
//
//                                @Override
//                                public void onError(int code, String error) {
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            NetLoadingDialog.getInstance().dismissDialog();
//                                            Toast.makeText(getApplicationContext(), "登录失败,请您重新尝试~", Toast.LENGTH_SHORT).show();
//                                            Global.loginOut(mContext);
//                                            startActivity(new Intent(mContext,LoginActy.class));
//                                        }
//                                    });
//                                }
//                            });
//                        }

                    } else {
                        ToastUtil.makeText(mContext, "请输入用户名");
                    }
                } else {
                    ToastUtil.makeText(mContext, "请输入密码");
                }
                break;
            case R.id.app_forget_tv:
                startActivity(new Intent(mContext, FindPasswordCodeActy.class));
                break;
            case R.id.app_register_tv:
                startActivity(new Intent(mContext, RegistCodeActy.class));
                break;
        }
    }


}
