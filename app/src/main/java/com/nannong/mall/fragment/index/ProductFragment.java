package com.nannong.mall.fragment.index;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.NewProductActy;
import com.nannong.mall.activity.index.ProductCommentActy;
import com.nannong.mall.response.index.AppraiseListBean;
import com.nannong.mall.response.index.CollectContentResponse;
import com.nannong.mall.response.index.ContentDetailResponse;
import com.nannong.mall.response.index.ContentListBean;
import com.nannong.mall.response.index.ProductCommentResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.banner.CusConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.demo.NetworkImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 产品详情
 */
public class ProductFragment extends BaseFragment implements View.OnClickListener {


    /**
     * Banner
     */
    private CusConvenientBanner banner;
    /**
     * 分享
     */
    private TextView tvShare;
    /**
     * 选择
     */
    private TextView tvChoose;
    /**
     * 评论人名
     */
    private TextView tvCommentName;
    /**
     * 评论内容
     */
    private TextView tvComment;
    /**
     * 评论诗句
     */
    private TextView tvCommentTime;
    /**
     * 评论头像
     */
    private ImageView ivCommentHead;

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();
    /**
     * 默认的本地地址
     */
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    /**
     * 商品名称
     */
    private TextView tvName;
    /**
     * 价格
     */
    private TextView tvPrice;
    private PtrClassicFrameLayout refresh;
    /**
     * 现价
     */
    private TextView tvPriceNew;
//    /**
//     * 款式
//     */
//    private List<ContentStyleListBean> styleList;
    /**
     * 已选择
     */
    private RelativeLayout rlChoose;
    private ContentDetailResponse mContentDetailResponse;
    private Dialog styleDialog;
    private RelativeLayout rlComment;
    private static String mContentId;

    private View mView;
    private LinearLayout top_tab_layout,hide_layout;
    private TabLayout tab_product_title,tab_product_title2;
    private ArrayList<String> list_title;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private ProductBottomWebFragment mProductBottomWebFragment;
    private ProductBottomWebFragment mProductBottomParamsFragment;
    //    private DecorateCommentFragment mCommentFragment;
    private FragmentPagerAdapter fAdapter;
//    private ViewPager vp_FindFragment_pager;


    private View tvTitle;
    private NestedScrollView all_content_ll;
    private TextView btn_favour;
    private TextView info_tv;

    private boolean collectFlag = false;

    public static void setType(String type1) {
        type = type1;
    }

    private static String type = "1";

    public static ProductFragment newInstance(String contentId) {
        ProductFragment fragment = new ProductFragment();
        mContentId = contentId;
        return fragment;
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.product_detai_top, null);
        return mView;
    }

    @Override
    public void initView(View view)
    {
    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getActivity().getClass().getName())) {
            } else if (NotiTag.TAG_CHANGE_STYLE.equals(tag)) {
                String buyStyle = ((NoticeEvent) event).getText();
                tvChoose.setText(buyStyle);
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            refresh.refreshComplete();
            //收藏
            if (NotiTag.equalsTags(getActivity(), tag, CollectContentResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CollectContentResponse mCollectContentResponse = GsonHelper.toType(result, CollectContentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mCollectContentResponse.getResultCode()))
                    {
//                        Drawable drawable = getResources().getDrawable(R.mipmap.product_collected);
//                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
//                        collect_tv.setCompoundDrawables(null, drawable, null, null);//画在上方
                        collectFlag = true;
                        btn_favour.setBackgroundResource(R.drawable.app_yellow_bn);
                        btn_favour.setTextColor(Color.WHITE);
                        ToastUtil.makeText(getActivity(), getString(R.string.collect_success));
                    }
                    else
                    {
                        ErrorCode.doCode(getActivity(), mCollectContentResponse.getResultCode(), mCollectContentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(ContentDetailResponse.class.getName())) {
                mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode())) {
                        ContentListBean product = mContentDetailResponse.getContent();
                        List<String> bannerList = new ArrayList<>();
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl1())) {
                            bannerList.add(product.getThumPicUrl1());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl2())) {
                            bannerList.add(product.getThumPicUrl2());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl3())) {
                            bannerList.add(product.getThumPicUrl3());
                        }
                        if (GeneralUtils.isNotNullOrZeroLenght(product.getPicUrl4())) {
                            bannerList.add(product.getThumPicUrl4());
                        }
                        initBanner(bannerList);
                        tvName.setText(product.getContentName());
                        info_tv.setText(product.getContentType());
                        tvPrice.setText("￥"+product.getPrice());
//                        //已选
//                        styleList = mContentDetailResponse.getContentStyleList();
//                        if (styleList.size() > 0) {
//                            tvChoose.setText(styleList.get(0).getStyle());
//                            tvPrice.setText("¥" + styleList.get(0).getPrice());
//                        }
//                        styleDialog = ProductDialogUtil.productChoose(getActivity(), mContentDetailResponse);
                    } else {
                        ErrorCode.doCode(getActivity(), mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            //评论
            if (tag.equals(ProductCommentResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ProductCommentResponse mProductCommentResponse = GsonHelper.toType(result, ProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mProductCommentResponse.getResultCode())) {
                        if (mProductCommentResponse.getAppraiseList().size() > 0) {
                            mView.findViewById(R.id.comment_detail_rl).setVisibility(View.VISIBLE);
                            mView.findViewById(R.id.no_comment_tv).setVisibility(View.GONE);
                            AppraiseListBean appraiseItem = mProductCommentResponse.getAppraiseList().get(0);
                            tvComment.setText(appraiseItem.getText());
                            tvCommentName.setText(appraiseItem.getUserNickName());
                            tvCommentTime.setText(appraiseItem.getCreateTime());
                            GeneralUtils.setRoundImageViewWithUrl(getActivity(), appraiseItem.getUserPortrait(), ivCommentHead, R.drawable.default_head);
                        }else {
                            mView.findViewById(R.id.comment_detail_rl).setVisibility(View.GONE);
                            mView.findViewById(R.id.no_comment_tv).setVisibility(View.VISIBLE);
                        }
                    } else {
                        ErrorCode.doCode(getActivity(), mProductCommentResponse.getResultCode(), mProductCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(getActivity());
                }
            }
            if (tag.equals(ContentDetailResponse.class.getName())) {
                ContentDetailResponse mContentDetailResponse = GsonHelper.toType(result, ContentDetailResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mContentDetailResponse.getResultCode())) {
                        if (mProductBottomWebFragment!=null){
                            mProductBottomWebFragment.loadUrl(  mContentDetailResponse.getContent().getSpecificationLink());
                        }
                        if (mProductBottomParamsFragment!=null){
                            mProductBottomParamsFragment.loadUrl(mContentDetailResponse.getContent().getDescriptionLink());
                        }
                    } else {
//                        ErrorCode.doCode(getActivity(), mContentDetailResponse.getResultCode(), mContentDetailResponse.getDesc());
                    }
                } else {
//                    ToastUtil.showError(getActivity());
                }
            }
        }
    }


    /**
     * Banner展示网络数据
     *
     * @param ad
     */
    private synchronized void initBanner(final List<String> ad) {
        if (ad == null || ad.size() < 1) {
            return;
        }
        networkImages.clear();
        for (int i = 0; i < ad.size(); i++) {
            if (!networkImages.contains(ad.get(i))) {
                networkImages.add(ad.get(i));
            }
        }
        banner.stopTurning();
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_rl:
            case R.id.comment_detail_rl:
                startActivity(new Intent(getActivity(), ProductCommentActy.class));
                break;
            case R.id.share_tv:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " " + mContentDetailResponse.getContent().getContentName());
                intent.putExtra(Intent.EXTRA_TEXT, mContentDetailResponse.getContent().getSpecificationLink());
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
                break;
            case R.id.choose_rl:
                if (styleDialog != null) {
                    styleDialog.show();
                }
                break;
            case R.id.btn_fanhui:
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CLOSE_ACTIVITY));
                break;
            case R.id.btn_favour:
                if (collectFlag){
                    return;
                }
                ((NewProductActy)getActivity()).shouCang();
                break;
        }
    }

    private void initData() {
        UserServiceImpl.instance().getContentDetail(mContentId, ContentDetailResponse.class.getName());
        UserServiceImpl.instance().getProductCommentList(mContentId, ProductCommentResponse.class.getName());
    }

    private void initView() {
        mView.findViewById(R.id.btn_fanhui).setOnClickListener(this);
//        if (!DisplayUtil.checkDeviceHasNavigationBar(getActivity())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                mView.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//            }
//        }
        refresh = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_grid_view_frame);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                refresh.autoRefresh();
            }
        }, 200);
        banner = (CusConvenientBanner) mView.findViewById(R.id.product_banner);
        tvShare = (TextView) mView.findViewById(R.id.share_tv);
        rlComment = (RelativeLayout) mView.findViewById(R.id.comment_rl);
        tvChoose = (TextView) mView.findViewById(R.id.choose_tv);
        tvName = (TextView) mView.findViewById(R.id.name_tv);
        tvPrice = (TextView) mView.findViewById(R.id.price_tv);
        tvPriceNew = (TextView) mView.findViewById(R.id.price1_tv);
        tvCommentName = (TextView) mView.findViewById(R.id.comment_name_tv);
        tvCommentTime = (TextView) mView.findViewById(R.id.comment_time_tv);
        tvComment = (TextView) mView.findViewById(R.id.comment_content_tv);
        rlChoose = (RelativeLayout) mView.findViewById(R.id.choose_rl);
        ivCommentHead = (ImageView) mView.findViewById(R.id.comment_head_iv);
        tvShare.setOnClickListener(this);
        mView.findViewById(R.id.comment_detail_rl).setOnClickListener(this);
        rlChoose.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        bannerFirstInit();
        refresh.setLastUpdateTimeRelateObject(this);
        refresh.setResistance(1.7f);
        refresh.setRatioOfHeaderHeightToRefresh(1.2f);
        refresh.setDurationToClose(200);
        refresh.setDurationToCloseHeader(1000);
        // default is false
        refresh.setPullToRefresh(false);
        // default is true
        refresh.setKeepHeaderWhenRefresh(true);

        refresh.disableWhenHorizontalMove(true);

        refresh.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        tvTitle = (View)mView.findViewById(R.id.line_view);
//        if (!DisplayUtil.checkDeviceHasNavigationBar(getActivity())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                tvTitle.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
//            }
//        }
//        vp_FindFragment_pager = (ViewPager) mView.findViewById(R.id.vp_FindFragment_pager);
        tab_product_title = (TabLayout) mView.findViewById(R.id.tab_product_title);
        tab_product_title2= (TabLayout) mView.findViewById(R.id.tab_product_title2);
        list_title = new ArrayList<>();
        list_title.add("图文详情");
        if (type.equals("1")) {
            list_title.add("商品参数");
        } else {
            list_title.add("评论");
        }

        //设置TabLayout的模式
        tab_product_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_product_title.addTab(tab_product_title.newTab().setText(list_title.get(0)));
        tab_product_title.addTab(tab_product_title.newTab().setText(list_title.get(1)));
        //设置TabLayout的模式
        tab_product_title2.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_product_title2.addTab(tab_product_title2.newTab().setText(list_title.get(0)));
        tab_product_title2.addTab(tab_product_title2.newTab().setText(list_title.get(1)));

        //初始化各fragment
        mProductBottomParamsFragment = new ProductBottomWebFragment();
        mProductBottomWebFragment = new ProductBottomWebFragment();
//        mCommentFragment = new DecorateCommentFragment();

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(mProductBottomWebFragment);
//        if (type.equals("1")){
//            list_fragment.add(mProductBottomParamsFragment);
//        }else {
////            list_fragment.add(mCommentFragment);
//        }

//        list_fragment.add(new Fragment());
//        list_fragment.add(new Fragment());
        FrameLayout btm_fra_layout=V.f(mView,R.id.btm_fra_layout);
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) btm_fra_layout.getLayoutParams();
        lp.height= DisplayUtil.getHeight(getActivity());
        btm_fra_layout.setLayoutParams(lp);
        FragmentTransaction tra = getActivity().getSupportFragmentManager().beginTransaction();
        tra.add(R.id.btm_fra_layout, mProductBottomWebFragment, "mProductBottomWebFragment");
        tra.commit();
//        fAdapter = new CouponFragmentAdapter(getActivity().getSupportFragmentManager(), list_fragment, list_title);

        //viewpager加载adapter
//        vp_FindFragment_pager.setAdapter(fAdapter);
//        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) vp_FindFragment_pager.getLayoutParams();
//        lp.height= DisplayUtil.getHeight(getActivity());
//        vp_FindFragment_pager.setLayoutParams(lp);
//        vp_FindFragment_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position==0){
//                    FragmentTransaction tra = getActivity().getSupportFragmentManager().beginTransaction();
//                    tra.add(R.id.btm_fra_layout, mProductBottomWebFragment, "mProductBottomWebFragment");
//                    tra.commit();
//                }else{
//                    FragmentTransaction tra = getActivity().getSupportFragmentManager().beginTransaction();
//                    tra.add(R.id.btm_fra_layout, mProductBottomParamsFragment, "mProductBottomParamsFragment");
//                    tra.commit();
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager

//        tab_product_title.setupWithViewPager(vp_FindFragment_pager);

        top_tab_layout= V.f(mView,R.id.top_tab_layout);
        hide_layout= V.f(mView,R.id.hide_layout);
        all_content_ll=V.f(mView,R.id.all_content_ll);
        all_content_ll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>=hide_layout.getY()){
                    top_tab_layout.setVisibility(View.VISIBLE);
                }else{
                    top_tab_layout.setVisibility(View.GONE);
                }
            }
        });
        btn_favour=V.f(mView,R.id.btn_favour);
        btn_favour.setOnClickListener(this);
        info_tv=V.f(mView,R.id.info_tv);
    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit() {
        //第一次展示默认本地图片
        localImages.add(R.drawable.default_bg);//默认图片
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        banner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        banner.stopTurning();
    }

}
