package com.nannong.mall.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.logistics.LogisticsActivity;
import com.nannong.mall.response.mine.AddressBean;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.SharePref;
import de.greenrobot.event.EventBus;

public class PaySucActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_suc);
        initAll();
        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_PAY_SUCCESS));
    }

    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.apply_refund_tv).setOnClickListener(this);
        findViewById(R.id.transport_tv).setOnClickListener(this);
        //每次支付前选中的地址都会保存
        String addStr = SharePref.getString(Constants.CHOOSE_ADDRESS, "");
        AddressBean bean = GsonHelper.toType(addStr, AddressBean.class);
        TextView tvName = (TextView) findViewById(R.id.receiver_tv);
        TextView tvAddress = (TextView) findViewById(R.id.address_tv);
        TextView tvTel = (TextView) findViewById(R.id.tel_tv);
        tvName.setText(bean.getDeliveryUser());
        tvAddress.setText(bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail());
        tvTel.setText(bean.getPhone());

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("支付成功");
        headView.setRightImage(R.mipmap.fxpic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_refund_tv:
                Intent intent = new Intent(mContext, RefundActy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "1");
                startActivity(intent);
                break;
            case R.id.transport_tv:
                startActivity(new Intent(mContext, LogisticsActivity.class));
                break;
        }
    }
}
