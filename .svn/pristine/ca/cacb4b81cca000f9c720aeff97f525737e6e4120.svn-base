package cn.nj.www.my_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView
{
    private boolean isBottom;
    
    private int top;
    
    private boolean isDownBottom;
    
    private boolean isUpTop;
    
    public static int pageView;
    
    public MyWebView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public MyWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    
    public MyWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        top = t;
        if (top != 0)
        {
            isUpTop = false;
        }
        if (t + getHeight() >= computeVerticalScrollRange())
        {
            isBottom = true;
        }
        else
        {
            isBottom = false;
            isDownBottom = false;
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                isDownBottom = true;
                isUpTop = true;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (pageView == 0)
                {
                    if (isDownBottom)
                    {
                        if (isBottom)
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        else
                        {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    else
                    {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                else
                {
                    if (isUpTop)
                    {
                        if (top <= 0)
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        else
                        {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    else
                    {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    
}
