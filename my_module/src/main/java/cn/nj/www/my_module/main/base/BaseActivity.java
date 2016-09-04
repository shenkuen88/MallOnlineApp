package cn.nj.www.my_module.main.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.tools.ACache;
import cn.nj.www.my_module.tools.BitmapUtil;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.SystemBarTintManager;
import cn.nj.www.my_module.tools.permission.PermissionActivity;
import de.greenrobot.event.EventBus;

/**
 * <基础activity>
 *
 * @author wangtao
 */
public abstract class BaseActivity extends PermissionActivity {
    public Context mContext;

    private SystemBarTintManager tintManager;
    protected InputMethodManager inputManager;
    public ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        mCache = ACache.get(this);//网路返回值缓存
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        FileSystemManager.getCacheFilePathAll(mContext);
    }

    public void initAll(){
//        StatusBarCompat1.setStatusBarColor(this);
        initView();
        initViewData();
        initEvent();
    }
    public abstract void initView();

    public abstract void initViewData();

    public abstract void initEvent();
    public abstract void netResponse(BaseResponse event);

    //http://blog.csdn.net/eclothy/article/details/41912445
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void initWindow()
    {
        if (!DisplayUtil.checkDeviceHasNavigationBar(this))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.BLACK);
                tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.head_view_bg));
                tintManager.setStatusBarTintEnabled(true);
            }
            else
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                Window window = getWindow();
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        int statusBarHeight = DisplayUtil.getStatusBarHeight(this.getBaseContext());
        BaseApplication.statusBarHeight = statusBarHeight;
    }


    public void onEvent(BaseResponse event) {
    }

    public  void onEventMainThread(BaseResponse event) throws Exception {
        netResponse(event);
    }

    public void onEventBackgroundThread(BaseResponse event) {
    }

    public void onEventAsync(BaseResponse event) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.currentActivity = this.getClass().getName();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getInstance().deleteActivity(this);
        EventBus.getDefault().unregister(this);
        ViewGroup root = (ViewGroup) this.getWindow().getDecorView();  //获取本Activity下的获取最外层控件
        BitmapUtil.destoryViewImage(root);
        super.onDestroy();
    }
    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if ( getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if ( getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
