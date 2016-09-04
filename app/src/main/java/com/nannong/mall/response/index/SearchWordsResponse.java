package com.nannong.mall.response.index;


import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class SearchWordsResponse extends BaseResponse
{
    List<String> searchList;

    public List<String> getSearchList()
    {
        if (searchList == null)
        {
            searchList = new ArrayList<>();
        }
        return searchList;
    }

    public void setSearchList(List<String> searchList)
    {
        this.searchList = searchList;
    }
}
