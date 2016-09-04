package cn.nj.www.my_module.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView1 extends ScrollView {


    public MyScrollView1(Context context) {
        super(context);
    }

    public MyScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 解决 由于子控件的大小导致ScrollView滚动到底部的问题
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

}
