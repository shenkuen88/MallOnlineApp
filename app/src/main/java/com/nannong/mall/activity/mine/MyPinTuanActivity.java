package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.PinTuanResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;

public class MyPinTuanActivity extends BaseActivity {
    private ListView my_listview;
    private VaryViewHelper mVaryViewHelper;
    private CommonAdapter<PinTuanResponse> mAdapter;
    private List<PinTuanResponse> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pin_tuan);
        initAll();
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("我参与的拼团");
    }
    @Override
    public void initView() {
        initTitle();
        my_listview= V.f(this,R.id.my_listview);
    }

    @Override
    public void initViewData() {
        mAdapter=new CommonAdapter<PinTuanResponse>(mContext,datas,R.layout.pin_tuan_item) {
            @Override
            public void convert(ViewHolder helper,final PinTuanResponse item) {
                LinearLayout btn_sqth = helper.getView(R.id.btn_sqth);//申请退货
                LinearLayout btn_ckwl = helper.getView(R.id.btn_ckwl);//查看物流
                LinearLayout btn_msfk = helper.getView(R.id.btn_msfk);//马上付款
                LinearLayout btn_txfh = helper.getView(R.id.btn_txfh);//提醒发货
                LinearLayout btn_qrsh = helper.getView(R.id.btn_qrsh);//确认收货
                LinearLayout btn_pj = helper.getView(R.id.btn_pj);//评价
                LinearLayout btn_zxs = helper.getView(R.id.btn_zxs);//找相似
                LinearLayout btn_qkqx = helper.getView(R.id.btn_qkqx);//钱款去向
                LinearLayout btn_qxdd= helper.getView(R.id.btn_qxdd);//取消订单

                helper.setText(R.id.store_nam, item.getShopName());
//                helper.setText(R.id.all_num, "共" + item.get() + "件商品");
                helper.getView(R.id.all_num).setVisibility(View.GONE);
                helper.setText(R.id.all_money, "￥" + item.getRealPrice());
//                helper.setText(R.id.dy_price, "(含运费￥" + item.getd() + ")");
                switch (item.getStatus() + "") {
                    case "1":
                        btn_qxdd.setVisibility(View.VISIBLE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.VISIBLE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "待付款");
                        break;
                    case "2":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.GONE);
//                        btn_txfh.setVisibility(View.VISIBLE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "待发货");
                        break;
                    case "3":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.VISIBLE);
                        btn_ckwl.setVisibility(View.VISIBLE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.VISIBLE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "待收货");
                        break;
                    case "4":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.VISIBLE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.VISIBLE);
                        btn_zxs.setVisibility(View.GONE);
                        btn_qkqx.setVisibility(View.GONE);
                        helper.setText(R.id.state, "交易成功");
                        break;
                    case "5":
                        btn_qxdd.setVisibility(View.GONE);
                        btn_sqth.setVisibility(View.GONE);
                        btn_ckwl.setVisibility(View.GONE);
                        btn_msfk.setVisibility(View.GONE);
                        btn_txfh.setVisibility(View.GONE);
                        btn_qrsh.setVisibility(View.GONE);
                        btn_pj.setVisibility(View.GONE);
                        btn_zxs.setVisibility(View.VISIBLE);
                        btn_qkqx.setVisibility(View.VISIBLE);
                        helper.setText(R.id.state, "退款成功");
                        break;
                }
                CommonAdapter<PinTuanResponse.OrderContentListBean> goodsCommonAdapter
                        = new CommonAdapter<PinTuanResponse.OrderContentListBean>(mContext
                        , item.getOrderContentList(), R.layout.item_myorder_goods) {
                    @Override
                    public void convert(ViewHolder helper, PinTuanResponse.OrderContentListBean mItem) {
                        if (GeneralUtils.isNotNullOrZeroLenght(mItem.getPicUrl())) {
                            ImageView img = helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(orderListActivity, mItem.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mContext, mItem.getPicUrl(), img, R.drawable.default_bg);
                        }
                    }
                };
                ListView myitemlist = helper.getView(R.id.myitemlist);
                myitemlist.setAdapter(goodsCommonAdapter);
                GeneralUtils.setListViewHeightBasedOnChildrenExtend(myitemlist);
            }
        };
        my_listview.setAdapter(mAdapter);
        getPTData();
        if (datas.size() == 0)
        {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                    .setEmptyView(R.mipmap.youhuijuan, "您还没有消费哦~")//空页面，图+文字
                    .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (GeneralUtils.isNetworkConnected(mContext))
                            {
                                mVaryViewHelper.showLoadingView();
                                getPTData();
                            }
                            else
                            {
                                ToastUtil.showError(mContext);
                            }
                        }
                    })//错误页点击刷新实现
                    .build(mContext);
            mVaryViewHelper.showLoadingView();
        }
    }

    @Override
    public void initEvent() {

    }
    private void getPTData(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(int i=0;i<5;i++){
            PinTuanResponse c=new PinTuanResponse();
            datas.add(c);
        }
        mAdapter.setData(datas);
        mAdapter.notifyDataSetChanged();
        if(datas.size()==0) {
            mVaryViewHelper.showEmptyView();
        }
    }
    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag)){
                finish();
            }
        }
    }
}
