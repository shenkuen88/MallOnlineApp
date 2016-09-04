package com.nannong.mall.response.index;

import java.io.Serializable;

/**
 * Created by huqing on 2016/7/12.
 */
public class BannerListBean implements Serializable{
    private String recordID;
    private int serviceType;
    private String title;
    private String cover;
    private int type;
    private String link;
    private String content;
    private String contentID;
    private String status;
    private String createTime;
    private String thumCover;

    @Override
    public String toString() {
        return "BannerListBean{" +
                "recordID='" + recordID + '\'' +
                ", serviceType=" + serviceType +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", type=" + type +
                ", link='" + link + '\'' +
                ", content='" + content + '\'' +
                ", contentID='" + contentID + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getThumCover() {
        return thumCover;
    }

    public void setThumCover(String thumCover) {
        this.thumCover = thumCover;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
