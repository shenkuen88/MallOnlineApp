package com.nannong.mall.activity.order.wx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nannong.mall.R;
import com.nannong.mall.response.order.AddOrderResponse;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.network.GsonHelper;


public class PayActivity extends Activity {

    private IWXAPI api;
    AddOrderResponse mComplainResponse ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        String result = getIntent().getStringExtra(IntentCode.ZFB_RESULT);
        mComplainResponse = GsonHelper.toType(result, AddOrderResponse.class);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if(isPaySupported) {
            if (null != mComplainResponse) {
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId = mComplainResponse.getAppID();//
                req.partnerId = mComplainResponse.getPartnerID();//
                req.prepayId = mComplainResponse.getPrepay_id(); //
                req.nonceStr = mComplainResponse.getNonce_str();//
                req.timeStamp = genTimeStamp();
                req.packageValue = "Sign=WXPay";
                req.sign = mComplainResponse.getSign();//
                req.extData = genTimeStamp(); // optional


//                Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                boolean fl= api.sendReq(req);
                Log.e("sub","f1="+fl);
//                        }else{
//                            Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
//                            Toast.makeText(PayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Log.d("PAY_GET", "服务器请求错误");
//                        Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                }catch(Exception e){
//                    Log.e("PAY_GET", "异常："+e.getMessage());
//                    Toast.makeText(PayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                payBtn.setEnabled(true);

            }
        }else{
            Toast.makeText(PayActivity.this, "您的手机不支持微信支付", Toast.LENGTH_SHORT).show();
        }
        Button appayBtn = (Button) findViewById(R.id.appay_btn);

        appayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
//                Button payBtn = (Button) findViewById(R.id.appay_btn);
//                payBtn.setEnabled(false);
//                Toast.makeText(PayActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
//                try{
//                    byte[] buf = Util.httpGet(mComplainResponse.getCallbackUrl());
//                    if (buf != null && buf.length > 0) {
//                        String content = new String(buf);
//                        Log.e("get server pay params:",content);
//                        JSONObject json = new JSONObject(content);
//                        CMLog.e(com.zhongchao.mall.constant.Constants.HTTP_TAG,json.toString());
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                if(isPaySupported) {
                    if (null != mComplainResponse) {
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId = mComplainResponse.getAppID();//
                        req.partnerId = mComplainResponse.getPartnerID();//
                        req.prepayId = mComplainResponse.getPrepay_id(); //
                        req.nonceStr = mComplainResponse.getNonce_str();//
                        req.timeStamp = genTimeStamp();
                        req.packageValue = "Sign=WXPay";
                        req.sign = mComplainResponse.getSign();//
                        req.extData = genTimeStamp(); // optional


                        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
//                        }else{
//                            Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
//                            Toast.makeText(PayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Log.d("PAY_GET", "服务器请求错误");
//                        Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                }catch(Exception e){
//                    Log.e("PAY_GET", "异常："+e.getMessage());
//                    Toast.makeText(PayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                payBtn.setEnabled(true);

                    }
                }else{
                    Toast.makeText(PayActivity.this, "您的手机不支持微信支付", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
        checkPayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String genTimeStamp() {
        return System.currentTimeMillis() / 1000+"";
    }
}
