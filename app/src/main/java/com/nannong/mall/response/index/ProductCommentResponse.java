package com.nannong.mall.response.index;


import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class ProductCommentResponse extends BaseResponse
{

    private int totalCount;

    private List<AppraiseListBean> appraiseList= new ArrayList<>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AppraiseListBean> getAppraiseList() {
        return appraiseList;
    }

    public void setAppraiseList(List<AppraiseListBean> appraiseList) {
        this.appraiseList = appraiseList;
    }

}
