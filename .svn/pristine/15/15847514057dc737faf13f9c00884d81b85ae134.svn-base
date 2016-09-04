package cn.nj.www.my_module.view.dialog;//package text.huqing.com.model.view.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.Random;
//
//import text.huqing.com.model.R;
//import Constants;
//import MyGifView;
//
///**
// * Created by lee on 2014/7/30.
// */
//public class NiftyDialogBuilder extends Dialog implements DialogInterface
//{
//
//    private Effectstype type = null;
//
//    private LinearLayout mLinearLayoutView;
//
//    private RelativeLayout mRelativeLayoutView;
//
//    private FrameLayout mFrameLayoutCustomView;
//
//    private View mDialogView;
//
//    private int mDuration = -1;
//
//    private static int mOrientation = 1;
//
//    private boolean isCancelable = true;
//
//    private volatile static NiftyDialogBuilder instance;
//
//    private Integer[] gifs = new Integer[]{R.drawable.expression_one, R.drawable.expression_two, R.drawable.expression_three};
//
//    private Integer[] tips = new Integer[]{R.string.expression_tip_one, R.string.expression_tip_two, R.string.expression_tip_three};
//
//    public NiftyDialogBuilder(Context context)
//    {
//        super(context);
//        init(context);
//
//    }
//
//    public NiftyDialogBuilder(Context context, int theme)
//    {
//        super(context, theme);
//        init(context);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    public static NiftyDialogBuilder getInstance(Context context)
//    {
//
//        int ort = context.getResources().getConfiguration().orientation;
//        if (mOrientation != ort)
//        {
//            mOrientation = ort;
//            instance = null;
//        }
//
//        if (instance == null || ((Activity) context).isFinishing())
//        {
//            synchronized (NiftyDialogBuilder.class)
//            {
//                if (instance == null)
//                {
//                    instance = new NiftyDialogBuilder(context, R.style.main_dialog);
//                }
//            }
//        }
//        return instance;
//
//    }
//
//    private void init(Context context)
//    {
//
//        mDialogView = View.inflate(context, R.layout.dialog_layout, null);
//
//        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
//        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
//        mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.customPanel);
//
//        setContentView(mDialogView);
//
//        this.setOnShowListener(new OnShowListener()
//        {
//            @Override
//            public void onShow(DialogInterface dialogInterface)
//            {
//
//                mLinearLayoutView.setVisibility(View.VISIBLE);
//                if (type == null)
//                {
//                    type = Effectstype.Newspager;
//                }
//                start(type);
//
//            }
//        });
//        mRelativeLayoutView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                if (isCancelable)
//                {
//                    dismiss();
//                }
//            }
//        });
//    }
//
//    public NiftyDialogBuilder withDuration(int duration)
//    {
//        this.mDuration = duration;
//        return this;
//    }
//
//    public NiftyDialogBuilder withEffect(Effectstype type)
//    {
//        this.type = type;
//        return this;
//    }
//
//    public NiftyDialogBuilder setCustomView(Context context, String score, int tag)
//    {
//        Random random = new Random();
//        int num = random.nextInt(gifs.length);
//        View customView = View.inflate(context, R.layout.custom_view, null);
//        MyGifView myGif = (MyGifView) customView.findViewById(R.id.money_gif_view);
//        TextView tvOne = (TextView) customView.findViewById(R.id.expression_tip_one_tv);
//        TextView tvTwo = (TextView) customView.findViewById(R.id.expression_tip_two_tv);
//        TextView tvThree = (TextView) customView.findViewById(R.id.expression_tip_three_tv);
//        tvTwo.setVisibility(View.GONE);
//        tvThree.setVisibility(View.GONE);
//        switch (tag)
//        {
//            case Constants.EXPRESSION_SUCCESS:
//                myGif.setMovieResource(gifs[num]);
//                tvOne.setText(context.getResources().getString(tips[num]));
//                tvThree.setVisibility(View.VISIBLE);
//                tvThree.setText(context.getResources().getString(R.string.expression_score, score));
//                break;
//            case Constants.EXPRESSION_ERROR:
//                myGif.setMovieResource(R.drawable.expression_four);
//                tvOne.setText(context.getResources().getString(R.string.expression_tip_error_one));
//                tvTwo.setVisibility(View.VISIBLE);
//                tvTwo.setText(context.getResources().getString(R.string.expression_tip_error_two));
//                break;
//            case Constants.EXPRESSION_PASS:
//                myGif.setMovieResource(R.drawable.expression_five);
//                tvOne.setText(context.getResources().getString(R.string.expression_tip_pass_one));
//                tvTwo.setVisibility(View.VISIBLE);
//                tvTwo.setText(context.getResources().getString(R.string.expression_tip_pass_two));
//                break;
//        }
//        if (mFrameLayoutCustomView.getChildCount() > 0)
//        {
//            mFrameLayoutCustomView.removeAllViews();
//        }
//        mFrameLayoutCustomView.addView(customView);
//        return this;
//    }
//
//    public NiftyDialogBuilder setCustomView(View view, Context context)
//    {
//        if (mFrameLayoutCustomView.getChildCount() > 0)
//        {
//            mFrameLayoutCustomView.removeAllViews();
//        }
//        mFrameLayoutCustomView.addView(view);
//
//        return this;
//    }
//
//    public NiftyDialogBuilder isCancelableOnTouchOutside(boolean cancelable)
//    {
//        this.isCancelable = cancelable;
//        this.setCanceledOnTouchOutside(cancelable);
//        return this;
//    }
//
//    public NiftyDialogBuilder isCancelable(boolean cancelable)
//    {
//        this.isCancelable = cancelable;
//        this.setCancelable(cancelable);
//        return this;
//    }
//
//    @Override
//    public void show()
//    {
//
//        super.show();
//    }
//
//    private void start(Effectstype type)
//    {
//        BaseEffects animator = type.getAnimator();
//        if (mDuration != -1)
//        {
//            animator.setDuration(Math.abs(mDuration));
//        }
//        animator.start(mRelativeLayoutView);
//    }
//
//    @Override
//    public void dismiss()
//    {
//        super.dismiss();
//    }
//}
