package com.nannong.mall.response.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class GetBrandListResponse extends BaseResponse
{


    private List<CategoryListBean> categoryList;

    public List<CategoryListBean> getCategoryList()
    {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList)
    {
        this.categoryList = categoryList;
    }

    public static class CategoryListBean
    {
        private String categoryID;

        private String categoryName;

        private String shopID;

        private int seq;

        private int delFlag;

        private int status;

        private String createTime;

        private String shopName;

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

        public String getShopID()
        {
            return shopID;
        }

        public void setShopID(String shopID)
        {
            this.shopID = shopID;
        }

        public int getSeq()
        {
            return seq;
        }

        public void setSeq(int seq)
        {
            this.seq = seq;
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
