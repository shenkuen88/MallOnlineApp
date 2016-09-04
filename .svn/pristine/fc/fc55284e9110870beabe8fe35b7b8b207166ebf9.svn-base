package cn.nj.www.my_module.view.citylist;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.permission.PermissionActivity;
import cn.nj.www.my_module.view.citylist.adapter.CityListAdapter;
import cn.nj.www.my_module.view.citylist.adapter.ResultListAdapter;
import cn.nj.www.my_module.view.citylist.db.DBManager;
import cn.nj.www.my_module.view.citylist.model.City;
import cn.nj.www.my_module.view.citylist.model.LocateState;
import cn.nj.www.my_module.view.citylist.utils.StringUtils;
import cn.nj.www.my_module.view.citylist.utils.ToastUtils;
import cn.nj.www.my_module.view.citylist.view.SideLetterBar;
import de.greenrobot.event.EventBus;

/**
 * author zaaach on 2016/1/26.
 */
public class CityPickerActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_PICK_CITY = 2333;
    public static final String KEY_PICKED_CITY = "picked_city";

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;


    private LocationClient mLocationClient;//定位SDK的核心类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        initData();
        initView();
        checkPermission(new PermissionActivity.CheckPermListener() {
                            @Override
                            public void superPermission() {
                                InitLocation();
                                startLocation();
                            }
                        }, R.string.need_loaction_permission,
                Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            //
            if (tag.equals(NotiTag.TAG_LOCATION_CITY_SUCCESS)) {
//                ToastUtil.makeText(mContext, "定位成功");
                String city = Global.getCity();
                String district = Global.getDistrict();
                String location = StringUtils.extractLocation(city, district);
                mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                mLocationClient.stop();
            }
        }

    }

    private void startLocation() {
        mLocationClient.start();
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
//                chooseCity(name);
                Global.saveCity(name);
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHOOSE_CITY));
                finish();
            }

            @Override
            public void onLocateClick() {
                Log.e("onLocateClick", "重新定位...");
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
//                mLocationClient.startLocation();
                startLocation();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    public void initView() {
        View view = (View) findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setTitleText("选择城市");
        headView.setHiddenRight();

        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseCity(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);

        clearBtn.setOnClickListener(this);
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    private void chooseCity(String city) {
        ToastUtils.showToast(this, "点击的城市：" + city);
//        Intent data = new Intent();
//        data.putExtra(KEY_PICKED_CITY, city);
//        setResult(RESULT_OK, data);
//        finish();
    }

    @Override
    protected void onStop() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        super.onStop();
    }

    /**
     * 定位初始化设置
     */
    private void InitLocation() {
        mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_search_clear:
//                searchBox.setText("");
//                clearBtn.setVisibility(View.GONE);
//                emptyView.setVisibility(View.GONE);
//                mResultListView.setVisibility(View.GONE);
//                break;
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLocationClient.stopLocation();
    }
}
