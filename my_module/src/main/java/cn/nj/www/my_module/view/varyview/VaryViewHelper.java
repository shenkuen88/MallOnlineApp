package cn.nj.www.my_module.view.varyview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.view.varyview.widget.ProgressWheel;


/**
 * 作者： corer   时间： 2015/10/18.
 * 功能：帮助切换错误，数据为空，正在加载的页面
 * 修改：
 */
public class VaryViewHelper
{
    /**
     * 切换不同视图的帮助类
     */
    public OverlapViewHelper mViewHelper;

    /**
     * 错误页面
     */
    public View mErrorView;

    /**
     * 正在加载页面
     */
    public View mLoadingView;

    /**
     * 数据为空的页面
     */
    public View mEmptyView;

    /**
     * 正在加载页面的进度环
     */
    public ProgressWheel mLoadingProgress;


    public VaryViewHelper(View view)
    {
        this(new OverlapViewHelper(view));
    }

    public VaryViewHelper(OverlapViewHelper helper)
    {
        this.mViewHelper = helper;
    }


    public void setUpEmptyView(View view)
    {
        mEmptyView = view;
        mEmptyView.setClickable(true);
    }

    public void setUpErrorView(View view, View.OnClickListener listener)
    {
        mErrorView = view;
        mErrorView.setClickable(true);

        View btn = view.findViewById(R.id.vv_error_refresh);
        if (btn != null)
        {
            btn.setOnClickListener(listener);
        }
    }

    public void setUpLoadingView(View view)
    {
        mLoadingView = view;
        mLoadingView.setClickable(true);
        mLoadingProgress = (ProgressWheel) view.findViewById(R.id.vv_loading_progress);
    }

    public void showEmptyView()
    {
        mViewHelper.showCaseLayout(mEmptyView);
        stopProgressLoading();
    }

    public void showErrorView()
    {
        mViewHelper.showCaseLayout(mErrorView);
        stopProgressLoading();
    }

    public void showLoadingView()
    {
        mViewHelper.showCaseLayout(mLoadingView);
        startProgressLoading();
    }

    public void showDataView()
    {
        mViewHelper.restoreLayout();
        stopProgressLoading();
    }


    private void stopProgressLoading()
    {
        if (mLoadingProgress != null && mLoadingProgress.isSpinning())
        {
            mLoadingProgress.stopSpinning();
        }
    }

    private void startProgressLoading()
    {
        if (mLoadingProgress != null && !mLoadingProgress.isSpinning())
        {
            mLoadingProgress.spin();
        }
    }

    public void releaseVaryView()
    {
        mErrorView = null;
        mLoadingView = null;
        mEmptyView = null;
    }

    public static class Builder
    {
        private View mErrorView;

        private View mLoadingView;

        private View mEmptyView;

        private View mDataView;

        private View.OnClickListener mRefreshListener;

        private int imgResource;

        private String emptyStr;

        public Builder setErrorView(View errorView)
        {
            mErrorView = errorView;
            return this;
        }

        public Builder setLoadingView(View loadingView)
        {
            mLoadingView = loadingView;
            return this;
        }

        public Builder setEmptyView(int imgResource, String emptyStr)
        {
            this.imgResource = imgResource;
            this.emptyStr = emptyStr;
            return this;
        }

        public Builder setDataView(View dataView)
        {
            mDataView = dataView;
            return this;
        }

        public Builder setRefreshListener(View.OnClickListener refreshListener)
        {
            mRefreshListener = refreshListener;
            return this;
        }

        public VaryViewHelper build(Context context)
        {
            VaryViewHelper helper = new VaryViewHelper(mDataView);
            //没有数据时
            mEmptyView = LayoutInflater.from(context).inflate(R.layout.layout_emptyview, null);
            ImageView iv = (ImageView) mEmptyView.findViewById(R.id.img_iv);
            TextView tv = (TextView) mEmptyView.findViewById(R.id.tips_tv);
            if (GeneralUtils.isNotNullOrZeroLenght(emptyStr))
            {
                GeneralUtils.setImageViewWithUrl(context, "", iv, imgResource);
                tv.setText(emptyStr);
            }
            helper.setUpEmptyView(mEmptyView);
            //错误时，提供刷新
            if (mRefreshListener != null)
            {
                mErrorView = LayoutInflater.from(context).inflate(R.layout.layout_errorview, null);
                helper.setUpErrorView(mErrorView, mRefreshListener);
            }
            //加载
            View loadingView = LayoutInflater.from(context).inflate(R.layout.layout_loadingview, null);
            helper.setUpLoadingView(loadingView);
            return helper;
        }
    }

}
