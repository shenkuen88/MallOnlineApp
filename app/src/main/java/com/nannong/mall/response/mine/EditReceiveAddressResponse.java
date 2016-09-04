package com.nannong.mall.response.mine;


import cn.nj.www.my_module.bean.BaseResponse;

public class EditReceiveAddressResponse extends BaseResponse
{

    private AddressBean userAddress;

    public AddressBean getUserAddress()
    {
        return userAddress;
    }

    public void setUserAddress(AddressBean userAddress)
    {
        this.userAddress = userAddress;
    }


}
