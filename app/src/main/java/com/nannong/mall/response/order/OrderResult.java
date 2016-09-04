package com.nannong.mall.response.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class OrderResult  {
    private String orderid;
    private String ordernum;
    private String orderstate;
    private String orderprice;
    private String storename;
    private String deliveryprice;
    private List<Goods> goodsList=new ArrayList<Goods>();
    public OrderResult() {
    }

    public OrderResult(String orderid, String ordernum, String orderstate, String orderprice, String storename, String deliveryprice, List<Goods> goodsList) {
        this.orderid = orderid;
        this.ordernum = ordernum;
        this.orderstate = orderstate;
        this.orderprice = orderprice;
        this.storename = storename;
        this.deliveryprice = deliveryprice;
        this.goodsList = goodsList;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public String getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(String deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public static class Goods {
        private String goodsid;
        private String goodsname;
        private String goodsprice;
        private String goodsoprice;
        private String type;
        private String goodsnum;
        private String goodspic;

        public Goods() {
        }

        public Goods(String goodsid, String goodsname, String goodsprice, String goodsoprice, String type, String goodsnum, String goodspic) {
            this.goodsid = goodsid;
            this.goodsname = goodsname;
            this.goodsprice = goodsprice;
            this.goodsoprice = goodsoprice;
            this.type = type;
            this.goodsnum = goodsnum;
            this.goodspic = goodspic;
        }

        public String getGoodspic() {
            return goodspic;
        }

        public void setGoodspic(String goodspic) {
            this.goodspic = goodspic;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getGoodsprice() {
            return goodsprice;
        }

        public void setGoodsprice(String goodsprice) {
            this.goodsprice = goodsprice;
        }

        public String getGoodsoprice() {
            return goodsoprice;
        }

        public void setGoodsoprice(String goodsoprice) {
            this.goodsoprice = goodsoprice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(String goodsnum) {
            this.goodsnum = goodsnum;
        }
    }
}
