package cn.nj.www.my_module.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class MyPopWindow
{

    private PopupWindow mWindow;

    private Context mContext;

    public MyPopWindow(Context context, int width, int height, boolean isOutTouch)
    {
        mContext = context;
        mWindow = new PopupWindow(new View(context), width, height);
        if (isOutTouch)
        {
            mWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        mWindow.isOutsideTouchable();
        mWindow.setFocusable(true);
        mWindow.setOnDismissListener(new OnDismissListener()
        {

            @Override
            public void onDismiss()
            {
            }
        });
//        test();
    }

    public PopupWindow getPopWindow()
    {
        return mWindow;
    }

    public void test()
    {
        if (mWindow.getContentView().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        } else if (mWindow.getContentView().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        }
    }

    /**
     * 设置显示视图
     */
    public void setContentView(View contentView)
    {
        mWindow.setContentView(contentView);
    }

    /**
     * 设置显示视图
     */
    public void setContentView(int layoutID)
    {
        View contentView = LayoutInflater.from(mContext)
                .inflate(layoutID, null);
        mWindow.setContentView(contentView);
    }

    /**
     * 设置显示动画
     */
    public void setAnimationStyle(int animationStyleID)
    {
        mWindow.setAnimationStyle(animationStyleID);
    }

    /**
     * 显示popupwindow 参数：parent 显示在哪个窗体之上 gravity 显示在窗体的哪个部位 Gravity类 x y
     * 相对目前位置的偏移量
     */
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        mWindow.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 显示popupwindow在anchor的下方
     */
    public void showAsDropDown(View anchor, int xoff, int yoff)
    {
        mWindow.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 隐藏popwindow
     */
    public void dismiss()
    {
        if (mWindow.isShowing())
        {
            mWindow.dismiss();
        }
    }

    /**
     * popwindow是否显示
     */
    public boolean isShowing()
    {
        return mWindow.isShowing();
    }

    /**
     * 设置popwindow的背景
     */
    public void setBackground(Drawable d)
    {
        mWindow.setBackgroundDrawable(d);
    }

    /**
     * 得到子view
     */
    public View getContextView()
    {
        return mWindow.getContentView();
    }

    /**
     * 设置点击popwindow之外 窗口是否消失
     */
    public void setOutsideTouchable(boolean touchable)
    {
        mWindow.setOutsideTouchable(touchable);
    }

    /**
     * @param width
     */
    public void setWidth(int width)
    {
        mWindow.setWidth(width);
    }

    /**
     * @param height
     */
    public void setHeight(int height)
    {
        mWindow.setHeight(height);
    }

    public View findViewById(int id)
    {
        return mWindow.getContentView().findViewById(id);
    }
}
