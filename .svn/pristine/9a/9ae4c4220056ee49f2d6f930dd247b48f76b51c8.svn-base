package com.nannong.mall.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nannong.mall.R;
import com.nannong.mall.adapter.index.CouponFragmentAdapter;
import com.nannong.mall.response.mine.DelFavourResponse;
import com.nannong.mall.response.mine.FavourResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.MySwipeMenuListView;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenu;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuCreator;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuItem;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuListView;

/**
 * create jwei by 2016/7/10
 * 收藏
 */
public class MyFavourActivity extends BaseActivity {
    private HeadView headView;
    private LinearLayout no_history;//空页面
//    private ListView his_goods_list;
    private MySwipeMenuListView his_goods_list;
    private CommonAdapter<FavourResponse.FavoriteListBean> hisgoodsAdapter;
    private List<FavourResponse.FavoriteListBean> hglist = new ArrayList<FavourResponse.FavoriteListBean>();

    private int page=1;
    private String num="10";
    private TabLayout tab_product_title;
    private ViewPager view_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_goods);
        initAll();
    }

    @Override
    public void initView() {
        initTitle();
        his_goods_list = V.f(this, R.id.his_goods_list);
        view_pager=V.f(this,R.id.view_pager);
        tab_product_title=V.f(this,R.id.tab_product_title);
        initEmtyView();
    }

    @Override
    public void initViewData() {
//        hisgoodsAdapter=new CommonAdapter<HistoryGoodsResult>(mContext,hglist,R.layout.his_goods_item) {
//            @Override
//            public void convert(ViewHolder helper, HistoryGoodsResult item) {
//                helper.setText(R.id.title, item.getName());
//                CommonAdapter<HistoryGoodsResult.Goods> mAdapter =
//                        new CommonAdapter<HistoryGoodsResult.Goods>(mContext,item.getGoods(),R.layout.his_g_item) {
//                            @Override
//                            public void convert(ViewHolder helper, HistoryGoodsResult.Goods item) {
//                                helper.setText(R.id.goods_info,item.getName());
//                                helper.setText(R.id.goods_price,"￥"+item.getPrice());
//                                if(GeneralUtils.isNotNullOrZeroLenght(item.getPic())){
//                                    ImageView img=helper.getView(R.id.img);
//                                    ImageLoaderUtil.getInstance().initImage(mContext, item.getPic(), img, Constants.DEFAULT_IMAGE_F_LOAD);
//                                }
//                            }
//                        };
//                MySwipeMenuListView my_sub_list=helper.getView(R.id.my_sub_list);
//                my_sub_list.setAdapter(mAdapter);
//                GeneralUtils.setListViewHeightBasedOnChildrenExtend(my_sub_list);
//                initLeftSlideList(my_sub_list);
//            }
//        };
//        his_goods_list.setAdapter(hisgoodsAdapter);
        List<Fragment> list_fragment=new ArrayList<>();
        list_fragment.add(new Fragment());list_fragment.add(new Fragment());
        List<String> list_title=new ArrayList<>();
        list_title.add("商铺");
        list_title.add("商品");
        CouponFragmentAdapter fAdapter = new CouponFragmentAdapter(getSupportFragmentManager()
                , list_fragment, list_title);
        //viewpager加载adapter
        view_pager.setAdapter(fAdapter);
        tab_product_title.setupWithViewPager(view_pager);

        hisgoodsAdapter =
                new CommonAdapter<FavourResponse.FavoriteListBean>(mContext,hglist,R.layout.item_his_g) {
                    @Override
                    public void convert(ViewHolder helper, FavourResponse.FavoriteListBean item) {
                        helper.setText(R.id.goods_info,item.getObjectName());
                        helper.setText(R.id.goods_price,"￥"+item.getPrice());
                        if(GeneralUtils.isNotNullOrZeroLenght(item.getThumPicUrl())){
                            ImageView img=helper.getView(R.id.img);
//                            ImageLoaderUtil.getInstance().initImage(mContext, item.getPicUrl(), img, Constants.DEFAULT_IMAGE_F_LOAD);
                            GeneralUtils.setImageViewWithUrl(mContext, item.getThumPicUrl(), img, R.drawable.default_bg);
                        }
                    }
                };
        his_goods_list.setAdapter(hisgoodsAdapter);
        his_goods_list.setEmptyView(no_history);
        initLeftSlideList(his_goods_list);
        page=1;
        getFavourGoods();
    }
    //获取收藏
    private void getFavourGoods() {
//        hglist.clear();
//        for(int i=0;i<2;i++){
//            List<HistoryGoodsResult.Goods> templist=new ArrayList<HistoryGoodsResult.Goods>();
//            for(int j=0;j<4;j++){
//                HistoryGoodsResult.Goods goods=new HistoryGoodsResult.Goods(""+j,"视频"+j,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3704122693,1924714915&fm=21&gp=0.jpg","100");
//                templist.add(goods);
//            }
//            hglist.add(new HistoryGoodsResult(templist,""+i,"商品"+i));
//            hisgoodsAdapter.setData(hglist);
//            hisgoodsAdapter.notifyDataSetChanged();
//        }
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getFavourList(page+"", num, FavourResponse.class.getName());
    }
    private boolean isloading=false;
    private int count=0;
    @Override
    public void initEvent() {
        his_goods_list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if(!isloading){
                            isloading=true;
                            if(count>page*Integer.valueOf(num)) {
                                page++;
                                getFavourGoods();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });
        his_goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    private FavourResponse.FavoriteListBean delitem=null;
    //初始化向左滑动出现删除按钮
    private void initLeftSlideList(MySwipeMenuListView listview){
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.app_color)));
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(mContext,60));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu

                menu.addMenuItem(openItem);

            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                delitem=hglist.get(position);
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().DelFavour(delitem.getFavoriteID(), DelFavourResponse.class.getName());
                return false;
            }
        });
    }
    //初始化标题
    private void initTitle() {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("我的收藏");
        headView.setRightImage(R.mipmap.search_icon);
    }
    //初始化空View
    private void initEmtyView(){
        no_history = V.f(this, R.id.no_history);
        ImageView tips_pic= V.f(this, R.id.tips_pic);
        TextView tips= V.f(this, R.id.tips);
        tips_pic.setImageResource(R.mipmap.no_collect_icon);
        tips.setText("您还没有任何收藏哦，赶快去看看吧~~");
    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
                Toast.makeText(mContext, "click：right", Toast.LENGTH_SHORT).show();
//                DialogUtil.showNoTipTwoBnttonDialog(mContext
//                        ,"你确定要清空浏览历史吗?"
//                        ,"取消"
//                        ,"确定"
//                        ,NotiTag.TAG_DEL_GOODS_CANCEL,NotiTag.TAG_DEL_GOODS_OK);
            }
//            if(NotiTag.TAG_DEL_GOODS_OK.equals(tag)){
//                Toast.makeText(mContext, "click：right", Toast.LENGTH_SHORT).show();
//            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(FavourResponse.class.getName())) {
                isloading=false;
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    FavourResponse favourResponse = GsonHelper.toType(result, FavourResponse.class);
                    if (Constants.SUCESS_CODE.equals(favourResponse.getResultCode())) {
                        if(page==1) {
                            hglist.clear();
                        }
                        if(favourResponse.getFavoriteList()!=null) {
                            hglist.addAll(favourResponse.getFavoriteList());
                        }
                        hisgoodsAdapter.setData(hglist);
                        hisgoodsAdapter.notifyDataSetChanged();
                    } else {
                        ErrorCode.doCode(mContext, favourResponse.getResultCode(), favourResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(DelFavourResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    FavourResponse favourResponse = GsonHelper.toType(result, FavourResponse.class);
                    if (Constants.SUCESS_CODE.equals(favourResponse.getResultCode())) {
                        if(delitem!=null){
                            hglist.remove(delitem);
                            hisgoodsAdapter.setData(hglist);
                            hisgoodsAdapter.notifyDataSetChanged();
                        }else{
                            page=1;
                            getFavourGoods();
                        }
                    } else {
                        ErrorCode.doCode(mContext, favourResponse.getResultCode(), favourResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }
}
