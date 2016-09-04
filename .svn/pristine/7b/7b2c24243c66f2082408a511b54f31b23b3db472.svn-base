package com.nannong.mall.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.AddReceiveAddressResponse;
import com.nannong.mall.response.mine.AddressBean;
import com.nannong.mall.response.mine.EditReceiveAddressResponse;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.switchbn.SwitchButton;
import cn.nj.www.my_module.view.wheel.cascade.activity.LocationBaseActivity;
import cn.nj.www.my_module.view.wheel.widget.OnWheelChangedListener;
import cn.nj.www.my_module.view.wheel.widget.WheelView;
import cn.nj.www.my_module.view.wheel.widget.adapters.ArrayWheelAdapter;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/4.
 * 添加收货地址
 */
public class AddRecieveAddressActy extends LocationBaseActivity implements View.OnClickListener, OnWheelChangedListener
{
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
    private String flag = "0"; //0 新加 1 修改
    private int editPosition = -1;
    /**
     * 需要修改的地址实体
     */
    private AddressBean editBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_location);
        flag = getIntent().getStringExtra(IntentCode.EDIT_ADDRESS);
        editPosition = getIntent().getIntExtra(IntentCode.EDIT_ADDRESS_POSITION, -1);
        editBean = (AddressBean) getIntent().getSerializableExtra(IntentCode.EDIT_ADDRESS_BEAN_DETAIL);
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
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                CMLog.e(Constants.HTTP_TAG, flag + "  " + "1 编辑 0 增加");

                if (GeneralUtils.isNotNullOrZeroLenght(nameEt.getText().toString())) {
                    if (GeneralUtils.isNotNullOrZeroLenght(phoneEt.getText().toString())) {
                        if (GeneralUtils.isNotNullOrZeroLenght(locationEt.getText().toString())) {
                            if (GeneralUtils.isNotNullOrZeroLenght(addressEt.getText().toString())) {
                                String isDefault = "0";
                                if (defaultBn.isChecked()) {
                                    isDefault = "1";
                                }
                                NetLoadingDialog.getInstance().loading(mContext);
                                //都不为空时上传数据
                                if (flag.equals("1")) {
                                    UserServiceImpl.instance().editReceiveAddress(editBean.getRecordID(), mCurrentProviceName,
                                            mCurrentCityName, mCurrentDistrictName, addressEt.getText().toString(),
                                            nameEt.getText().toString(),
                                            phoneEt.getText().toString(), isDefault, EditReceiveAddressResponse.class.getName());
                                } else {
                                    UserServiceImpl.instance().addReceiveAddress(mCurrentProviceName,
                                            mCurrentCityName, mCurrentDistrictName, addressEt.getText().toString(),
                                            nameEt.getText().toString(),
                                            phoneEt.getText().toString(), isDefault, AddReceiveAddressResponse.class.getName());
                                }
                            } else {
                                ToastUtil.makeText(mContext, "请输入详细地址");
                            }
                        } else {
                            ToastUtil.makeText(mContext, "请输入所在地区");
                        }
                    } else {
                        ToastUtil.makeText(mContext, "请输入手机号码");
                    }
                } else {
                    ToastUtil.makeText(mContext, "请输入收货人姓名");
                }
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(AddReceiveAddressResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    AddReceiveAddressResponse mAddReceiveAddressResponse = GsonHelper.toType(result, AddReceiveAddressResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddReceiveAddressResponse.getResultCode())) {
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.NEW_SAVE_ADDRESS_RESULT, result));
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mAddReceiveAddressResponse.getResultCode(), mAddReceiveAddressResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            } else if (tag.equals(EditReceiveAddressResponse.class.getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    CMLog.e(Constants.HTTP_TAG, result);
                    EditReceiveAddressResponse mEditReceiveAddressResponse = GsonHelper.toType(result, EditReceiveAddressResponse.class);
                    if (Constants.SUCESS_CODE.equals(mEditReceiveAddressResponse.getResultCode())) {
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHANGE_RECEIVE_ADDRESS, editPosition, result));
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mEditReceiveAddressResponse.getResultCode(), mEditReceiveAddressResponse.getDesc());
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
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        mViewProvince.setCurrentItem(2);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
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
        switch (v.getId()) {
            case R.id.btn_confirm:
                locationEt.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName);
                bottomView.setVisibility(View.GONE);
                break;
            case R.id.btn_cancel:
                bottomView.setVisibility(View.GONE);
                break;
            case R.id.location_ll:
                bottomView.setVisibility(View.VISIBLE);
                hideKeyboardd();
                break;
            default:
                break;
        }
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

        if (editBean != null) {
            nameEt.setText(editBean.getDeliveryUser());
            phoneEt.setText(editBean.getPhone());
            locationEt.setText(editBean.getProvince() + " " + editBean.getCity() + " " + editBean.getArea());
            addressEt.setText(editBean.getDetail());
            if (editBean.getIsDefault().equals("1")) {//1-是；0-否
                defaultBn.setChecked(true);
            } else {
                defaultBn.setChecked(false);
            }
        }
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

    private void hideKeyboardd() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            try {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
