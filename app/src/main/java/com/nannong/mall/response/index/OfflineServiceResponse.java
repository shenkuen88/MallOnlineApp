package com.nannong.mall.response.index;

import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

/**
 * Created by huqing on 2016/9/5.
 */
public class OfflineServiceResponse extends BaseResponse
{

    /**
     * shopList : [{"shopID":"6","type":2,"shopCode":"16004","shopName":"线下商家1","picUrl":"http://obhtkhsaa.bkt.clouddn.com/shop_e2ec669d-cfa9-46e7-ae98-affdbb3471c3.png","description":"线下商家11222描述","address":"南京江宁区秦淮路1号欧尚超市","phone":"180520111111","top":"烧鸡,烧肉,红烧鱼","score":0,"viewCount":0,"delFlag":0,"status":1,"createTime":"2016-08-31"}]
     * totalCount : 1
     */

    private int totalCount;

    /**
     * shopID : 6
     * type : 2
     * shopCode : 16004
     * shopName : 线下商家1
     * picUrl : http://obhtkhsaa.bkt.clouddn.com/shop_e2ec669d-cfa9-46e7-ae98-affdbb3471c3.png
     * description : 线下商家11222描述
     * address : 南京江宁区秦淮路1号欧尚超市
     * phone : 180520111111
     * top : 烧鸡,烧肉,红烧鱼
     * score : 0.0
     * viewCount : 0
     * delFlag : 0
     * status : 1
     * createTime : 2016-08-31
     */

    private List<ShopListBean> shopList;

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public List<ShopListBean> getShopList()
    {
        return shopList;
    }

    public void setShopList(List<ShopListBean> shopList)
    {
        this.shopList = shopList;
    }

    public static class ShopListBean
    {
        private String shopID;

        private int type;

        /**
         * 类别
         */
        private String category;

        private String distance;

        private int aveage;

        private String shopCode;

        private String shopName;

        private String picUrl;

        private String description;

        private String address;

        private String phone;

        private String top;

        private double score;

        private int viewCount;

        private int delFlag;

        private int status;

        private String createTime;

        public String getCategory()
        {
            return category;
        }

        public void setCategory(String category)
        {
            this.category = category;
        }

        public String getDistance()
        {
            return distance;
        }

        public void setDistance(String distance)
        {
            this.distance = distance;
        }

        public int getAveage()
        {
            return aveage;
        }

        public void setAveage(int aveage)
        {
            this.aveage = aveage;
        }

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
            return picUrl;
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

        public double getScore()
        {
            return score;
        }

        public void setScore(double score)
        {
            this.score = score;
        }

        public int getViewCount()
        {
            return viewCount;
        }

        public void setViewCount(int viewCount)
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
    }
}
