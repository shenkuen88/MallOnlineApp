package com.nannong.mall.response.logistics;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class LogisticsResponse extends BaseResponse {

    /**
     * createTime : null
     * phone : 13312345678
     * deliveryUser : 收货人
     * orderID : null
     * status : 2
     * recordID : null
     * expressName :
     * expressCode : 1234
     * deliveryNum : null
     * deliveryAddress : 南京测试地址
     */

    private DeliveryBean delivery;
    /**
     * context : 快递已发出
     * time : 2016-7-21 18:00:00
     */

    private List<DeliveryRecordListBean> deliveryRecordList;

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public List<DeliveryRecordListBean> getDeliveryRecordList() {
        return deliveryRecordList;
    }

    public void setDeliveryRecordList(List<DeliveryRecordListBean> deliveryRecordList) {
        this.deliveryRecordList = deliveryRecordList;
    }

    public static class DeliveryBean {
        private String createTime;
        private String phone;
        private String deliveryUser;
        private String orderID;
        private int status;
        private String recordID;
        private String expressName;
        private String expressCode;
        private String deliveryNum;
        private String deliveryAddress;
        private String stateDesc;

        public String getStateDesc() {
            return stateDesc;
        }

        public void setStateDesc(String stateDesc) {
            this.stateDesc = stateDesc;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDeliveryUser() {
            return deliveryUser;
        }

        public void setDeliveryUser(String deliveryUser) {
            this.deliveryUser = deliveryUser;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRecordID() {
            return recordID;
        }

        public void setRecordID(String recordID) {
            this.recordID = recordID;
        }

        public String getExpressName() {
            return expressName;
        }

        public void setExpressName(String expressName) {
            this.expressName = expressName;
        }

        public String getExpressCode() {
            return expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getDeliveryNum() {
            return deliveryNum;
        }

        public void setDeliveryNum(String deliveryNum) {
            this.deliveryNum = deliveryNum;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }
    }

    public static class DeliveryRecordListBean {
        private String context;
        private String time;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
