package com.nannong.mall.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nannong.mall.R;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.fragment.CommunityFragment;
import com.nannong.mall.fragment.FriendFragment;
import com.nannong.mall.fragment.IndexFragment;
import com.nannong.mall.fragment.MineFragment;
import com.nannong.mall.fragment.cart.CartFragment;
import com.nannong.mall.response.index.AppInitInfoListBean;
import com.nannong.mall.response.index.GetUploadUrlResponse;
import com.nannong.mall.response.index.InitAppResponse;
import com.nannong.mall.response.mine.LoginResponse;

import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.UserBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.StatusBarUtil;
import cn.nj.www.my_module.tools.StringEncrypt;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.NoScrollViewPager;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {
    private NoScrollViewPager my_viewpager;

    private ImageView shouye_pic, friend_pic, user_pic, shequ_pic, ivCart;

    private UserBean user;

    private LocationClient mLocationClient;//定位SDK的核心类

    private long downTime;

    private InitAppResponse mInitAppResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.myStatusBar(this);
        setContentView(R.layout.activity_main);
        initWindow();
        initAll();
        doLogin();
        checkAndStartLocation();

    }


    private void doLogin() {

        if (GeneralUtils.isNotNullOrZeroLenght(Global.getPassword()) && GeneralUtils.isNotNullOrZeroLenght(Global.getLoginName())) {
//            if(EMClient.getInstance().isLoggedInBefore()){
//                //enter to main activity directly if you logged in before.
//                UserServiceImpl.instance().login(Global.getLoginName(), StringEncrypt.Encrypt(Global.getPassword()),
//                        LoginResponse.class.getName());
//                AppUtil.initEMConnectListener(this);
//            }else{
//                EMClient.getInstance().login(Global.getLoginName(), Global.getLoginName(), new EMCallBack() {
//
//                    @Override
//                    public void onSuccess() {
            UserServiceImpl.instance().login(Global.getLoginName(), StringEncrypt.Encrypt(Global.getPassword()),
                    LoginResponse.class.getName());
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        AppUtil.initEMConnectListener(MainActivity.this);
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                NetLoadingDialog.getInstance().dismissDialog();
//                                Toast.makeText(getApplicationContext(), "登录失败,请您重新尝试~", Toast.LENGTH_SHORT).show();
//                                Global.loginOut(mContext);
//                                startActivity(new Intent(mContext,LoginActy.class));
//                            }
//                        });
//                    }
//                });
//            }
        }
    }

    @Override
    public void initView() {
        my_viewpager = V.f(this, R.id.my_viewpager);
        shouye_pic = V.f(this, R.id.ivIndex);
        friend_pic = V.f(this, R.id.ivFriend);
        user_pic = V.f(this, R.id.ivMine);
        shequ_pic = V.f(this, R.id.ivZone);
        ivCart = V.f(this, R.id.ivCart);
    }

    @Override
    public void initViewData() {
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        my_viewpager.setAdapter(fragmentPagerAdapter);
        my_viewpager.setOffscreenPageLimit(4);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (tag.equals(NotiTag.TAG_LOCATION_SUCCESS)) {
                if (mLocationClient != null) {
                    mLocationClient.stop();
                }
                //应用初始化
                UserServiceImpl.instance().initAPP(InitAppResponse.class.getName());
            }
        }


        if (event instanceof NetResponseEvent) {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName())) {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                    } else {
                        Global.loginOut(mContext);
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT));
//                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT));
//                    ToastUtil.showError(this);
                }
            } else if (tag.equals(InitAppResponse.class.getName())) {
                //应用初始化，包含升级操作
                mInitAppResponse = GsonHelper.toType(result, InitAppResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    try {
                        if (Constants.SUCESS_CODE.equals(mInitAppResponse.getResultCode())) {
                            CMLog.e(Constants.HTTP_TAG, result);
                            SharePref.saveString(Constants.lastUpdateTime, mInitAppResponse.getServerTime());
                            List<AppInitInfoListBean> appInitInfoList = mInitAppResponse.getAppInitInfoList();
                            if (appInitInfoList.size() > 0) {
                                SharePref.saveString(Constants.lastInitInfo, result.trim());
                            }
                            if (GeneralUtils.isNotNullOrZeroLenght(mInitAppResponse.getCommunity().getName())) {
                                SharePref.saveString(Constants.initGetZoneName, mInitAppResponse.getCommunity().getName());
                                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_APP_INIT_SUCCESS));
                            }
                            InitAppResponse.AppVersionInfoBean versionBean = mInitAppResponse.getAppVersionInfo();
                            int versionCode = mInitAppResponse.getAppVersionInfo().getCodeVersion();
                            CMLog.e(Constants.HTTP_TAG, versionCode + "   " + Constants.VERSION_NAME);
//                            if (versionCode>Constants.VERSION_NAME) {
//                                UpdateUtils updateUtils = new UpdateUtils();
//                                CodeBean codeBean = new CodeBean(versionBean.getCodeVersion() + "",
//                                        versionBean.getShowVersion() + "",
//                                        versionBean.getDescription(),
//                                        versionBean.getUrl());
//                                updateUtils.showDialog(mContext, codeBean);
//                            }
                        } else {
                            ErrorCode.doCode(this, mInitAppResponse.getResultCode(), mInitAppResponse.getDesc());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showError(this);
                }
            } else if (tag.equals(GetUploadUrlResponse.class.getName())) {//上传图片的token
                GetUploadUrlResponse mGetUploadUrlResponse = GsonHelper.toType(result, GetUploadUrlResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mGetUploadUrlResponse.getResultCode())) {
                        Global.saveToken(mGetUploadUrlResponse.getUpToken());
                    }
                }
            }
        }
    }

    private Fragment myfragmet;

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    myfragmet = IndexFragment.getInstance();
                    break;
                case 1:
                    myfragmet = CommunityFragment.getInstance();
                    break;
                case 2:
                    myfragmet = FriendFragment.getInstance();
                    break;
                case 3:
                    myfragmet = new CartFragment();
                    break;
                case 4:
                    myfragmet = MineFragment.getInstance();
                    break;
            }
            return myfragmet;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    private void initTuBiao() {
        //初始化图标
        shouye_pic.setImageResource(R.mipmap.index_g);
        friend_pic.setImageResource(R.mipmap.friend_g);
        user_pic.setImageResource(R.mipmap.me_g);
        shequ_pic.setImageResource(R.mipmap.sq_g);
        ivCart.setImageResource(R.mipmap.cart_nol);
    }


    public void indexMethod(View view) {
        //跳转首页
        initTuBiao();
        shouye_pic.setImageResource(R.mipmap.index_p);
        my_viewpager.setCurrentItem(0);
    }

    public void friendMethod(View view) {
        //跳转朋友
        if (GeneralUtils.isLogin()) {
            initTuBiao();
            friend_pic.setImageResource(R.mipmap.friend_p);
            my_viewpager.setCurrentItem(2);
//            if(EMClient.getInstance().isLoggedInBefore()){
//                //enter to main activity directly if you logged in before.
//                AppUtil.initEMConnectListener(this);
//            }else{
//                EMClient.getInstance().login(Global.getLoginName(), Global.getPassword(), new EMCallBack() {
//
//                    @Override
//                    public void onSuccess() {
//                        AppUtil.initEMConnectListener(MainActivity.this);
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(getApplicationContext(), "登录失效,请您尝试重新登录!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(mContext, LoginActy.class));
//                            }
//                        });
//                    }
//                });
//            }
        } else {
            startActivity(new Intent(mContext, LoginActy.class));
        }

    }

    public void shequMethod(View view) {
        //社区
        initTuBiao();
        shequ_pic.setImageResource(R.mipmap.sq_p);
        my_viewpager.setCurrentItem(1);
        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_TO_COMMUNITY));

    }

    public void cartMethod(View view) {
        if (GeneralUtils.isLogin()) {
            //购物车
            initTuBiao();
            ivCart.setImageResource(R.mipmap.cart_sel);
            my_viewpager.setCurrentItem(3);
        } else {
            startActivity(new Intent(mContext, LoginActy.class));
        }
    }

    public void userMethod(View view) {
        //跳转个人中心
        initTuBiao();
        user_pic.setImageResource(R.mipmap.me_p);
        my_viewpager.setCurrentItem(4);
    }


    /**
     * 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getDownTime() - downTime <= 2000) {
                BaseApplication.getInstance().onTerminate();
            } else {
                ToastUtil.makeText(this, getResources().getString(R.string.exit_tips));
                downTime = event.getDownTime();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkAndStartLocation() {
        checkPermission(new CheckPermListener() {
                            @Override
                            public void superPermission() {
                                startLocation();
                            }
                        }, R.string.need_loaction_permission,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }

    private void startLocation() {
        if (((BaseApplication) getApplication()).mLocationClient != null) {
            mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
            InitLocation();//初始化
            mLocationClient.start();
        }
    }

    /**
     * 定位初始化设置
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onStop() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public static void getUpLoadImageUrl() {
        UserServiceImpl.instance().getUploadUrl(GetUploadUrlResponse.class.getName());
    }

}
