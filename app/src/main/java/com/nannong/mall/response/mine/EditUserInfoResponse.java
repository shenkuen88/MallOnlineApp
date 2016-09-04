package com.nannong.mall.response.mine;


import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.index.UserBean;

public class EditUserInfoResponse extends BaseResponse
{

    /**
     * userID : 7
     * userName : 15211111111
     * password : HDG+CnX+UKYQficL0hlBb0fC9ZdIh0HF7oNGUPfnvM4=
     * userType : null
     * nickName : 15211111111
     * portrait : 15211111111
     * gender : 2
     * birthday : null
     * communityID : null
     * phone : 15211111111
     * email : null
     * status : 1
     * delFlag : 1
     * lastLoginTime : 2016-07-11
     * createTime : 2016-07-11
     */
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


}
