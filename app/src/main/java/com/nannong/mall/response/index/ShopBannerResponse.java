package com.nannong.mall.response.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class ShopBannerResponse extends BaseResponse
{


    private List<BannerListBean> bannerList;


    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }



}
