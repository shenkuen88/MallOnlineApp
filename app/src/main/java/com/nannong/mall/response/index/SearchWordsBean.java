package com.nannong.mall.response.index;

import java.io.Serializable;

/**
 * Created by huqing on 2016/9/1.
 */
public class SearchWordsBean implements Serializable
{
    String words;

    public String getWords()
    {
        return words;
    }

    public void setWords(String words)
    {
        this.words = words;
    }
}
