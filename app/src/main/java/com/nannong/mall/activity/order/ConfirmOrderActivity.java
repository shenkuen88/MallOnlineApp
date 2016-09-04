package com.nannong.mall.activity.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.reflect.TypeToken;
import com.nannong.mall.R;
import com.nannong.mall.activity.mine.RecieveAddressListActy;
import com.nannong.mall.activity.order.zfb.PayResult;
import com.nannong.mall.activity.order.zfb.ZFBPayActivity;
import com.nannong.mall.response.mine.AddressBean;
import com.nannong.mall.response.order.AddOrderResponse;
import com.nannong.mall.response.order.GoodsBean;
import com.nannong.mall.response.order.StoreBean;
import com.nannong.mall.response.order.StoreGoodsBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.OrderContent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.MyListView;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvNoReceiver;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout llReceiver;
    private CheckBox cbWX, cbZFB;
    /**
     * 发票
     */
    private RelativeLayout rlBill;
    private TextView tvBill;
    /**
     * 订单列表
     * 对应的item是 item_confirm_order
     * 同一个商店中的商品对应的布局 item_confirm_order_same_shop
     */
    private MyListView lvOrder;
    /**
     * 收货地址
     */
    private RelativeLayout rlReceiver;
    private ArrayList<GoodsBean> goodslist = new ArrayList<GoodsBean>();
    private ArrayList<StoreGoodsBean> shopList = new ArrayList<StoreGoodsBean>();
    private AddressBean addressBean;
    private CommonAdapter<StoreGoodsBean> adapter;
    private TextView tvShouldPay;
    /**
     * 提交订单
     */
    private Button bnSubmit;
    /**
     * 构造的订单
     */
    private ArrayList<OrderContent> orderList = new ArrayList<>();
    private int payType;//支付方式
    //  应付金额
    private double totalPay = 0;
    private OrderContent orderContent;
    private AddressBean bean;
    private String orderState = 0 + "";
    private AddOrderResponse mComplainResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        orderState = getIntent().getStringExtra(IntentCode.ORDER_STATE);
        Type type = new TypeToken<ArrayList<StoreGoodsBean>>() {
        }.getType();
        shopList = GsonHelper.fromJson(getIntent().getStringExtra(IntentCode.ORDER_GOODS_LIST), type);
        initAll();
        initAdapter();
    }


    private void initAdapter() {
        adapter = new CommonAdapter<StoreGoodsBean>(mContext, shopList, R.layout.item_confirm_order) {
            @Override
            public void convert(ViewHolder helper, final StoreGoodsBean item) {
                StoreBean shopBean = item.getStoreBean();
                helper.setText(R.id.title_tv, shopBean.getName());//店名
                List<GoodsBean> goodList = item.getGoodsBeanList();
                MyListView lvGood = helper.getView(R.id.good_lv);
                helper.setText(R.id.yf_tv, "0");//运费
                //订单金额
                TextView tvOrderPay = helper.getView(R.id.order_pay_tv);
                //应付金额
                TextView tvPay = helper.getView(R.id.total_tv);
                CommonAdapter<GoodsBean> gAdapter
                        = new CommonAdapter<GoodsBean>(mContext, goodList, R.layout.item_confirm_order_same_shop) {
                    @Override
                    public void convert(ViewHolder helper, GoodsBean item) {
                        CMLog.e(Constants.HTTP_TAG, item.toString());
                        orderContent = new OrderContent(item.getContentID(), item.getCount() + "", item.getStyle());
                        if (orderList.size() > 0) {
                            for (int i = 0; i < orderList.size(); i++) {
                                OrderContent bean = orderList.get(i);
                                if (
                                    //orderList中无该orderContent
                                        !(orderContent.getContentID().equals(bean.getContentID())
                                                && orderContent.getStyle().equals(bean.getStyle())
                                                && orderContent.getCount().equals(orderContent.getCount()))) {
                                    orderList.add(orderContent);
                                    return;
                                }
                            }
                        } else {
                            orderList.add(orderContent);
                        }
                        //图标
                        ImageView iv = helper.getView(R.id.good_iv);
                        GeneralUtils.setImageViewWithUrl(mContext, item.getPicUrl(), iv,  R.drawable.default_bg);
                        //数量
                        helper.setText(R.id.num_tv, "×" + item.getCount());
                        //价格
                        helper.setText(R.id.price_tv, "¥" + item.getPrice());
                        //名称
                        helper.setText(R.id.name, item.getObjectName());
                        //样式
                        helper.setText(R.id.classify_tv, item.getStyle());
                        totalPay = item.getCount() * item.getPrice();
                    }
                };
                lvGood.setAdapter(gAdapter);
                tvOrderPay.setText("¥" + totalPay);
                tvPay.setText("¥" + totalPay);
                tvShouldPay.setText(totalPay + "元");
            }
        };
        lvOrder.setAdapter(adapter);

    }

    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.zfb_rl).setOnClickListener(this);
        findViewById(R.id.wx_rl).setOnClickListener(this);
        rlReceiver = (RelativeLayout) findViewById(R.id.receiver_rl);
        rlReceiver.setOnClickListener(this);
        tvNoReceiver = (TextView) findViewById(R.id.no_receiver_tv);
        tvShouldPay = (TextView) findViewById(R.id.should_pay_tv);
        bnSubmit = (Button) findViewById(R.id.submit_order_bn);
        bnSubmit.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.name_tv);
        tvPhone = (TextView) findViewById(R.id.phone_tv);
        tvBill = (TextView) findViewById(R.id.bill_info_tv);
        tvAddress = (TextView) findViewById(R.id.address_tv);
        llReceiver = (LinearLayout) findViewById(R.id.receiver_ll);
        lvOrder = (MyListView) findViewById(R.id.order_list);
        rlBill = (RelativeLayout) findViewById(R.id.bill_rl);
        cbZFB = (CheckBox) findViewById(R.id.zhb_cb);
        cbWX = (CheckBox) findViewById(R.id.wx_cb);
        tvNoReceiver.setVisibility(View.VISIBLE);
        llReceiver.setVisibility(View.GONE);
        receiveAddressShow();
        cbWX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWX.setChecked(true);
                    cbZFB.setChecked(false);
                } else {
                    cbWX.setChecked(false);
                    cbZFB.setChecked(true);
                }
            }
        });
        cbZFB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbWX.setChecked(true);
                    cbZFB.setChecked(false);
                } else {
                    cbWX.setChecked(false);
                    cbZFB.setChecked(true);
                }
            }
        });

    }


    private void receiveAddressShow() {
        String addStr = SharePref.getString(Constants.CHOOSE_ADDRESS, "");
        if (GeneralUtils.isNotNullOrZeroLenght(addStr)) {
            bean = GsonHelper.toType(addStr, AddressBean.class);
            tvNoReceiver.setVisibility(View.GONE);
            llReceiver.setVisibility(View.VISIBLE);
            tvName.setText("收货人：" + bean.getDeliveryUser());
            tvPhone.setText("手机号码：" + bean.getPhone());
            tvAddress.setText("收货地址：" + bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail());
        } else {
            tvNoReceiver.setVisibility(View.VISIBLE);
            llReceiver.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        rlBill.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_SET_BILL.equals(tag)) {
                if (SharePref.getString(Constants.TAG_SET_BILL, "").equals("1")) {
                    tvBill.setText("需要发票");
                } else {
                    tvBill.setText("不需要发票");
                }
            } else if (NotiTag.TAG_CHOOSE_ADDRESS_OK.equals(tag)) {
                receiveAddressShow();
            } else if (NotiTag.TAG_PAY_SUCCESS.equals(tag)) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(AddOrderResponse.class.getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    mComplainResponse = GsonHelper.toType(result, AddOrderResponse.class);
                    if (Constants.SUCESS_CODE.equals(mComplainResponse.getResultCode())) {
                        if (payType == 2) {
//                            Intent zfbIntent = new Intent(mContext, ZFBPayActivity.class);
//                            zfbIntent.putExtra(IntentCode.ZFB_RESULT, result);
//                            startActivity(zfbIntent);
                            String orderInfo = getOrderInfo("中巢的商品", "中巢旗下的商品", mComplainResponse.getTotalPrice()+"");

                            /**
                             * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
                             */
                            String sign = ZFBPayActivity.sign(orderInfo);
                            try {
                                /**
                                 * 仅需对sign 做URL编码
                                 */
                                sign = URLEncoder.encode(sign, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            /**
                             * 完整的符合支付宝参数规范的订单信息
                             */
                            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + ZFBPayActivity.getSignType();

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // 构造PayTask 对象
                                    PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                                    // 调用支付接口，获取支付结果
                                    String result = alipay.pay(payInfo, true);

                                    Message msg = new Message();
                                    msg.what = ZFBPayActivity.SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };

                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else {
//                            Intent zfbIntent = new Intent(mContext, PayActivity.class);
//                            zfbIntent.putExtra(IntentCode.ZFB_RESULT, result);
//                            startActivity(zfbIntent);
                            IWXAPI api = WXAPIFactory.createWXAPI(this, com.nannong.mall.activity.order.wx.Constants.APP_ID);
                            AddOrderResponse mc = GsonHelper.toType(result, AddOrderResponse.class);
                            if(null != mc){

                                PayReq req = new PayReq();
                                req.appId			= mc.getAppID();//
                                req.partnerId		= mc.getPartnerID();
                                req.prepayId		= mc.getPrepay_id();
                                req.nonceStr		= mc.getNonce_str();
                                req.timeStamp		= mc.getTimestamp();
                                req.packageValue	= "Sign=WXpay";
//                                req.sign			= mc.getSign();
                                req.extData			= mc.getTimestamp(); // optional
                                List<NameValuePair> signParams = new LinkedList<NameValuePair>();
                                signParams.add(new BasicNameValuePair("appid", req.appId));
                                signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
                                signParams.add(new BasicNameValuePair("package", req.packageValue));
                                signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
                                signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
                                signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
                                req.sign = genAppSign(signParams);
                                Log.e("sub",mc.getSign()+"==="+req.sign);
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(com.nannong.mall.activity.order.wx.Constants.APP_ID);
                                boolean bl=api.sendReq(req);
                                Log.e("sub","bl"+bl);
                            }
                        }
                    } else {
                        ErrorCode.doCode(mContext, mComplainResponse.getResultCode(), mComplainResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    // 随机串 防重发
    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("ningbozhongchaowenhuachuanmei123");

        String appSign = getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
//        Log.e("orion", appSign);
        return appSign;
    }
    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("确认订单");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    private String genTimeStamp() {
        return System.currentTimeMillis() / 1000+"";
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //确认订单
            case R.id.submit_order_bn:
                if (GeneralUtils.isNullOrZeroLenght(tvAddress.getText().toString())) {
                    ToastUtil.makeText(mContext, "请确认收货信息");
                } else {
                    if (cbZFB.isChecked()) {
                        payType = 2;
                    } else {
                        payType = 3;
                    }
                    NetLoadingDialog.getInstance().loading(mContext);

                    //新添加订单
                    if (orderState.equals("0")) {
                        UserServiceImpl.instance().addOrder(orderList, payType, bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail(),
                                bean.getDeliveryUser(), bean.getPhone(),
                                AddOrderResponse.class.getName());
                    } else {
                        UserServiceImpl.instance().addOrder(orderState, payType, bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetail(),
                                bean.getDeliveryUser(), bean.getPhone(),
                                AddOrderResponse.class.getName());
                    }
                }
                break;
            case R.id.bill_rl:
                startActivity(new Intent(mContext, BillActivity.class));
                break;
            case R.id.wx_rl:
                cbWX.setChecked(true);
                cbZFB.setChecked(false);
                break;
            case R.id.zfb_rl:
                cbWX.setChecked(false);
                cbZFB.setChecked(true);
                break;
            case R.id.receiver_rl:
                Intent intent = new Intent(mContext, RecieveAddressListActy.class);
                if (addressBean != null && GeneralUtils.isNotNullOrZeroLenght(addressBean.getPhone())) {
                    intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, addressBean.getPhone());
                    intent.putExtra(IntentCode.EDIT_ADDRESS_BEAN_DETAIL, addressBean.getDetail());
                } else {
                    intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, "1");
                }
                startActivity(intent);
                break;
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ZFBPayActivity.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(mContext, PaySucActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + ZFBPayActivity.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + ZFBPayActivity.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + mComplainResponse.getOrderCode().replace("#","") + "\"";
//        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" +mComplainResponse .getCallbackUrl() + "\"";
//        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
