package com.nannong.mall.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.activity.order.ConfirmOrderActivity;
import com.nannong.mall.fragment.index.ProductFragment;
import com.nannong.mall.response.index.AddToBuyCarResponse;
import com.nannong.mall.response.index.CollectContentResponse;
import com.nannong.mall.response.index.ContentDetailResponse;
import com.nannong.mall.response.index.ContentListBean;
import com.nannong.mall.response.order.GoodsBean;
import com.nannong.mall.response.order.StoreBean;
import com.nannong.mall.response.order.StoreGoodsBean;

import java.util.ArrayList;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.MyScrollView;
import cn.nj.www.my_module.view.MyWebView;
import de.greenrobot.event.EventBus;

public class NewProductActy extends BaseActivity implements View.OnClickListener
{

    private LinearLayout rlBottom;

    /**
     * 加入购物车
     */
    private Button addBuy_bn;

    /**
     * 购买
     */
    private Button buy_bn;

    /**
     * 收藏
     */
    private TextView collect_tv;

    /**
     * 客服
     */
    private TextView service_tv;

    private String contentID = "";

    private ContentDetailResponse mContentDetailResponse;

    private ContentListBean productContent;

    private int buyNum = 1;

    //    private String buyStyle="";
    private boolean collectFlag = false;

    private FrameLayout top_fragment;

    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_acty);

        contentID = getIntent().getStringExtra(IntentCode.contentID);
        shopId = getIntent().getStringExtra(IntentCode.shopId);
        CMLog.e(Constants.LOCAL_TAG, contentID + "  --");
        if (contentID == null)
        {
            ToastUtil.makeText(mContext, "无contentID");
            finish();
        }
        initAll();
    }

    @Override
    public void initView()
    {
        top_fragment = V.f(this, R.id.top_fragment);
        addBuy_bn = (Button) findViewById(R.id.addBuy_bn);
        collect_tv = (TextView) findViewById(R.id.collect_tv);
        service_tv = (TextView) findViewById(R.id.service_tv);
        buy_bn = (Button) findViewById(R.id.buy_bn);
    }

    @Override
    public void initViewData()
    {
        FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
        tra.add(R.id.top_fragment, ProductFragment.newInstance(contentID), ProductFragment.class.getName());
        tra.commit();
    }

    @Override
    public void initEvent()
    {
        addBuy_bn.setOnClickListener(this);
        collect_tv.setOnClickListener(this);
        service_tv.setOnClickListener(this);
        buy_bn.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.equalsTags(this, NotiTag.TAG_CLOSE_ACTIVITY, tag))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {

            }
            else if (NotiTag.TAG_CHANGE_BUY_NUM.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                buyNum = ((NoticeEvent) event).getPosition();
            }
            else if (NotiTag.TAG_CHANGE_STYLE.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
//                buyStyle = ((NoticeEvent) event).getText();

            }
            else if (NotiTag.TAG_ADD_CAR.equals(tag))
            {
                int num = ((NoticeEvent) event).getPosition();
                String style = ((NoticeEvent) event).getText();
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().addToBuyCar(contentID, num, style, "",
                        AddToBuyCarResponse.class.getName());
            }
            else if (NotiTag.equalsTags(mContext, NotiTag.TAG_BUY_NOW, tag))
            {
                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(getGoods()));
                intent.putExtra(IntentCode.ORDER_STATE, "0");//0 新生成订单，代付款订单 传订单号
                startActivity(intent);
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            //添加到购物车
            if (NotiTag.equalsTags(mContext, tag, AddToBuyCarResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    AddToBuyCarResponse mAddToBuyCarResponse = GsonHelper.toType(result, AddToBuyCarResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddToBuyCarResponse.getResultCode()))
                    {
                        ToastUtil.makeText(mContext, "添加成功");
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mAddToBuyCarResponse.getResultCode(), mAddToBuyCarResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
//            //收藏
            if (NotiTag.equalsTags(mContext, tag, CollectContentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CollectContentResponse mCollectContentResponse = GsonHelper.toType(result, CollectContentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCollectContentResponse.getResultCode()))
                    {
//                        Drawable drawable = getResources().getDrawable(R.mipmap.product_collected);
//                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
//                        collect_tv.setCompoundDrawables(null, drawable, null, null);//画在上方
                        collectFlag = true;
                        ToastUtil.makeText(mContext, getString(R.string.collect_success));
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mCollectContentResponse.getResultCode(), mCollectContentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(ContentDetailResponse.class.getName()))
            {
                mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode()))
                    {
                        productContent = mContentDetailResponse.getContent();
//                        buyStyle = mContentDetailResponse.getContentStyleList().get(0).getStyle();
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                }
                else
                {
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
            case R.id.addBuy_bn:
                // && GeneralUtils.isNotNullOrZeroLenght(buyStyle)
                if (GeneralUtils.isLogin())
                {
                    NetLoadingDialog.getInstance().loading(mContext);
//                    UserServiceImpl.instance().addToBuyCar(contentID, 1, buyStyle, "",
//                            AddToBuyCarResponse.class.getName());
                }
                else
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
            case R.id.buy_bn:
                if (GeneralUtils.isLogin())
                {
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_BUY_NOW));
                }
                else
                {
                    startActivity(new Intent(mContext, LoginActy.class));
                }
                break;
            case R.id.collect_tv:
                Intent intent = new Intent(mContext, OnlineShopActivity.class);
                intent.putExtra(IntentCode.shopId, shopId);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void shouCang()
    {
        if (collectFlag)
        {
            ToastUtil.makeText(mContext, "您已收藏过了");
            return;
        }
        else if (GeneralUtils.isLogin())
        {
            UserServiceImpl.instance().collectContent(1 + "", contentID,
                    CollectContentResponse.class.getName());
        }
        else
        {
            startActivity(new Intent(mContext, LoginActy.class));
        }
    }

    @Override
    protected void onDestroy()
    {
        MyScrollView.page = 0;
        MyWebView.pageView = 0;
        super.onDestroy();
    }

    public ArrayList<StoreGoodsBean> getGoods()
    {
        ArrayList<StoreGoodsBean> slist = new ArrayList<StoreGoodsBean>();

        StoreGoodsBean storeGoodsBean = new StoreGoodsBean();
        storeGoodsBean.setStoreBean(null);
        //商店实体
        StoreBean storeBean = new StoreBean(mContentDetailResponse.getContent().getShopID(),
                mContentDetailResponse.getContent().getShopName(), false, false);
//        StoreBean storeBean = new StoreBean("", "中巢", false, false);
        storeGoodsBean.setStoreBean(storeBean);

        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setContentID(mContentDetailResponse.getContent().getContentID());
        goodsBean.setPicUrl(mContentDetailResponse.getContent().getPicUrl1());
        goodsBean.setObjectName(mContentDetailResponse.getContent().getContentName());
        goodsBean.setCount(buyNum);
//        goodsBean.setStyle(buyStyle);
        if (GeneralUtils.isNotNullOrZeroLenght(mContentDetailResponse.getContent().getPrice()))
        {
            goodsBean.setPrice(Double.parseDouble(mContentDetailResponse.getContent().getPrice()));
        }
        else
        {
            goodsBean.setPrice(0);
        }

        storeGoodsBean.getGoodsBeanList().add(goodsBean);
        if (storeGoodsBean.getStoreBean() != null)
        {
            slist.add(storeGoodsBean);
        }
        return slist;
    }
}
