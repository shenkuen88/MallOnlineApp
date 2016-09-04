package com.nannong.mall.response.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwei on 2016/8/2 0002.
 */
public class StoreGoodsBean implements Parcelable{
    private StoreBean storeBean;

    private List<GoodsBean> goodsBeanList=new ArrayList<>();

    public StoreGoodsBean() {
    }

    public StoreGoodsBean(StoreBean storeBean, List<GoodsBean> goodsBeanList) {
        this.storeBean = storeBean;
        this.goodsBeanList = goodsBeanList;
    }

    protected StoreGoodsBean(Parcel in) {
        goodsBeanList = in.createTypedArrayList(GoodsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(goodsBeanList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoreGoodsBean> CREATOR = new Creator<StoreGoodsBean>() {
        @Override
        public StoreGoodsBean createFromParcel(Parcel in) {
            return new StoreGoodsBean(in);
        }

        @Override
        public StoreGoodsBean[] newArray(int size) {
            return new StoreGoodsBean[size];
        }
    };

    public StoreBean getStoreBean() {
        return storeBean;
    }

    public void setStoreBean(StoreBean storeBean) {
        this.storeBean = storeBean;
    }

    public List<GoodsBean> getGoodsBeanList() {
        return goodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList) {
        this.goodsBeanList = goodsBeanList;
    }
}
