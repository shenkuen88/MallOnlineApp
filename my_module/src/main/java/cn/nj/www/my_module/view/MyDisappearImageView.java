package cn.nj.www.my_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class MyDisappearImageView extends ImageView
{
    public MyDisappearImageView(Context context) {
        super(context);
    }

    public MyDisappearImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyDisappearImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

}
