package cn.nj.www.my_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyInfoScrollView extends ScrollView {
    private boolean isBottom;

    private int top;

    private boolean isDownBottom;

    private boolean isUpTop;

    public static int page;

    public MyInfoScrollView(Context context) {
        super(context);
    }

    public MyInfoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyInfoScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean isDown = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            isDown = true;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (isDown) {
                return false;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
