package com.nannong.mall.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.FeedbackResponse;

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
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;


/**
 * 反馈
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 吐槽内容
     */
    private EditText etFeedBack;
    private EditText etTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_feedback);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        etFeedBack = (EditText) findViewById(R.id.person_feedback_et);
        etTel = (EditText) findViewById(R.id.person_feedback_tel_et);
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
                if (isOpen) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
                }
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                String feedBackStr = etFeedBack.getText().toString()+" "+etTel.getText().toString();
                if (feedBackStr.length() > 0) {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().feedback(feedBackStr,  FeedbackResponse.class.getName());
                }
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals( FeedbackResponse.class.getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                     FeedbackResponse mComplainResponse = GsonHelper.toType(result,  FeedbackResponse.class);
                    if (Constants.SUCESS_CODE.equals(mComplainResponse.getResultCode())) {
                        ToastUtil.makeText(mContext, "提交成功");
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mComplainResponse.getResultCode(), mComplainResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("意见反馈");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("提交");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

}
