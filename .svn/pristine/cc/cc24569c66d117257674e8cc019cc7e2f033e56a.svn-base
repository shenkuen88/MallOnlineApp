package com.nannong.mall.activity.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.fragment.index.OnlineShopIndexFragment;
import com.nannong.mall.fragment.index.ShopSearchListFragment;
import com.nannong.mall.response.mine.LoginResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.StatusBarUtil;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;
import cn.nj.www.my_module.view.NoScrollViewPager;
import de.greenrobot.event.EventBus;

public class OnlineShopActivity extends BaseActivity
{
    private NoScrollViewPager my_viewpager;


    private TextView tvIndex, tvAllProduct;

    private View  topView;

    private View finish_iv;

    private String shopId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StatusBarUtil.myStatusBar(this);
        setContentView(R.layout.activity_online_shop);
        shopId = getIntent().getStringExtra(IntentCode.shopId);

        initAll();
    }


    @Override
    public void initView()
    {
        my_viewpager = V.f(this, R.id.my_viewpager);
        topView = V.f(this, R.id.topView);
        tvIndex = V.f(this, R.id.tvIndex);
        tvAllProduct = V.f(this, R.id.tvAllProduct);
        finish_iv = V.f(this, R.id.finish_iv);
        finish_iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        findViewById(R.id.et_search).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                allProductMethod();
            }
        });
        topView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                allProductMethod();
            }
        });
    }

    @Override
    public void initViewData()
    {
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        my_viewpager.setAdapter(fragmentPagerAdapter);
        my_viewpager.setOffscreenPageLimit(2);
    }

    @Override
    public void initEvent()
    {
        tvIndex.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                indexMethod();
            }
        });
        tvAllProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                allProductMethod();
            }
        });
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (tag.equals(NotiTag.TAG_BACK_ACTIVITY))
            {
                indexMethod();
            }
        }


        if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName()))
            {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode()))
                    {
                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
                    }
                    else
                    {
                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
        }
    }

    private Fragment myfragmet;

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {

        public MyFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    myfragmet = OnlineShopIndexFragment.getInstance(shopId);
                    break;
                case 1:
                    myfragmet = ShopSearchListFragment.getInstance(shopId);
                    break;

            }
            return myfragmet;
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    }

    public void indexMethod()
    {
        topView.setVisibility(View.VISIBLE);
        my_viewpager.setCurrentItem(0);
        tvIndex.setTextColor(getResources().getColor(R.color.app_color));
        tvAllProduct.setTextColor(getResources().getColor(R.color.register_black_text));
    }


    public void allProductMethod()
    {
        topView.setVisibility(View.GONE);
        tvIndex.setTextColor(getResources().getColor(R.color.register_black_text));
        tvAllProduct.setTextColor(getResources().getColor(R.color.app_color));
        my_viewpager.setCurrentItem(1);
    }


}
