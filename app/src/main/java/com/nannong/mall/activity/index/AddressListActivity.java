//package com.nannong.mall.activity.index;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.Poi;
//import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
//import com.hyphenate.chatuidemo.DemoApplication;
//import com.nannong.mall.R;
//import com.nannong.mall.tool.LocationService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.nj.www.my_module.adapter.CommonAdapter;
//import cn.nj.www.my_module.adapter.ViewHolder;
//import cn.nj.www.my_module.bean.BaseResponse;
//import cn.nj.www.my_module.bean.NetResponseEvent;
//import cn.nj.www.my_module.bean.NoticeEvent;
//import cn.nj.www.my_module.constant.Global;
//import cn.nj.www.my_module.constant.IntentCode;
//import cn.nj.www.my_module.constant.NotiTag;
//import cn.nj.www.my_module.main.base.BaseActivity;
//import cn.nj.www.my_module.main.base.BaseApplication;
//import cn.nj.www.my_module.main.base.HeadView;
//import cn.nj.www.my_module.tools.CMLog;
//import cn.nj.www.my_module.tools.GeneralUtils;
//import cn.nj.www.my_module.tools.NetLoadingDialog;
//
///***
// * 单点定位示例，用来展示基本的定位结果，配置在LocationService.java中
// * 默认配置也可以在LocationService中修改
// * 默认配置的内容自于开发者论坛中对开发者长期提出的疑问内容
// *
// * @author baidu
// */
//public class AddressListActivity extends BaseActivity
//{
//	private LocationService locationService;
//	private LinearLayout loadingll;
//	private CommonAdapter<LocationBean> adapter;
//	private ListView locationlv;
//	private TextView noLocationtv;
//	private String alreadySelected = "";
//	private CheckBox checkBox;
//	private BaiduMapPoiSearch poiSearch;
//
//	public String longitude = "0";
//	public String latitude = "0";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_address_lv);
//		alreadySelected = getIntent().getStringExtra(IntentCode.CHOOSE_LOCATION);
//		initTitle();
//		initView();
//		initAdapter();
//	}
//
//
//	@Override
//	public void onEventMainThread(BaseResponse event) throws Exception {
//		if (event instanceof NoticeEvent) {
//			String tag = ((NoticeEvent) event).getTag();
//			if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
//				finish();
//			}
//		}
//		if (event instanceof NetResponseEvent) {
//			NetLoadingDialog.getInstance().dismissDialog();
//			String tag = ((NetResponseEvent) event).getTag();
//			String result = ((NetResponseEvent) event).getResult();
//		}
//	}
//
//	private void initAdapter() {
//		locationlv.setAdapter(adapter = new CommonAdapter<LocationBean>(mContext, locationBeans, R.layout.app_location_choose_item) {
//			@Override
//			public void convert(ViewHolder helper, final LocationBean item) {
//				helper.setText(R.id.location_address_tv, item.getLocationName());
//				if (item.isCheck()) {
//					helper.getView(R.id.location_checkbox).setVisibility(View.VISIBLE);
//				} else {
//					helper.getView(R.id.location_checkbox).setVisibility(View.GONE);
//				}
//				helper.getView(R.id.choose_location_ll).setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent();
//						intent.putExtra(IntentCode.CHOOSE_LOCATION, item.getLocationName());
//						setResult(RESULT_OK, intent);
//						finish();
//					}
//				});
//			}
//		});
//	}
//
//	public void initView() {
//		checkBox = (CheckBox) findViewById(R.id.app_location_checkbox);
//		if (GeneralUtils.isNullOrZeroLenght(alreadySelected)) {
//			checkBox.setVisibility(View.VISIBLE);
//		} else {
//			checkBox.setVisibility(View.GONE);
//		}
//		loadingll = (LinearLayout) findViewById(R.id.app_location_loading_ll);
//		locationlv = (ListView) findViewById(R.id.app_location_lv);
//		noLocationtv = (TextView) findViewById(R.id.app_show_no_location_tv);
//		findViewById(R.id.app_location_no_rl).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra(IntentCode.CHOOSE_LOCATION, "");
//				setResult(RESULT_OK, intent);
//				finish();
//			}
//		});
//		noLocationtv.setOnClickListener(new View.OnClickListener() {
//											@Override
//											public void onClick(View v) {
//												Intent intent = new Intent();
//												intent.putExtra(IntentCode.CHOOSE_LOCATION, "");
//												setResult(RESULT_OK, intent);
//												finish();
//											}
//										}
//		);
//	}
//
//	@Override
//	public void initViewData()
//	{
//
//	}
//
//	@Override
//	public void initEvent()
//	{
//
//	}
//
//	@Override
//	public void netResponse(BaseResponse event)
//	{
//
//	}
//
//
//	private void initTitle() {
//		View view = findViewById(R.id.common_back);
//		HeadView headView = new HeadView((ViewGroup) view);
//		headView.setTitleText("所在位置");
//		headView.setLeftImage(R.mipmap.app_title_back);
//		headView.setHiddenRight();
//	}
//
//	/***
//	 * Stop location service
//	 */
//	@Override
//	protected void onStop() {
//		locationService.unregisterListener(mListener); //注销掉监听
//		locationService.stop(); //停止定位服务
//		super.onStop();
//	}
//
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		// -----------location config ------------
//		locationService = ((DemoApplication) getApplication());
//		locationService.registerListener(mListener);
//		//注册监听
//		locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//		locationService.start();// 定位SDK
//
//	}
//
//
//	/*****
//	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
//	 */
//	private BDLocationListener mListener = new BDLocationListener() {
//
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location==null)return;
//			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//				StringBuffer sb = new StringBuffer(256);
//				sb.append("time : ");
//				/**
//				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//				 */
//				sb.append(location.getTime());
//				sb.append("\nerror code : ");
//				sb.append(location.getLocType());
//				sb.append("\nlatitude : ");
//				Global.savelatitude(location.getLatitude() + "");
//				Global.savelangitude(location.getLongitude() + "");
//				sb.append(location.getLatitude());
//				sb.append("\nlontitude : ");
//				sb.append(location.getLongitude());
//				sb.append("\nradius : ");
//				sb.append(location.getRadius());
//				sb.append("\nCountryCode : ");
//				sb.append(location.getCountryCode());
//				sb.append("\nCountry : ");
//				sb.append(location.getCountry());
//				sb.append("\ncitycode : ");
//				sb.append(location.getCityCode());
//				sb.append("\ncity : ");
//				sb.append(location.getCity());
//				sb.append("\nDistrict : ");
//				sb.append(location.getDistrict());
//				sb.append("\nStreet : ");
//				sb.append(location.getStreet());
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//				sb.append("\nDescribe: ");
//				sb.append(location.getLocationDescribe());
//				sb.append("\nDirection(not all devices have value): ");
//				sb.append(location.getDirection());
//				sb.append("\nPoi: ");
//				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//					for (int i = 0; i < location.getPoiList().size(); i++) {
//						Poi poi = (Poi) location.getPoiList().get(i);
//						CMLog.e("poi", poi.getName());
//						if (!locationList.contains(poi.getName())) {
//							locationList.add(poi.getName());
//						}
//					}
//				}
//				locationBeans.clear();
//				for (int i = 0; i < locationList.size(); i++) {
//					if (locationList.get(i).toString().trim().equals(alreadySelected)) {
//						locationBeans.add(new LocationBean(locationList.get(i), true));
//					} else {
//						locationBeans.add(new LocationBean(locationList.get(i), false));
//					}
//				}
//				adapter.notifyDataSetChanged();
//
//				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//					sb.append("\nspeed : ");
//					sb.append(location.getSpeed());// 单位：km/h
//					sb.append("\nsatellite : ");
//					sb.append(location.getSatelliteNumber());
//					sb.append("\nheight : ");
//					sb.append(location.getAltitude());// 单位：米
//					sb.append("\ndescribe : ");
//					sb.append("gps定位成功");
//					loadingll.setVisibility(View.GONE);
//				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//					// 运营商信息
//					sb.append("\noperationers : ");
//					sb.append(location.getOperators());
//					sb.append("\ndescribe : ");
//					sb.append("网络定位成功");
//					loadingll.setVisibility(View.GONE);
//				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//					sb.append("\ndescribe : ");
//					sb.append("离线定位成功，离线定位结果也是有效的");
//					loadingll.setVisibility(View.GONE);
//				} else if (location.getLocType() == BDLocation.TypeServerError) {
//					sb.append("\ndescribe : ");
//					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//					sb.append("\ndescribe : ");
//					sb.append("网络不同导致定位失败，请检查网络是否通畅");
//				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//					sb.append("\ndescribe : ");
//					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//				}
//			}
//		}
//
//	};
//
//	private class LocationBean {
//		private String locationName;
//		private boolean isCheck;
//
//		public LocationBean(String locationName, boolean isCheck) {
//			this.locationName = locationName;
//			this.isCheck = isCheck;
//		}
//
//		public String getLocationName() {
//			return locationName;
//		}
//
//		public void setLocationName(String locationName) {
//			this.locationName = locationName;
//		}
//
//		public boolean isCheck() {
//			return isCheck;
//		}
//
//		public void setIsCheck(boolean isCheck) {
//			this.isCheck = isCheck;
//		}
//
//	}
//
//	private List<LocationBean> locationBeans = new ArrayList<>();
//	private List<String> locationList = new ArrayList<>();
//}
