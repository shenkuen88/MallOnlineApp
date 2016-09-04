package cn.nj.www.my_module.view.pull;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import cn.nj.www.my_module.R;


/**
 * Created by kai.wang on 3/17/14.
 */
public class PullToZoomLayout extends PullToZoomBase {

    private final int minHeight;
    private View headerView;
    private int headerHeight;
    private int maxHeight;
    private int currentHeight;

    private boolean zooming = false;
    private float downY = 0.0f;

    public PullToZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.pull);
        int layout = a.getResourceId(R.styleable.pull_mheader, 0);
        maxHeight = a.getLayoutDimension(R.styleable.pull_maxHeaderHeight, 0);
        minHeight = a.getLayoutDimension(R.styleable.pull_minHeaderHeight, 0);
        a.recycle();
        if (layout == 0) {
            throw new RuntimeException("PullToZoomLayout haven't header view.");
        }
        if (maxHeight == 0) {
            throw new RuntimeException("PullToZoomLayout maxHeight must be set.");
        }

        headerView = LayoutInflater.from(context).inflate(layout, null);
        addHeaderView(headerView, minHeight);
        headerHeight = getHeaderHeight();
        currentHeight = headerHeight;
        headerShowing = true;
    }


    @Override
    public void move(int distance, boolean upwards, boolean release) {
        // illegal distance
        if (distance > 30) return;

        if (release) {
            // zooming
            if (headerView.getHeight() > headerHeight) {
                AnimUtil.collapse(headerView, headerHeight);
                currentHeight = headerHeight;
            }
            zooming = false;
            return;
        } else {
            zooming = true;
            resizeHeader(distance, upwards);
        }
    }

    private void resizeHeader(int distance, boolean upwards) {
        distance = (int) (distance / 1.5f);
        // zoom out
        if (upwards && headerView.getHeight() > headerHeight) {
            int tmpHeight = currentHeight - distance;
            if (tmpHeight < headerHeight) {
                tmpHeight = headerHeight;
            }
            currentHeight = tmpHeight;
            resizeHeight(currentHeight);

        }
        if (!upwards && headerView.getHeight() >= headerHeight) {
            // zoom in
            currentHeight += distance;
            if (currentHeight > maxHeight) {
                currentHeight = maxHeight;
            }
            resizeHeight(currentHeight);
        }

    }

    private void resizeHeight(int resizeHeight) {
        LayoutParams params = (LayoutParams) headerView.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT, resizeHeight);
        } else {
            params.height = resizeHeight;
        }
        headerView.setLayoutParams(params);
    }

    protected boolean isHeaderZooming() {
        return zooming;
    }

    private boolean isDown = false;
    private boolean isMove = false;
    private int mLastMotionY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        boolean result = false;
//        if(contentView == null){
//            return true;
//        }
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mLastMotionY = y;
            downY = ev.getRawY();
            isDown = true;
        } else if (action == MotionEvent.ACTION_MOVE) {

           if (Math.abs(downY - ev.getRawY())>10){
               isMove = true;
           }
            if (headerShowing) {
                result = true;
            }
            // deltaY > 0 是向下运动,< 0是向上运动
            int deltaY = y - mLastMotionY;
            if (deltaY>0){
                if (iMove!=null){
                    iMove.moveDistance(true,deltaY);
                }
            }
            if (deltaY<0){
                if (iMove!=null){
                    iMove.moveDistance(false,deltaY);
                }
            }
        }else if (action == MotionEvent.ACTION_UP){
            if (iHandUp!=null){
                iHandUp.leaveHand(false,(y- firstPointY));
                firstPointY = 0;
            }
        }


        if (isMove && isDown){
            isDown = false;
            isMove = false;
            return true;
        }else {
            return false;
        }
//        return false;//事件传递给子控件
    }
    private  int firstPointY = 0 ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            firstPointY = y;
            return true;
        } else if (action == MotionEvent.ACTION_MOVE) {
            computeTravel(event, false);
            downY = event.getRawY();
            int deltaY = y - mLastMotionY;
            if (iMove!=null){
                iMove.moveDistance(true,deltaY);
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            computeTravel(event, true);
            if (iHandUp!=null){
                iHandUp.leaveHand(false,(y- firstPointY));
                firstPointY = 0;
            }
        }
        return super.onTouchEvent(event);
    }

    private IMove iMove;

    public void setIMove(IMove iMove) {
        this.iMove = iMove;
    }

    public interface IMove {
        void moveDistance(boolean isDown, int dis);
    }
    private IHandUp iHandUp;

    public void setIIHandUp(IHandUp iHandUp) {
        this.iHandUp = iHandUp;
    }

    public interface IHandUp {
        void leaveHand(boolean isCancel, int distance);//distance 移动的距离
    }

    /**
     * 计算并调整header显示的高度
     *
     * @param ev
     * @param actionUp
     */
    private void computeTravel(MotionEvent ev, boolean actionUp) {
        float movingY = ev.getRawY();
        int travel = (int) (downY - movingY);
        boolean up = travel > 0;
        travel = Math.abs(travel);

        move(travel, up, actionUp);
    }

}
