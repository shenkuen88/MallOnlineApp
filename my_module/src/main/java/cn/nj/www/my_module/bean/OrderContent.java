package cn.nj.www.my_module.bean;


import java.io.Serializable;

import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * Created by huqing on 2016/8/2.
 */
public class OrderContent implements Serializable {
    private String relationID;
    private String orderID;
    private String contentID;
    private String contentName;
    private String count;
    private String style;
    private String originalPrice;
    private String realPrice;

    public OrderContent(String contentID, String count, String style) {
        this.contentID = contentID;
        this.count = count;
        this.style = style;

//        this.relationID = "";
//        this.orderID = "";
//        this.contentName = "";
//        this.originalPrice = "";
//        this.realPrice = "";
    }


    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getContentID() {
        if (GeneralUtils.isNotNullOrZeroLenght(contentID)) {
            return contentID;
        } else {
            return "";
        }
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getCount() {
     if (GeneralUtils.isNotNullOrZeroLenght(count)){
         return count;
     }else {
         return "";
     }
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStyle() {
        if (GeneralUtils.isNotNullOrZeroLenght(style)) {
            return style;
        } else {
            return "";
        }
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }
}
