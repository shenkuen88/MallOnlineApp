package com.nannong.mall.fragment.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nannong.mall.R;
import com.nannong.mall.activity.order.ConfirmOrderActivity;
import com.nannong.mall.adapter.cart.MyBaseExpandableListAdapter;
import com.nannong.mall.response.cart.CartDelResponse;
import com.nannong.mall.response.cart.CartNumResponse;
import com.nannong.mall.response.cart.CartResponse;
import com.nannong.mall.response.order.GoodsBean;
import com.nannong.mall.response.order.StoreBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * 购物车
 */
public class CartFragment extends BaseFragment {

    //定义父列表项List数据集合
    List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    //定义子列表项List数据集合
    List<List<Map<String, Object>>> childMapList_list = new ArrayList<List<Map<String, Object>>>();

    MyBaseExpandableListAdapter myBaseExpandableListAdapter;
    CheckBox id_cb_select_all;
    ExpandableListView expandableListView;
    RelativeLayout id_rl_cart_is_empty;
    RelativeLayout id_rl_foot;

    TextView id_tv_edit_all;
    private HeadView headView;
    private View mView;
    TextView id_tv_totalCount_jiesuan, id_tv_delete_all;
    private int page = 1;
    private String num = "10000";

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            //获取到是否是缓存
            NetResponseEvent.Cache cache = ((NetResponseEvent) event).getCache();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(CartResponse.class.getName())) {
                if (cache.equals(NetResponseEvent.Cache.isCache)) {
                    //缓存数据需要做特殊处理的时候进行(一般不用去做处理)
                } else if (cache.equals(NetResponseEvent.Cache.isNetWork)) {
                    //网络数据(一般不用去做处理)
                }
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CartResponse cartResponse = GsonHelper.toType(result, CartResponse.class);
                    if (Constants.SUCESS_CODE.equals(cartResponse.getResultCode())) {
                        parentMapList.clear();
                        childMapList_list.clear();
                        for (Map.Entry<String, List<CartResponse.CartRecord>> map : cartResponse.getCartRecordMap().entrySet()) {
                            CartResponse.Shop shop = cartResponse.getShopMap().get(map.getKey());
                            //提供父列表的数据
                            Map<String, Object> parentMap = new HashMap<String, Object>();
                            parentMap.put("parentName", new StoreBean(shop.getShopID(), shop.getShopName(), false, false));
                            parentMapList.add(parentMap);
                            //提供当前父列的子列数据
                            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();
                            for (int j = 0; j < map.getValue().size(); j++) {
                                CartResponse.CartRecord c = map.getValue().get(j);
                                Map<String, Object> childMap = new HashMap<String, Object>();

                                GoodsBean goodsBean = new GoodsBean(c.getCreateTime(), c.getUserID(), c.getPicUrl(), c.getPrice(),
                                        c.getStyle(), c.getCount(), c.getShopID(),
                                        c.getObjectName(), c.getShopName(),
                                        c.getRecordID(), c.getContentID(),
                                        c.getSize(), GoodsBean.STATUS_VALID, false, false);
                                childMap.put("childName", goodsBean);
                                childMapList.add(childMap);
                            }
                            childMapList_list.add(childMapList);
                        }
                        Log.e("sub", parentMapList.size() + "," + childMapList_list.size());
                        init();
                    } else {
                        ErrorCode.doCode(getActivity(), cartResponse.getResultCode(), cartResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(CartNumResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CartNumResponse cartNumResponse = GsonHelper.toType(result, CartNumResponse.class);
                    if (Constants.SUCESS_CODE.equals(cartNumResponse.getResultCode())) {
//                        initCartData();
                    } else {
                        ErrorCode.doCode(getActivity(), cartNumResponse.getResultCode(), cartNumResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(CartDelResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CartDelResponse delResponse = GsonHelper.toType(result, CartDelResponse.class);
                    if (Constants.SUCESS_CODE.equals(delResponse.getResultCode())) {
                        initCartData();
                    } else {
                        ErrorCode.doCode(getActivity(), delResponse.getResultCode(), delResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.cart_acty, null);
        return mView;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            initCartData();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            initCartData();
        }
    }

    private void init() {

        //initData();

        expandableListView = (ExpandableListView) mView.findViewById(R.id.id_elv_listview);
        initTitle();
        myBaseExpandableListAdapter = new MyBaseExpandableListAdapter(getActivity(), parentMapList, childMapList_list);
        expandableListView.setAdapter(myBaseExpandableListAdapter);
        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click：" + position, Toast.LENGTH_SHORT).show();
            }
        });


        for (int i = 0; i < parentMapList.size(); i++) {
            expandableListView.expandGroup(i);
        }


        id_rl_cart_is_empty = (RelativeLayout) mView.findViewById(R.id.id_rl_cart_is_empty);
        id_tv_delete_all = (TextView) mView.findViewById(R.id.id_tv_delete_all);
        id_tv_delete_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myBaseExpandableListAdapter.removeGoods();
                // Toast.makeText(mContext, "删除多选商品", Toast.LENGTH_SHORT).show();
            }
        });

        id_tv_edit_all = headView.getRightTextView();

        id_tv_edit_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    if (MyBaseExpandableListAdapter.EDITING.equals(tv.getText())) {
                        myBaseExpandableListAdapter.setupEditingAll(true);
                        tv.setText(MyBaseExpandableListAdapter.FINISH_EDITING);
                        changeFootShowDeleteView(true);//这边类似的功能 后期待使用观察者模式
                    } else {
                        myBaseExpandableListAdapter.setupEditingAll(false);
                        tv.setText(MyBaseExpandableListAdapter.EDITING);
                        changeFootShowDeleteView(false);//这边类似的功能 后期待使用观察者模式
                    }
                }
            }
        });

        id_cb_select_all = (CheckBox) mView.findViewById(R.id.id_cb_select_all);
      /* 要么遍历判断再选择 id_cb_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // Toast.makeText(mContext, "all isChecked：" + isChecked, Toast.LENGTH_SHORT).show();
                myBaseExpandableListAdapter.setupAllChecked(isChecked);
            }
        });*/
        id_cb_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) v;
                    myBaseExpandableListAdapter.setupAllChecked(checkBox.isChecked());
                }
            }
        });

        final TextView id_tv_totalPrice = (TextView) mView.findViewById(R.id.id_tv_totalPrice);

        id_tv_totalCount_jiesuan = (TextView) mView.findViewById(R.id.id_tv_totalCount_jiesuan);
        id_tv_totalCount_jiesuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBaseExpandableListAdapter.getGoods() == null || myBaseExpandableListAdapter.getGoods().size() == 0) {
                    Toast.makeText(getActivity(), "请选择结算商品", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                    intent.putExtra(IntentCode.ORDER_GOODS_LIST, GsonHelper.toJson(myBaseExpandableListAdapter.getGoods()));
                    intent.putExtra(IntentCode.ORDER_STATE, "0");//0 新生成订单，1 代付款订单
                    startActivity(intent);
                    CMLog.e(Constants.HTTP_TAG, "传递的数据 " + myBaseExpandableListAdapter.getGoods().size() + "  "
                            + myBaseExpandableListAdapter.getGoods().get(0).getGoodsBeanList().size());
                }

            }
        });
        myBaseExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyBaseExpandableListAdapter.OnGoodsCheckedChangeListener() {
            @Override
            public void onGoodsCheckedChange(int totalCount, double totalPrice) {
                id_tv_totalPrice.setText(String.format(getString(R.string.total), totalPrice));
                id_tv_totalCount_jiesuan.setText(String.format(getString(R.string.jiesuan), totalCount));
            }
        });

        myBaseExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyBaseExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
            @Override
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                id_cb_select_all.setChecked(allParentIsChecked);
            }
        });

        myBaseExpandableListAdapter.setOnEditingTvChangeListener(new MyBaseExpandableListAdapter.OnEditingTvChangeListener() {
            @Override
            public void onEditingTvChange(boolean allIsEditing) {

                changeFootShowDeleteView(allIsEditing);//这边类似的功能 后期待使用观察者模式

            }
        });

        myBaseExpandableListAdapter.setOnCheckHasGoodsListener(new MyBaseExpandableListAdapter.OnCheckHasGoodsListener() {
            @Override
            public void onCheckHasGoods(boolean isHasGoods) {
                setupViewsShow(isHasGoods);
            }
        });


        /**====include进来方式可能会导致view覆盖listview的最后一个item 解决*/
        //在onCreate方法中一般没办法直接调用view.getHeight方法来获取到控件的高度
        id_rl_foot = (RelativeLayout) mView.findViewById(R.id.id_rl_foot);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        id_rl_foot.measure(w, h);
        int r_width = id_rl_foot.getMeasuredWidth();
        int r_height = id_rl_foot.getMeasuredHeight();
        Log.i("MeasureSpec", "MeasureSpec r_width = " + r_width);
        Log.i("MeasureSpec", "MeasureSpec r_height = " + r_height);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int top = dp2px(getActivity(), 48);
        lp.setMargins(0, top, 0, r_height);//48

        expandableListView.setLayoutParams(lp);
        /**==========*/


        if (parentMapList != null && parentMapList.size() > 0) {
            setupViewsShow(true);
        } else {
            setupViewsShow(false);
        }

    /*    *//**
         * 第一个参数  应用程序接口 this
         * 第二个父列List<?extends Map<String,Object>>集合 为父列提供数据
         * 第三个参数  父列显示的组件资源文件
         * 第四个参数  键值列表 父列Map字典的key
         * 第五个要显示的父列组件id
         * 第六个 子列的显示资源文件
         * 第七个参数 键值列表的子列Map字典的key
         * 第八个要显示子列的组件id
         *
         * 第五个参数groupTo - The group views that should display column in the "groupFrom" parameter. These should all be TextViews. The first N views in this list are given the values of the first N columns in the groupFrom parameter.
         *//*

        SimpleExpandableListAdapter simpleExpandableListAdapter = new SimpleExpandableListAdapter(
                this, parentMapList, R.layout.parent_layout, new String[] { "parentName"},
                new int[] { R.id.tv_title_parent}, childMapList_list, R.layout.child_layout,
                new String[] { "childName"}, new int[] { R.id.tv_items_child});
        expandableListView.setAdapter(simpleExpandableListAdapter);*/
    }

    private void initTitle() {
        View view = mView.findViewById(R.id.common_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
            expandableListView.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
        }
        headView = new HeadView((ViewGroup) view);
        headView.setTitleText("购物车");
        headView.setHiddenLeft();
        headView.setRightText("编辑");
    }

    private void setupViewsShow(boolean isHasGoods) {
        if (isHasGoods) {
            expandableListView.setVisibility(View.VISIBLE);
            id_rl_cart_is_empty.setVisibility(View.GONE);
            id_rl_foot.setVisibility(View.VISIBLE);
            id_tv_edit_all.setVisibility(View.VISIBLE);
        } else {
            expandableListView.setVisibility(View.GONE);
            id_rl_cart_is_empty.setVisibility(View.VISIBLE);
            id_rl_foot.setVisibility(View.GONE);
            id_tv_edit_all.setVisibility(View.GONE);
        }
    }

    public void changeFootShowDeleteView(boolean showDeleteView) {

        if (showDeleteView) {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.FINISH_EDITING);

            id_tv_totalCount_jiesuan.setVisibility(View.GONE);
            id_tv_delete_all.setVisibility(View.VISIBLE);
        } else {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.EDITING);

            id_tv_totalCount_jiesuan.setVisibility(View.VISIBLE);
            id_tv_delete_all.setVisibility(View.GONE);
        }
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initCartData() {
//        NetLoadingDialog.getInstance().loading(getActivity());
        UserServiceImpl.instance().getCartList(page + "", num, CartResponse.class.getName());
        id_cb_select_all.setChecked(false);
//        for (int i = 0; i < 4; i++) {
//            String store = "旗舰店";
//            if (i % 2 == 0) {
//                store = "专营店";
//            }
//            //提供父列表的数据
//            Map<String, Object> parentMap = new HashMap<String, Object>();
//
//            parentMap.put("parentName", new StoreBean("" + i, store + i, false, false));
//          /*  if (i%2==0) {
//                parentMap.put("parentIcon", R.mipmap.ic_launcher);
//            }else
//            {
//                parentMap.put("parentIcon", R.mipmap.louisgeek);
//            }*/
//            parentMapList.add(parentMap);
//            //提供当前父列的子列数据
//            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();
//            for (int j = 0; j < 3; j++) {
//                Map<String, Object> childMap = new HashMap<String, Object>();
//
//                GoodsBean goodsBean = new GoodsBean(i + "_" + j, store + i + "下的商品" + j, "url", "均码，红色", 150, 120, 1, GoodsBean.STATUS_VALID, false, false);
//                childMap.put("childName", goodsBean);
//                childMapList.add(childMap);
//            }
//            childMapList_list.add(childMapList);
//        }
    }

}

