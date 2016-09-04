package cn.nj.www.my_module.bean;


import cn.nj.www.my_module.bean.index.ArticleListBean;

public class NoticeInfoEvent extends BaseResponse
{
    private String tag;

    private String text;

    private int position;
    private ArticleListBean mArticleListBean;

    public NoticeInfoEvent(String tag, String text) {
        this.setTag(tag);
        this.setText(text);
    }

    public NoticeInfoEvent(String tag, String text, int position) {
        this.setTag(tag);
        this.setText(text);
        this.position = position;
    }

    public NoticeInfoEvent(String tag, String text, ArticleListBean position) {
        this.setTag(tag);
        this.setText(text);
        this.mArticleListBean = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArticleListBean getmArticleListBean() {
        return mArticleListBean;
    }

    public void setmArticleListBean(ArticleListBean mArticleListBean) {
        this.mArticleListBean = mArticleListBean;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
