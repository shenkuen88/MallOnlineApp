package com.nannong.mall.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.AddReceiveAddressResponse;
import com.nannong.mall.response.mine.LoginResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.switchbn.SwitchButton;
import cn.nj.www.my_module.view.wheel.cascade.activity.LocationBaseActivity;
import cn.nj.www.my_module.view.wheel.widget.OnWheelChangedListener;
import cn.nj.www.my_module.view.wheel.widget.WheelView;
import cn.nj.www.my_module.view.wheel.widget.adapters.ArrayWheelAdapter;


public class LocationActivity extends LocationBaseActivity implements OnClickListener, OnWheelChangedListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private EditText nameEt;
    private EditText phoneEt;
    private TextView locationEt;
    private EditText addressEt;
    /**
     * 默认收货地址按钮
     */
    private SwitchButton defaultBn;
    private boolean isDefault;
    private LinearLayout locationll;
    private LinearLayout bottomView;
    private TextView cancelBn;
    private TextView comBn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_location);
        initAll();
        setUpViews();
        setUpListener();
        setUpData();
    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag)) {
                UserServiceImpl.instance().addReceiveAddress("江苏省", "南京市", "江宁区", "竹山路", "姓名",
                        "15211111111", "0", AddReceiveAddressResponse.class.getName());

//                if (GeneralUtils.isNotNullOrZeroLenght(nameEt.getText().toString())){
//                    if (GeneralUtils.isNotNullOrZeroLenght(phoneEt.getText().toString())){
//                        if (GeneralUtils.isNotNullOrZeroLenght(locationEt.getText().toString())){
//                            if (GeneralUtils.isNotNullOrZeroLenght(addressEt.getText().toString())){
//                                //都不为空时上传数据
//
//
//                            }else {
//                                ToastUtil.makeText(mContext,"请输入详细地址");
//                            }
//                        }else {
//                            ToastUtil.makeText(mContext,"请输入所在地区");
//                        }
//                    }else {
//                        ToastUtil.makeText(mContext,"请输入手机号码");
//                    }
//                }else {
//                    ToastUtil.makeText(mContext,"请输入收货人姓名");
//                }
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(LoginResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
                    } else {
                        ErrorCode.doCode(mContext, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(LocationActivity.this, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        mViewProvince.setCurrentItem(2);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_confirm:
//                locationEt.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName);
//                bottomView.setVisibility(View.GONE);
//                break;
//            case R.id.btn_cancel:
//                bottomView.setVisibility(View.GONE);
//                break;
//            case R.id.location_ll:
//                bottomView.setVisibility(View.VISIBLE);
//                break;
//            default:
//                break;
//        }
    }


    @Override
    public void initView() {
        initTitle();
        locationll = (LinearLayout) findViewById(R.id.location_ll);
        locationll.setOnClickListener(this);
        nameEt = (EditText) findViewById(R.id.name_et);
        phoneEt = (EditText) findViewById(R.id.phone_et);
        locationEt = (TextView) findViewById(R.id.location_et);
        addressEt = (EditText) findViewById(R.id.address_et);
        defaultBn = (SwitchButton) findViewById(R.id.default_bn);
        defaultBn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDefault = true;
                }
            }
        });

        bottomView = (LinearLayout) findViewById(R.id.bottom_ll);
        cancelBn = (TextView) findViewById(R.id.btn_cancel);
        comBn = (TextView) findViewById(R.id.btn_confirm);
        cancelBn.setOnClickListener(this);
        comBn.setOnClickListener(this);
    }

    private void initTitle() {
        View view = (View) findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("保存");
        headView.setTitleText("添加收货地址");
    }
}
