package com.nannong.mall.response.mine;

/**
 * Created by jwei on 2016/9/3 0003.
 */
public class CoupinsResponse {
    private String id;
    private String name;
    private String info;
    private String price;
    private String time;

    public CoupinsResponse() {
    }

    public CoupinsResponse(String id, String name, String info, String price, String time) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
