package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.EditPasswordResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
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
import cn.nj.www.my_module.view.ClearPasswordEditText;


/**
 * 设置密码
 */
public class EditPasswordActy extends BaseActivity implements View.OnClickListener
{


    private Button comfirmBn;

    private String phoneNum = "";

    private ClearPasswordEditText etPsdComfirm;

    private ClearPasswordEditText psdEt;

    /**
     * 旧密码
     */
    private ClearPasswordEditText etOld;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password_reset);
        phoneNum = getIntent().getStringExtra(IntentCode.REGISTER_PHONE);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("修改密码");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView()
    {
        initTitle();
        psdEt = (ClearPasswordEditText) findViewById(R.id.app_phone_et);
        etOld = (ClearPasswordEditText) findViewById(R.id.app_old_et);
        etPsdComfirm = (ClearPasswordEditText) findViewById(R.id.app_code_et);
        comfirmBn = (Button) findViewById(R.id.app_register_next_bn);

        comfirmBn.setOnClickListener(this);
        etPsdComfirm.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (GeneralUtils.isNotNullOrZeroLenght(etPsdComfirm.getText().toString()))
                {
                    comfirmBn.setEnabled(true);
                }
            }
        });

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

    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(EditPasswordResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    EditPasswordResponse mCheck = GsonHelper.toType(result, EditPasswordResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCheck.getResultCode()))
                    {
                        //密码修改成功
                        ToastUtil.makeText(mContext, "密码修改成功，请用新密码登录");
                        Global.loginOut(mContext);
                        startActivity(new Intent(mContext, LoginActy.class));
                        finish();

                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mCheck.getResultCode(), mCheck.getDesc());
                        NetLoadingDialog.getInstance().dismissDialog();
                    }
                }
                else
                {
                    NetLoadingDialog.getInstance().dismissDialog();
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
            case R.id.app_register_next_bn:
                if (GeneralUtils.isNullOrZeroLenght(etOld.getText().toString().trim()))
                {
                    ToastUtil.makeText(mContext, "请输入旧密码");
                    return;
                }
                else if (etPsdComfirm.getText().toString().trim().equals(psdEt.getText().toString().trim()))
                {
                    if (etPsdComfirm.getText().toString().trim().length() >= 6)
                    {
                        UserServiceImpl.instance().editPassword("1", phoneNum, etOld.getText().toString().trim(), StringEncrypt.Encrypt(etPsdComfirm.getText().toString().trim()), EditPasswordResponse.class.getName());
                    }
                    else
                    {
                        ToastUtil.makeText(mContext, "请输入大于六位数的密码");
                    }
                }
                else
                {
                    ToastUtil.makeText(mContext, "两次密码输入不一致");
                }

                break;

        }
    }
}
