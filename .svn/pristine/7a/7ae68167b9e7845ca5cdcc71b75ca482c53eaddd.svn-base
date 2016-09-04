//package com.nannong.live.main.view;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.nj.www.my_module.R;
//import cn.nj.www.my_module.bean.NoticeEvent;
//import cn.nj.www.my_module.bean.index.ContentDetailResponse;
//import cn.nj.www.my_module.bean.index.ContentListBean;
//import cn.nj.www.my_module.bean.index.ContentStyleListBean;
//import cn.nj.www.my_module.constant.NotiTag;
//import cn.nj.www.my_module.tools.DialogUtil;
//import cn.nj.www.my_module.tools.GeneralUtils;
//import cn.nj.www.my_module.view.FlowLayout;
//import cn.nj.www.my_module.view.PlusMinusBar;
//import de.greenrobot.event.EventBus;
//
///**
// * 产品规格选择的dialog
// */
//public class ProductDialogUtil {
//
//    static int num = 0;//数量
//    static Context mContext;
//    static EditText num_et;
//    /**
//     * 流式布局  规格选择的
//     */
//    private static FlowLayout flowLayout;
//    private static List<ContentStyleListBean> styleList = new ArrayList<>();
//    private static List<String> styleStrList = new ArrayList<>();
//    /**
//     * 已选
//     */
//    private static TextView choose_tv;
//    private static TextView price_tv;
//    /**
//     * 加减控件
//     */
//    private static PlusMinusBar pm;
//    private static int index = 0;
//    private static String style = "";
//
//    /**
//     * 产品规格选
//     */
//    public static Dialog productChoose(final Context context, ContentDetailResponse mContentDetailResponse) {
//        mContext = context;
//        final Dialog dialog = DialogUtil.initDialog(context, R.layout.dlg_product_choose);
//        final ImageView product_iv = (ImageView) dialog.findViewById(R.id.product_iv);
//        final ImageView cancel_iv = (ImageView) dialog.findViewById(R.id.cancel_iv);
//        final TextView name_tv = (TextView) dialog.findViewById(R.id.name_tv);
//        pm = (PlusMinusBar) dialog.findViewById(R.id.num_bar);
//        price_tv = (TextView) dialog.findViewById(R.id.price_tv);
//        choose_tv = (TextView) dialog.findViewById(R.id.choose_tv);
//        final Button addBuy_bn = (Button) dialog.findViewById(R.id.addBuy_bn);
//        final Button buy_bn = (Button) dialog.findViewById(R.id.buy_bn);
//        flowLayout = (FlowLayout) dialog.findViewById(R.id.des_fl);
//        dialog.setCanceledOnTouchOutside(false);
//        //右上角关闭
//        cancel_iv.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        ContentListBean product = mContentDetailResponse.getContent();
//        //图片
////        ImageLoaderUtil.getInstance().initImage(context, product.getPicUrl1(), product_iv, Constants.DEFAULT_IMAGE_F_LOAD);
//        GeneralUtils.setImageViewWithUrl(context, product.getThumPicUrl1(), product_iv,  R.drawable.default_bg);
//        //名字
//        name_tv.setText(product.getContentName());
//        //价格
//        price_tv.setText(product.getPrice());
//        //已选
//        choose_tv.setText("已选：" + mContentDetailResponse.getContentStyleList().get(0).getStyle());
//        //样式选择
//        styleList = mContentDetailResponse.getContentStyleList();
//        styleStrList.clear();
//        for (int i = 0; i < styleList.size(); i++) {
//            styleStrList.add(styleList.get(i).getStyle());
//        }
//        initChildViews((String[]) styleStrList.toArray(new String[styleStrList.size()]));
//        //加减控件 对价格的显示设置
//        pm.setiResult(new PlusMinusBar.IResult() {
//            @Override
//            public void onResult(int num) {
//                price_tv.setText("¥" + styleList.get(index).getPrice() * num);
//                if (num > 0) {
//                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHANGE_BUY_NUM, pm.getNum()));
//                }else {
//                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHANGE_BUY_NUM, pm.getNum()));
//                }
//            }
//        });
//        //添加到购物车
//        addBuy_bn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pm.getNum() > 0) {
//                    if (GeneralUtils.isLogin()) {
//                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_ADD_CAR, pm.getNum(), style));
//                        dialog.dismiss();
//                    } else {
////                        context.startActivity(new Intent(mContext, LoginActy.class));
////                        dialog.dismiss();
//                    }
//                } else {
//                }
//            }
//        });
//        //立即购买
//        buy_bn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pm.getNum() > 0) {
//                    if (GeneralUtils.isLogin()) {
//                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_BUY_NOW));
//                        dialog.dismiss();
//                    } else {
////                        context.startActivity(new Intent(mContext, LoginActy.class));
////                        dialog.dismiss();
//                    }
//                }
//            }
//        });
//        return dialog;
//    }
//
//
//    private static void initChildViews(String hotWords[]) {
//        flowLayout.setVisibility(View.VISIBLE);
//        flowLayout.removeAllViews();
//        final List<TextView> tvList = new ArrayList<>();
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.leftMargin = 25;
//        lp.rightMargin = 25;
//        lp.topMargin = 15;
//        lp.bottomMargin = 15;
//        for (int i = 0; i < hotWords.length; i++) {
//            final TextView view = new TextView(mContext);
//            if (GeneralUtils.isNotNullOrZeroLenght(hotWords[i])) {
//                view.setText(hotWords[i]);
//                view.setTag(i);
//                view.setTextColor(Color.GRAY);
//                view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle));
//                tvList.add(view);
//                //默认选中第一个
//                if (i == 0) {
//                    view.setTextColor(mContext.getResources().getColor(R.color.app_color));
//                    view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle_pink));
//                    choose_tv.setText("已选：" + styleList.get((int) view.getTag()).getStyle());
//                    price_tv.setText("¥" + styleList.get((int) view.getTag()).getPrice());
//                }
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        style = styleList.get((int) view.getTag()).getStyle();
//                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHANGE_STYLE, styleList.get((int) view.getTag()).getStyle()));
//                        choose_tv.setText("已选：" + styleList.get((int) view.getTag()).getStyle());
//                        price_tv.setText("¥" + styleList.get((int) view.getTag()).getPrice() * pm.getNum());
//                        index = (int) view.getTag();
//                        clearOther(tvList);
//                        view.setTextColor(mContext.getResources().getColor(R.color.app_color));
//                        view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle_pink));
//                    }
//                });
//                flowLayout.addView(view, lp);
//            }
//        }
//    }
//
//    private static void clearOther(List<TextView> tvList) {
//        for (int i = 0; i < tvList.size(); i++) {
//            tvList.get(i).setTextColor(Color.GRAY);
//            tvList.get(i).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.app_style_circle));
//        }
//    }
//
//
//}
