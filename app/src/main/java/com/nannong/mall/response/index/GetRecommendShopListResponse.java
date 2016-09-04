package com.nannong.mall.response.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class GetRecommendShopListResponse extends BaseResponse
{


    /**
     * contentID : 1
     * contentCode : 16123003
     * contentName : 商品1
     * keyword : null
     * contentType : null
     * goodsType : 1
     * point : null
     * abstracts : 商品摘要1
     * recommend : 1
     * picUrl1 : http://obhtkhsaa.bkt.clouddn.com/content_14da99b6-0c7c-454e-90dd-82e891aa28df.jpg
     * picUrl2 :
     * picUrl3 :
     * picUrl4 :
     * description :  <p>似懂非懂神烦大叔范德萨发斯蒂芬斯蒂芬</p>
     * shopID : 1
     * categoryID : 1
     * categoryName : 商家分类1
     * free : null
     * originalPrice : null
     * price : 100
     * score : null
     * appraiseCount : null
     * adminID : 1
     * delFlag : 0
     * status : 1
     * createTime : 2016-09-01
     * descriptionLink : http://121.43.156.212:8080/api/h5/detail?type=4&id=1
     * thumPicUrl1 : http://obhtkhsaa.bkt.clouddn.com/content_14da99b6-0c7c-454e-90dd-82e891aa28df.jpg?imageMogr/v2/auto-orient/thumbnail/1366x768/quality/80/format/webp
     * thumPicUrl2 :
     * thumPicUrl3 :
     * thumPicUrl4 :
     * shopName : shagnjia 1
     */

    private List<ContentListBean> contentList;

    public List<ContentListBean> getContentList()
    {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList)
    {
        this.contentList = contentList;
    }

    public static class ContentListBean
    {
        private String contentID;

        private String contentCode;

        private String contentName;

        private Object keyword;

        private Object contentType;

        private int goodsType;

        private Object point;

        private String abstracts;

        private int recommend;

        private String picUrl1;

        private String picUrl2;

        private String picUrl3;

        private String picUrl4;

        private String description;

        private String shopID;

        private String categoryID;

        private String categoryName;

        private Object free;

        private Object originalPrice;

        private int price;

        private Object score;

        private Object appraiseCount;

        private String adminID;

        private int delFlag;

        private int status;

        private String createTime;

        private String descriptionLink;

        private String thumPicUrl1;

        private String thumPicUrl2;

        private String thumPicUrl3;

        private String thumPicUrl4;

        private String shopName;

        public String getContentID()
        {
            return contentID;
        }

        public void setContentID(String contentID)
        {
            this.contentID = contentID;
        }

        public String getContentCode()
        {
            return contentCode;
        }

        public void setContentCode(String contentCode)
        {
            this.contentCode = contentCode;
        }

        public String getContentName()
        {
            return contentName;
        }

        public void setContentName(String contentName)
        {
            this.contentName = contentName;
        }

        public Object getKeyword()
        {
            return keyword;
        }

        public void setKeyword(Object keyword)
        {
            this.keyword = keyword;
        }

        public Object getContentType()
        {
            return contentType;
        }

        public void setContentType(Object contentType)
        {
            this.contentType = contentType;
        }

        public int getGoodsType()
        {
            return goodsType;
        }

        public void setGoodsType(int goodsType)
        {
            this.goodsType = goodsType;
        }

        public Object getPoint()
        {
            return point;
        }

        public void setPoint(Object point)
        {
            this.point = point;
        }

        public String getAbstracts()
        {
            return abstracts;
        }

        public void setAbstracts(String abstracts)
        {
            this.abstracts = abstracts;
        }

        public int getRecommend()
        {
            return recommend;
        }

        public void setRecommend(int recommend)
        {
            this.recommend = recommend;
        }

        public String getPicUrl1()
        {
            return picUrl1;
        }

        public void setPicUrl1(String picUrl1)
        {
            this.picUrl1 = picUrl1;
        }

        public String getPicUrl2()
        {
            return picUrl2;
        }

        public void setPicUrl2(String picUrl2)
        {
            this.picUrl2 = picUrl2;
        }

        public String getPicUrl3()
        {
            return picUrl3;
        }

        public void setPicUrl3(String picUrl3)
        {
            this.picUrl3 = picUrl3;
        }

        public String getPicUrl4()
        {
            return picUrl4;
        }

        public void setPicUrl4(String picUrl4)
        {
            this.picUrl4 = picUrl4;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getShopID()
        {
            return shopID;
        }

        public void setShopID(String shopID)
        {
            this.shopID = shopID;
        }

        public String getCategoryID()
        {
            return categoryID;
        }

        public void setCategoryID(String categoryID)
        {
            this.categoryID = categoryID;
        }

        public String getCategoryName()
        {
            return categoryName;
        }

        public void setCategoryName(String categoryName)
        {
            this.categoryName = categoryName;
        }

        public Object getFree()
        {
            return free;
        }

        public void setFree(Object free)
        {
            this.free = free;
        }

        public Object getOriginalPrice()
        {
            return originalPrice;
        }

        public void setOriginalPrice(Object originalPrice)
        {
            this.originalPrice = originalPrice;
        }

        public int getPrice()
        {
            return price;
        }

        public void setPrice(int price)
        {
            this.price = price;
        }

        public Object getScore()
        {
            return score;
        }

        public void setScore(Object score)
        {
            this.score = score;
        }

        public Object getAppraiseCount()
        {
            return appraiseCount;
        }

        public void setAppraiseCount(Object appraiseCount)
        {
            this.appraiseCount = appraiseCount;
        }

        public String getAdminID()
        {
            return adminID;
        }

        public void setAdminID(String adminID)
        {
            this.adminID = adminID;
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

        public String getDescriptionLink()
        {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink)
        {
            this.descriptionLink = descriptionLink;
        }

        public String getThumPicUrl1()
        {
            return thumPicUrl1;
        }

        public void setThumPicUrl1(String thumPicUrl1)
        {
            this.thumPicUrl1 = thumPicUrl1;
        }

        public String getThumPicUrl2()
        {
            return thumPicUrl2;
        }

        public void setThumPicUrl2(String thumPicUrl2)
        {
            this.thumPicUrl2 = thumPicUrl2;
        }

        public String getThumPicUrl3()
        {
            return thumPicUrl3;
        }

        public void setThumPicUrl3(String thumPicUrl3)
        {
            this.thumPicUrl3 = thumPicUrl3;
        }

        public String getThumPicUrl4()
        {
            return thumPicUrl4;
        }

        public void setThumPicUrl4(String thumPicUrl4)
        {
            this.thumPicUrl4 = thumPicUrl4;
        }

        public String getShopName()
        {
            return shopName;
        }

        public void setShopName(String shopName)
        {
            this.shopName = shopName;
        }
    }
}
