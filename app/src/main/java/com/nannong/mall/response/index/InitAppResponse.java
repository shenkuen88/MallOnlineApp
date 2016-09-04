package com.nannong.mall.response.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class InitAppResponse extends BaseResponse
{

    /**
     * serverTime : 20160815171642
     * appInitInfoList : []
     * appVersionInfo : {"id":"1","codeVersion":1002,"showVersion":"1.0.2","description":"版本升級測試描述信息","type":1,"url":"http://obhtkhsaa.bkt.clouddn.com/zhongchao-1.0.0.apk","createTime":null}
     * community : {"communityID":"1","name":"南京岔路口社区","province":"320000","city":"320100","area":"320115","addressDetail":"金盛路","picUrl":null,"gpsLong":"118.824731","gpsLati":"31.973317","scope":3,"delFlag":0,"status":1,"createTime":"2016-07-27"}
     */

    private String serverTime;
    /**
     * id : 1
     * codeVersion : 1002
     * showVersion : 1.0.2
     * description : 版本升級測試描述信息
     * type : 1
     * url : http://obhtkhsaa.bkt.clouddn.com/zhongchao-1.0.0.apk
     * createTime : null
     */

    private AppVersionInfoBean appVersionInfo;
    /**
     * communityID : 1
     * name : 南京岔路口社区
     * province : 320000
     * city : 320100
     * area : 320115
     * addressDetail : 金盛路
     * picUrl : null
     * gpsLong : 118.824731
     * gpsLati : 31.973317
     * scope : 3
     * delFlag : 0
     * status : 1
     * createTime : 2016-07-27
     */

    private CommunityBean community;
    private List<AppInitInfoListBean> appInitInfoList;
    public List<AppInitInfoListBean> getAppInitInfoList() {
        return appInitInfoList;
    }

    public void setAppInitInfoList(List<AppInitInfoListBean> appInitInfoList) {
        this.appInitInfoList = appInitInfoList;
    }
    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public AppVersionInfoBean getAppVersionInfo() {
        return appVersionInfo;
    }

    public void setAppVersionInfo(AppVersionInfoBean appVersionInfo) {
        this.appVersionInfo = appVersionInfo;
    }

    public CommunityBean getCommunity() {
        return community;
    }

    public void setCommunity(CommunityBean community) {
        this.community = community;
    }


    public static class AppVersionInfoBean {
        private String id;
        private int codeVersion;
        private String showVersion;
        private String description;
        private int type;
        private String url;
        private Object createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCodeVersion() {
            return codeVersion;
        }

        public void setCodeVersion(int codeVersion) {
            this.codeVersion = codeVersion;
        }

        public String getShowVersion() {
            return showVersion;
        }

        public void setShowVersion(String showVersion) {
            this.showVersion = showVersion;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }

    public static class CommunityBean {
        private String communityID;
        private String name;
        private String province;
        private String city;
        private String area;
        private String addressDetail;
        private Object picUrl;
        private String gpsLong;
        private String gpsLati;
        private int scope;
        private int delFlag;
        private int status;
        private String createTime;

        public String getCommunityID() {
            return communityID;
        }

        public void setCommunityID(String communityID) {
            this.communityID = communityID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public Object getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(Object picUrl) {
            this.picUrl = picUrl;
        }

        public String getGpsLong() {
            return gpsLong;
        }

        public void setGpsLong(String gpsLong) {
            this.gpsLong = gpsLong;
        }

        public String getGpsLati() {
            return gpsLati;
        }

        public void setGpsLati(String gpsLati) {
            this.gpsLati = gpsLati;
        }

        public int getScope() {
            return scope;
        }

        public void setScope(int scope) {
            this.scope = scope;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
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
