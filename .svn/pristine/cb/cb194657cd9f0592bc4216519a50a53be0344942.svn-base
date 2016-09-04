package com.nannong.mall.response.cart;

import android.os.Parcel;
import android.os.Parcelable;

import cn.nj.www.my_module.tools.GeneralUtils;


/**
 * Created by louisgeek on 2016/4/27.
 */
public class StoreBean implements Parcelable{
    /** 店铺ID */
    private String id;
    /** 店铺名称 */
    private String name="";

    private boolean isChecked;

    private boolean isEditing;

    public StoreBean(String id, String name,boolean isChecked,boolean isEditing) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
        this.isEditing = isEditing;
    }

    public StoreBean() {
    }

    protected StoreBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        isChecked = in.readByte() != 0;
        isEditing = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isEditing ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoreBean> CREATOR = new Creator<StoreBean>() {
        @Override
        public StoreBean createFromParcel(Parcel in) {
            return new StoreBean(in);
        }

        @Override
        public StoreBean[] newArray(int size) {
            return new StoreBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public String getName() {
        if (GeneralUtils.isNotNullOrZeroLenght(name)){
            return name;
        }else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
