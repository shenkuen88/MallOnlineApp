package com.nannong.mall.response.cart;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louisgeek on 2016/4/27.
 */
public class GoodsBean implements Parcelable  {

    public static final  int STATUS_INVALID=0;
    public static final  int STATUS_VALID=1;
    //===============================================

    private String createTime;
    private String userID;
    private String picUrl;
    private double price;
    private String style;
    private int count;
    private String shopID;
    private String objectName;
    private String shopName;
    private String recordID;
    private String contentID;
    private String size;
    private int state;

    @Override
    public String toString() {
        return "GoodsBean{" +
                "createTime='" + createTime + '\'' +
                ", contentID='" + contentID + '\'' +
                ", userID='" + userID + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", price=" + price +
                ", style='" + style + '\'' +
                ", count=" + count +
                ", shopID='" + shopID + '\'' +
                ", objectName='" + objectName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", recordID='" + recordID + '\'' +
                ", size='" + size + '\'' +
                ", state=" + state +
                ", isChecked=" + isChecked +
                ", isEditing=" + isEditing +
                '}';
    }

    /** 是否被选中 */
    private boolean isChecked;
    /** 是否是编辑状态 */
    private boolean isEditing;

    public GoodsBean() {
    }

    public GoodsBean(String createTime, String userID, String picUrl, double price, String style, int count, String shopID, String objectName, String shopName, String recordID, String contentID, String size, int state, boolean isChecked, boolean isEditing) {
        this.createTime = createTime;
        this.userID = userID;
        this.picUrl = picUrl;
        this.price = price;
        this.style = style;
        this.count = count;
        this.shopID = shopID;
        this.objectName = objectName;
        this.shopName = shopName;
        this.recordID = recordID;
        this.contentID = contentID;
        this.size = size;
        this.state = state;
        this.isChecked = isChecked;
        this.isEditing = isEditing;
    }

    protected GoodsBean(Parcel in) {
        createTime = in.readString();
        userID = in.readString();
        picUrl = in.readString();
        price = in.readDouble();
        style = in.readString();
        count = in.readInt();
        shopID = in.readString();
        objectName = in.readString();
        shopName = in.readString();
        recordID = in.readString();
        contentID = in.readString();
        size = in.readString();
        state = in.readInt();
        isChecked = in.readByte() != 0;
        isEditing = in.readByte() != 0;
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTime);
        dest.writeString(userID);
        dest.writeString(picUrl);
        dest.writeDouble(price);
        dest.writeString(style);
        dest.writeInt(count);
        dest.writeString(shopID);
        dest.writeString(objectName);
        dest.writeString(shopName);
        dest.writeString(recordID);
        dest.writeString(contentID);
        dest.writeString(size);
        dest.writeInt(state);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isEditing ? 1 : 0));
    }
}
