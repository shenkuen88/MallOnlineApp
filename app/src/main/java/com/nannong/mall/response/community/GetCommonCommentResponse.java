package com.nannong.mall.response.community;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class GetCommonCommentResponse extends BaseResponse
{

    private int totalCount;

    private List<CommentListBean> commentList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

}
