package com.nannong.mall.response.mine;

/**
 * Created by jwei on 2016/9/3 0003.
 */
public class BillListResponse {
    private String id;
    private String pic;
    private String price;
    private String type;
    private String info;
    private String time;

    public BillListResponse() {
    }

    public BillListResponse(String id, String pic, String price, String type, String info, String time) {
        this.id = id;
        this.pic = pic;
        this.price = price;
        this.type = type;
        this.info = info;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
