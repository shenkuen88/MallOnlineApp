package com.nannong.mall.response.index;


import cn.nj.www.my_module.bean.BaseResponse;

public class ContentDetailResponse extends BaseResponse
{


    private ContentListBean content;


    public ContentListBean getContent() {
        return content;
    }

    public void setContent(ContentListBean content) {
        this.content = content;
    }



}
