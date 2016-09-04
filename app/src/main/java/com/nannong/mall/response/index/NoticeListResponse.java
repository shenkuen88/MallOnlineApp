package com.nannong.mall.response.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class NoticeListResponse extends BaseResponse
{


    private int totalCount;


    private List<NoticeListBean> noticeList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<NoticeListBean> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeListBean> noticeList) {
        this.noticeList = noticeList;
    }

    public static class NoticeListBean {
        private String recordID;
        private String title;
        private Object newNotice;
        private int type;
        private String link;
        private String content;
        private String contentID;
        private int status;
        private String createTime;

        public String getRecordID() {
            return recordID;
        }

        public void setRecordID(String recordID) {
            this.recordID = recordID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getNewNotice() {
            return newNotice;
        }

        public void setNewNotice(Object newNotice) {
            this.newNotice = newNotice;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
