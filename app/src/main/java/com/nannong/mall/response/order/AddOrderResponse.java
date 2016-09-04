package com.nannong.mall.response.order;


import cn.nj.www.my_module.bean.BaseResponse;

public class AddOrderResponse extends BaseResponse
{


    /**
     * orderCode : 16081514712398284310085
     * totalPrice : 0.01
     * callbackUrl : http://221.226.118.110:18080/api/pay/weixinPayReturn
     * prepay_id : wx201608151347484d680935cc0723331982
     * partnerID : 1376700702
     * sign : 255C510E0B1F0BBD06881DAF68BC285C
     * nonce_str : 1DL48dL1wR01Aqqk
     * timestamp : 1471239829028
     * appID : wx8b028169a963d350
     */

    private String orderCode;
    private double totalPrice;
    private String callbackUrl;
    private String prepay_id;
    private String partnerID;
    private String sign;
    private String nonce_str;
    private String timestamp;
    private String appID;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
