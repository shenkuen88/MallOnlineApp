package com.nannong.mall.response.index;


import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * Created by huqing on 2016/7/20.
 */
public class ContentListBean
{
    /**
     * 自增长ID
     */
    private String contentID;
    /**
     * 内容编码
     */
    private String contentCode;
    /**
     * 内容名称
     */
    private String contentName;
    /**
     * 内容类型 客户端不处理
     */
    private int serviceType;
    /**
     *
     */
    private String contentType;
    /**
     * 摘要，用于列表展示
     */
    private String abstracts;

    private String picUrl1;
    private String thumPicUrl1;
    private String thumPicUrl2;
    private String thumPicUrl3;
    private String thumPicUrl4;
    private String picUrl2;
    private String picUrl3;
    private String picUrl4;
    /**
     * 商品详情
     */
    private String description;
    private String specification;
    /**
     * 商家ID
     */
    private String shopID;
    /**
     * 一级分类ID
     */
    private String categoryID;
    /**
     * 二级分类ID
     */
    private String subCategoryID;
    /**
     * 品牌ID
     */
    private String brandID;
    /**
     * 是否免费 1-免费；0-收费
     */
    private int free;
    private String originalPrice;
    /**
     * 实际价格
     */
    private String price;
    private String shopName;
    /**
     * 评分
     */
    private String score;
    private String playCount;
    private String appraiseCount;
    private String adminID;
    private int delFlag;
    private int status;
    private String createTime;
    private String descriptionLink;
    private String specificationLink;

    public String getThumPicUrl1() {
        return thumPicUrl1;
    }

    public void setThumPicUrl1(String thumPicUrl1) {
        this.thumPicUrl1 = thumPicUrl1;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }

    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }

    public String getSpecificationLink() {
        return specificationLink;
    }

    public void setSpecificationLink(String specificationLink) {
        this.specificationLink = specificationLink;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getThumPicUrl2() {
        return thumPicUrl2;
    }

    public void setThumPicUrl2(String thumPicUrl2) {
        this.thumPicUrl2 = thumPicUrl2;
    }

    public String getThumPicUrl3() {
        return thumPicUrl3;
    }

    public void setThumPicUrl3(String thumPicUrl3) {
        this.thumPicUrl3 = thumPicUrl3;
    }

    public String getThumPicUrl4() {
        return thumPicUrl4;
    }

    public void setThumPicUrl4(String thumPicUrl4) {
        this.thumPicUrl4 = thumPicUrl4;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public String getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2;
    }

    public String getPicUrl3() {
        return picUrl3;
    }

    public void setPicUrl3(String picUrl3) {
        this.picUrl3 = picUrl3;
    }

    public String getPicUrl4() {
        return picUrl4;
    }

    public void setPicUrl4(String picUrl4) {
        this.picUrl4 = picUrl4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        if (GeneralUtils.isNotNullOrZeroLenght(price)) {
            return price;
        } else {
            return "0";
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getAppraiseCount() {
        if (GeneralUtils.isNotNullOrZeroLenght(appraiseCount)) {
            return appraiseCount;
        } else {
            return 0 + "";
        }
    }

    public void setAppraiseCount(String appraiseCount) {
        this.appraiseCount = appraiseCount;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
