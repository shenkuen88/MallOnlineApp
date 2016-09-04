package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.CoupinsResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * 优惠券
 * jwei
 */
public class CouponsActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout btn_wsy,btn_ysy,btn_ygq;
    private TextView wsy_txt,ysy_txt,ygq_txt;
    private View wsy_line,ysy_line,ygq_line;
    private ListView my_listview;
    private VaryViewHelper mVaryViewHelper;
    private CommonAdapter<CoupinsResponse> mAdapter;
    private List<CoupinsResponse> datas=new ArrayList<>();
    private int type=0;//0.未使用 1.已使用 2.已过期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        initAll();
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("优惠券");
    }
    @Override
    public void initView() {
        initTitle();
        btn_wsy= V.f(this,R.id.btn_wsy);
        btn_ysy= V.f(this,R.id.btn_ysy);
        btn_ygq= V.f(this,R.id.btn_ygq);
        wsy_txt= V.f(this,R.id.wsy_txt);
        ysy_txt= V.f(this,R.id.ysy_txt);
        ygq_txt= V.f(this,R.id.ygq_txt);
        wsy_line= V.f(this,R.id.wsy_line);
        ysy_line= V.f(this,R.id.ysy_line);
        ygq_line= V.f(this,R.id.ygq_line);
        my_listview= V.f(this,R.id.my_listview);

    }

    @Override
    public void initViewData() {
        mAdapter=new CommonAdapter<CoupinsResponse>(mContext,datas,R.layout.coupons_item) {
            @Override
            public void convert(ViewHolder helper, CoupinsResponse item) {
                TextView name=helper.getView(R.id.name);
                TextView info=helper.getView(R.id.info);
                TextView price=helper.getView(R.id.price);
                TextView time=helper.getView(R.id.time);
                TextView dw=helper.getView(R.id.dw);
                name.setText(item.getName());
                info.setText(item.getInfo());
                price.setText(item.getPrice()+"");
                time.setText("有效期至:"+item.getTime());
                View my_top_view=helper.getView(R.id.my_top_view);
                ImageView sx_pic=helper.getView(R.id.sx_pic);
                switch (type){
                    case 0:
                        my_top_view.setBackgroundResource(R.mipmap.bj_coupon);
                        sx_pic.setVisibility(View.GONE);
                        name.setTextColor(getResources().getColor(R.color.app_color));
                        price.setTextColor(getResources().getColor(R.color.app_color));
                        dw.setTextColor(getResources().getColor(R.color.app_color));
                        break;
                    case 1:
                        my_top_view.setBackgroundResource(R.mipmap.bj_coupon_c);
                        sx_pic.setVisibility(View.VISIBLE);
                        name.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        price.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        dw.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        break;
                    case 2:
                        my_top_view.setBackgroundResource(R.mipmap.bj_coupon_c);
                        sx_pic.setVisibility(View.GONE);
                        name.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        price.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        dw.setTextColor(getResources().getColor(R.color.txt_nol_col));
                        break;
                }

            }
        };
        my_listview.setAdapter(mAdapter);
        getCouponsData();
        if (datas.size() == 0)
        {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                    .setEmptyView(R.mipmap.youhuijuan, "还没有优惠券哦~")//空页面，图+文字
                    .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (GeneralUtils.isNetworkConnected(mContext))
                            {
                                mVaryViewHelper.showLoadingView();
                                getCouponsData();
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
        btn_wsy.setOnClickListener(this);
        btn_ysy.setOnClickListener(this);
        btn_ygq.setOnClickListener(this);
    }
    private void getCouponsData(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(int i=0;i<4;i++){
            CoupinsResponse c=new CoupinsResponse(""+i,"名字"+i,"内容"+i,"100",sdf.format(new Date()));
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
    private void initAllview(){
        wsy_txt.setTextColor(getResources().getColor(R.color.txt_col));
        ysy_txt.setTextColor(getResources().getColor(R.color.txt_col));
        ygq_txt.setTextColor(getResources().getColor(R.color.txt_col));
        wsy_line.setVisibility(View.GONE);
        ysy_line.setVisibility(View.GONE);
        ygq_line.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_wsy:
                type=0;
                initAllview();
                wsy_txt.setTextColor(getResources().getColor(R.color.app_color));
                wsy_line.setVisibility(View.VISIBLE);
                getCouponsData();
                break;
            case R.id.btn_ysy:
                type=1;
                initAllview();
                ysy_txt.setTextColor(getResources().getColor(R.color.app_color));
                ysy_line.setVisibility(View.VISIBLE);
                getCouponsData();
                break;
            case R.id.btn_ygq:
                type=2;
                initAllview();
                ygq_txt.setTextColor(getResources().getColor(R.color.app_color));
                ygq_line.setVisibility(View.VISIBLE);
                getCouponsData();
                break;
        }
    }
}
