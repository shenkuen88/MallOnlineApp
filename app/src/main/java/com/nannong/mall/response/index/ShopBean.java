package com.nannong.mall.response.index;

import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * Created by huqing on 2016/9/1.
 */
public class ShopBean
{


    private String shopID;

    private int type;

    private String shopCode;

    private String shopName;

    private String picUrl;

    private String description;

    private String address;

    private String phone;

    private String top;

    private String score;

    private String viewCount;

    private int delFlag;

    private int status;

    private String createTime;

    public String getShopID()
    {
        return shopID;
    }

    public void setShopID(String shopID)
    {
        this.shopID = shopID;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getShopCode()
    {
        return shopCode;
    }

    public void setShopCode(String shopCode)
    {
        this.shopCode = shopCode;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getPicUrl()
    {
        if (GeneralUtils.isNotNullOrZeroLenght(picUrl)){
            return picUrl;
        }else {
            return "";
        }
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getTop()
    {
        return top;
    }

    public void setTop(String top)
    {
        this.top = top;
    }

    public String getScore()
    {
        return score;
    }

    public void setScore(String score)
    {
        this.score = score;
    }

    public String getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(String viewCount)
    {
        this.viewCount = viewCount;
    }

    public int getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(int delFlag)
    {
        this.delFlag = delFlag;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString()
    {
        return "ShopBean{" +
                "shopID='" + shopID + '\'' +
                ", type=" + type +
                ", shopCode='" + shopCode + '\'' +
                ", shopName='" + shopName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", top='" + top + '\'' +
                ", score='" + score + '\'' +
                ", viewCount='" + viewCount + '\'' +
                ", delFlag=" + delFlag +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
