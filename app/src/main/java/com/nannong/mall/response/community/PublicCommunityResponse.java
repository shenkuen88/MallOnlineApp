package com.nannong.mall.response.community;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.index.ArticleListBean;

public class PublicCommunityResponse extends BaseResponse
{


    private int totalCount;


    private List<ArticleListBean> articleList;

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public List<ArticleListBean> getArticleList()
    {
        return articleList;
    }

    public void setArticleList(List<ArticleListBean> articleList)
    {
        this.articleList = articleList;
    }


}
