package cn.nj.www.my_module.main.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import de.greenrobot.event.EventBus;

public class HeadView
{
    private ViewGroup mViewGroup;

    private ImageView ivBack;

    public LinearLayout getLeftView() {
        return llBack;
    }

    private LinearLayout llBack;

    private TextView tvTitle;

    private RelativeLayout rlRight;

    private TextView tvRight;

    private ImageView ivRight;

    public HeadView(ViewGroup layout)
    {

        mViewGroup = layout;
        ivBack = (ImageView) mViewGroup.findViewById(R.id.top_view_close_iv);
        llBack = (LinearLayout) mViewGroup.findViewById(R.id.top_view_close_ll);
        tvTitle = (TextView) mViewGroup.findViewById(R.id.top_view_title_tv);
        rlRight = (RelativeLayout) mViewGroup.findViewById(R.id.top_view_right_rl);
        tvRight = (TextView) mViewGroup.findViewById(R.id.top_view_right_tv);
        ivRight = (ImageView) mViewGroup.findViewById(R.id.top_view_right_iv);
        llBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CLOSE_ACTIVITY));
            }
        });
        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_DO_RIGHT));
            }
        });
    }

    /**
     * 设置左边按钮图片
     */
    public void setLeftImage(int resid)
    {
        ivBack.setImageResource(resid);
    }

    /**
     * 设置右边按钮图片
     */
    public void setRightImage(int resid)
    {
        tvRight.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resid);
    }

    /**
     * 设置右边按钮文字
     */
    public void setRightText(String text)
    {
        tvRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.GONE);
        tvRight.setText(text);
    }

    /**
     * 设置标题
     */
    public void setTitleText(String text)
    {
        tvTitle.setText(text);
    }

    /**
     * 隐藏右边
     */
    public void setHiddenRight()
    {
        rlRight.setVisibility(View.GONE);
    }

    /**
     * 隐藏左边
     */
    public void setHiddenLeft()
    {
        llBack.setVisibility(View.GONE);
    }

    public TextView getTvTitle()
    {
        return tvTitle;
    }

    public ImageView getRightView()
    {
        return ivRight;
    }
    public TextView getRightTextView()
    {
        return tvRight;
    }
}
