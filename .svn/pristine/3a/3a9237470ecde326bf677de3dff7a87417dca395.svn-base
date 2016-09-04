package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.BillListResponse;

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

public class BillListActivity extends BaseActivity {
    private ListView my_listview;
    private VaryViewHelper mVaryViewHelper;
    private CommonAdapter<BillListResponse> mAdapter;
    private List<BillListResponse> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        initAll();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("账单");
    }
    @Override
    public void initView() {
        initTitle();
        my_listview= V.f(this,R.id.my_listview);
    }

    @Override
    public void initViewData() {
        mAdapter=new CommonAdapter<BillListResponse>(mContext,datas,R.layout.bill_list_item) {
            @Override
            public void convert(ViewHolder helper, BillListResponse item) {
                TextView info=helper.getView(R.id.info);
                TextView price=helper.getView(R.id.price);
                TextView time=helper.getView(R.id.time);
                info.setText(item.getInfo());
                if(item.getType().equals("1")) {
                    price.setText("+"+item.getPrice());
                }else{
                    price.setText("-"+item.getPrice());
                }
                time.setText(item.getTime());

                ImageView pic=helper.getView(R.id.pic);
                if(GeneralUtils.isNotNullOrZeroLenght(item.getPic())){
                    GeneralUtils.setImageViewWithUrl(mContext,item.getPic(),pic,R.drawable.default_head);
                }

            }
        };
        my_listview.setAdapter(mAdapter);
        getBillData();
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
                                getBillData();
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
    private void getBillData(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(int i=0;i<4;i++){
            String type="0";
            if(i==0||i==3){
                type="1";
            }
            BillListResponse c=new BillListResponse(""+i,"","100",type,"内容","今天\n09:30");
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
