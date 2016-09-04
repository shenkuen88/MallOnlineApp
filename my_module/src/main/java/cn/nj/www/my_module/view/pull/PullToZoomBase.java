package cn.nj.www.my_module.view.pull;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by kai.wang on 3/17/14.
 */
abstract class PullToZoomBase extends PullBase {


    public PullToZoomBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * call this method when moving
     * @param distance moving distance
     * @param upwards is upwards?
     * @param release is release?
     */
    public abstract void move(int distance, boolean upwards, boolean release);



}
