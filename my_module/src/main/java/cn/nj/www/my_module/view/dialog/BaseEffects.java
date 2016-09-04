package cn.nj.www.my_module.view.dialog;


import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

public abstract  class BaseEffects {

    private static final int DURATION = 1 * 500;

    protected long mDuration =DURATION ;

    private AnimatorSet mAnimatorSet;

    {
        mAnimatorSet = new AnimatorSet();
    }

    protected abstract void setupAnimation(View view);

    public void start(View view) {
        reset(view);
        setupAnimation(view);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListener()
        {

            @Override
            public void onAnimationStart(Animator arg0)
            {
                Log.i("info", "onAnimationStart");
            }

            @Override
            public void onAnimationRepeat(Animator arg0)
            {
                Log.i("info", "onAnimationRepeat");
            }

            @Override
            public void onAnimationEnd(Animator arg0)
            {
                Log.i("info", "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator arg0)
            {
                Log.i("info", "onAnimationCancel");
            }
        });
    }
    public void reset(View view) {
        ViewHelper.setPivotX(view, view.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight() / 2.0f);
    }


    public AnimatorSet getAnimatorSet() {
        return mAnimatorSet;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

}
