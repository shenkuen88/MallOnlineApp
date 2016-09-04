//package com.nannong.live.main.view;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ViewFlipper;
//
//import java.util.List;
//
//import cn.nj.www.my_module.R;
//import cn.nj.www.my_module.bean.index.NoticeListBean;
//import cn.nj.www.my_module.constant.IntentCode;
//import cn.nj.www.my_module.main.base.CommonWebViewActivity;
//import cn.nj.www.my_module.tools.ToastUtil;
//
//public class PublicNoticeView extends LinearLayout {
//
//    private static final String TAG = "PUBLICNOTICEVIEW";
//    private Context mContext;
//    private ViewFlipper mViewFlipper;
//    private View mScrollTitleView;
//
//    public PublicNoticeView(Context context) {
//        super(context);
//        mContext = context;
//        init();
//    }
//
//    public PublicNoticeView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        init();
//    }
//
//    private void init() {
//        bindLinearLayout();
//    }
//
//    /**
//     * 初始化自定义的布局
//     */
//    private void bindLinearLayout() {
//        mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.scrollnoticebar, null);
//        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//        addView(mScrollTitleView, params);
//        mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.id_scrollNoticeTitle);
//        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
//        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
//        mViewFlipper.startFlipping();
//    }
//
//    /**
//     * 网络请求内容后进行适配
//     */
//    public void bindNotices(final List<NoticeListBean> noticeList) {
//        mViewFlipper.removeAllViews();
//        int i = 0;
//        while (i < noticeList.size()) {
//            RelativeLayout showView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.notice_item_view, null);
//            TextView btv = (TextView) showView.findViewById(R.id.b_tv);
//            btv.setText(noticeList.get(i).getTitle());
//            final NoticeListBean bean = noticeList.get(i);
//            LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//            mViewFlipper.addView(showView, layoutParams);
//            showView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (bean.getType().equals("1") || bean.getType().equals("2")) {
//                        //打开webView
//                        Intent intent = new Intent(mContext, CommonWebViewActivity.class);
//                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE,bean.getTitle());
//                        intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, bean.getLink());
//                        mContext.startActivity(intent);
//                    } else if (bean.getType().equals("3")) {
//                        ToastUtil.makeText(mContext, "进入内容详情页 " + bean.getTitle());
//                    }
//                }
//            });
//            i++;
//        }
//    }
//
//}
