package cn.nj.www.my_module.main.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.tools.ACache;
import cn.nj.www.my_module.tools.BitmapUtil;
import cn.nj.www.my_module.tools.DisplayUtil;
import de.greenrobot.event.EventBus;

/**
 * 
 * <基础Fragment>
 * <功能详细描述>
 * 
 * @author  hq
 * @version  [版本�? Apr 21, 2014]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseFragment extends Fragment
{

    public ACache mCache;
    public abstract void netResponse(BaseResponse event);
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mCache = ACache.get(getActivity());
    }
    private ViewGroup root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void initAll(View view){
        initView(view);
        initViewData();
        initEvent();
    }
    public abstract void initView(View view);

    public abstract void initViewData();

    public abstract void initEvent();

    public void onEvent(BaseResponse event)
    {
    }
    
    public void onEventMainThread(BaseResponse event)
    {
        netResponse(event);
    }
    
    public void onEventBackgroundThread(BaseResponse event)
    {
    }
    
    public void onEventAsync(BaseResponse event)
    {
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BitmapUtil.destoryViewImage(root);
    }
    protected void setImmerseLayout(View view)
    {
        if (!DisplayUtil.checkDeviceHasNavigationBar(getActivity()))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                view.setPadding(0, BaseApplication.statusBarHeight, 0, 0);
            }
        }
    }
}
