package com.nannong.mall.activity.logistics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.logistics.LogisticsResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
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
import cn.nj.www.my_module.tools.V;

public class LogisticsActivity extends BaseActivity {
    private HeadView headView;
    private String orderID = "1";
    private ListView my_listview;
    private CommonAdapter<LogisticsResponse.DeliveryRecordListBean> mAdapter;
    private List<LogisticsResponse.DeliveryRecordListBean> llist = new ArrayList<LogisticsResponse.DeliveryRecordListBean>();
    private TextView wl_state,wl_ly,wl_id,wl_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        orderID=getIntent().getStringExtra("orderID");
        initAll();
    }

    //初始化标题
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("查看物流");
        headView.setRightImage(R.mipmap.btn_more);
        my_listview = V.f(this, R.id.my_listview);
        wl_state = V.f(this, R.id.wl_state);
        wl_ly = V.f(this, R.id.wl_ly);
        wl_id = V.f(this, R.id.wl_id);
        wl_phone = V.f(this, R.id.wl_phone);
    }

    @Override
    public void initView() {
        initTitle();
    }

    @Override
    public void initViewData() {
        mAdapter = new CommonAdapter<LogisticsResponse.DeliveryRecordListBean>(mContext, llist, R.layout.item_logistics) {
            @Override
            public void convert(ViewHolder helper, LogisticsResponse.DeliveryRecordListBean item) {
                TextView context = helper.getView(R.id.context);
                TextView time = helper.getView(R.id.time);
                if (helper.getPosition() == 0) {
                    context.setTextColor(getResources().getColor(R.color.app_color));
                    time.setTextColor(getResources().getColor(R.color.app_color));
                    helper.getView(R.id.img).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img2).setVisibility(View.GONE);
                }else{
                    context.setTextColor(getResources().getColor(R.color.txt_nol_col));
                    time.setTextColor(getResources().getColor(R.color.txt_nol_col));
                    helper.getView(R.id.img).setVisibility(View.GONE);
                    helper.getView(R.id.img2).setVisibility(View.VISIBLE);
                }
                context.setText(item.getContext());
                time.setText(item.getTime());
            }
        };
        my_listview.setAdapter(mAdapter);
        getData();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            Log.e("sub", "result==" + result);
            if (tag.equals(LogisticsResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    LogisticsResponse logisticsResponse = GsonHelper.toType(result, LogisticsResponse.class);
                    if (Constants.SUCESS_CODE.equals(logisticsResponse.getResultCode())) {
                        llist.clear();
                        if (logisticsResponse.getDeliveryRecordList() != null && logisticsResponse.getDeliveryRecordList().size() > 0) {
                            llist.addAll(logisticsResponse.getDeliveryRecordList());
                        }
                        mAdapter.setData(llist);
                        mAdapter.notifyDataSetChanged();
//                        switch (logisticsResponse.getDelivery().getStatus()){
//                            case 1:wl_state.setText("未发货");break;
//                            case 2:wl_state.setText("在途中");break;
//                            case 3:wl_state.setText("完成");break;
//                            default:wl_state.setText("暂无状态");break;
//                        }



                        wl_state.setText(logisticsResponse.getDelivery().getStateDesc());
                        wl_ly.setText(logisticsResponse.getDelivery().getDeliveryAddress());
                        wl_id.setText(logisticsResponse.getDelivery().getOrderID());
                        wl_phone.setText(logisticsResponse.getDelivery().getDeliveryNum());
                    } else {
                        ErrorCode.doCode(mContext, logisticsResponse.getResultCode(), logisticsResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void getData() {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getLogisticsInfo(mContext, orderID, LogisticsResponse.class.getName());
    }

}
