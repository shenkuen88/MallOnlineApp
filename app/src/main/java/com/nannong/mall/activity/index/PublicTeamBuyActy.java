package com.nannong.mall.activity.index;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chatuidemo.DemoApplication;
import com.nannong.mall.R;
import com.nannong.mall.activity.MainActivity;
import com.nannong.mall.dialog.BottomDialog;
import com.nannong.mall.response.index.PublicTeamBuyResponse;
import com.nannong.mall.tool.LocationService;
import com.nannong.mall.view.chooseimage.ImageBucketChooseActivity;
import com.nannong.mall.view.chooseimage.ImageZoomActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.ClearEditText;
import cn.nj.www.my_module.view.photopicker.adapter.ImagePublishSmallAdapter;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;
import cn.nj.www.my_module.view.switchbn.SwitchButton;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/4.
 * app设置页面
 */
public class PublicTeamBuyActy extends BaseActivity implements View.OnClickListener
{


    private ViewGroup.LayoutParams originalParams;

    private View hideView;

    private ScrollView scrollView;

    private Button bnSubmit;

    private ClearEditText tvTitle;

    private TextView tvEndTime;

    public static TextView tvLocation;

    private ClearEditText tvPrice;

    private EditText tvDes;

    private Gson gson;

    private GridView mGridView;

    private ImagePublishSmallAdapter mAdapter;

    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();

    private List<String> upimg_key_list = new ArrayList<>();

    private UploadManager uploadManager;

    private ClearEditText cetMaxNum;

    private ClearEditText cetMinNum;

    private LinearLayout llContent;


    String url1 = "";

    String url2 = "";

    String url3 = "";

    String url4 = "";

    private SwitchButton sbn;

    private LinearLayout llSetNum;

    private TextView tvUpload;

    private int jumpPosition;
    private BottomDialog bottomDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_team_buy);
        SharePref.saveString(Constants.PUBLIC_NEED_IMG_ACTY, PublicTeamBuyActy.class.getName());

        gson = new Gson();
        initAll();
        initData();
    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_USER_EXIT.equals(tag) || NotiTag.TAG_DIALOG_LEFT1.equals(tag))
            {
                finish();
            }
            else if (NotiTag.equalsTags(mContext, tag, NotiTag.TAG_UPLOAD_PICS_SUCCESS))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(tvTitle.getText().toString()) || upimg_key_list.size() > 0)
                {

                    upLoadInfo();
                }
                else
                {
                    ToastUtil.makeText(mContext, "请添加内容！");
                }
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(PublicTeamBuyResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CMLog.e(Constants.HTTP_TAG, result);
                    PublicTeamBuyResponse loginResponse = GsonHelper.toType(result, PublicTeamBuyResponse.class);
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode()))
                    {
                        ToastUtil.makeText(mContext, "提交成功，请等待审核");
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void upLoadInfo()
    {
        String min = "-1";
        String max = "-1";
        if (!sbn.isChecked())
        {
            min = cetMinNum.getText().toString();
            max = cetMaxNum.getText().toString();
        }
        UserServiceImpl.instance().publicTeamBuyProject(tvTitle.getText().toString(),
                tvEndTime.getText().toString(),
                tvLocation.getText().toString(),
                tvPrice.getText().toString(),
                min, max,
                tvDes.getText().toString(),
                url1,
                url2,
                url3,
                url4,
                PublicTeamBuyResponse.class.getName());
    }

    @Override
    public void initView()
    {
        initTitle();
        hideView = (View) findViewById(R.id.hideView);
        llContent = (LinearLayout) findViewById(R.id.llContent);
        llSetNum = (LinearLayout) findViewById(R.id.llSetNum);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        bnSubmit = (Button) findViewById(R.id.bnSubmit);
        sbn = (SwitchButton) findViewById(R.id.sbn);
        tvTitle = (ClearEditText) findViewById(R.id.tvTitle);
        cetMinNum = (ClearEditText) findViewById(R.id.cetMinNum);
        cetMaxNum = (ClearEditText) findViewById(R.id.cetMaxNum);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvUpload = (TextView) findViewById(R.id.tvUpload);
        tvPrice = (ClearEditText) findViewById(R.id.tvPrice);
        tvDes = (EditText) findViewById(R.id.tvDes);
        originalParams = hideView.getLayoutParams();

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishSmallAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        tvDes.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                scrollView.scrollTo(0, jumpPosition);
            }
        });
        sbn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {//人数不限
                    llSetNum.setVisibility(View.GONE);
                }
                else
                {
                    llSetNum.setVisibility(View.VISIBLE);
                }
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                final int pos = position;
                //权限
                checkPermission(new CheckPermListener()
                                {
                                    @Override
                                    public void superPermission()
                                    {
                                        if (pos == getDataSize())
                                        {
                                            new PopupWindows(mContext, mGridView);
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(mContext,
                                                    ImageZoomActivity.class);
                                            intent.putExtra(IntentCode.EXTRA_IMAGE_LIST,
                                                    (Serializable) mDataList);
                                            intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION, pos);
                                            startActivity(intent);
                                        }
                                    }
                                }, R.string.permission_photo,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {
        tvLocation.setOnClickListener(this);
        bnSubmit.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        llContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                Rect r = new Rect();
                //获取当前界面可视部分
                PublicTeamBuyActy.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = PublicTeamBuyActy.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                CMLog.i("info", "软键盘高度:" + heightDifference + "  屏幕高度" + screenHeight + "  可视区域高度：" + r.bottom);
                //  在xml中设置hiddenView的高度，会导致部分界面超过一些低像素高度小的手机，已进入界面就可以滑动
                if (r.bottom < screenHeight)
                {
                    originalParams.height = heightDifference / 2;
                    hideView.setLayoutParams(originalParams);
                }
                else
                {
                    originalParams.height = 0;
                    hideView.setLayoutParams(originalParams);
//                    scrollView.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText("发布拼团");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bnSubmit:
                if (GeneralUtils.isNotNullOrZeroLenght(tvTitle.getText().toString()) &&
                        GeneralUtils.isNotNullOrZeroLenght(tvEndTime.getText().toString())
                        && GeneralUtils.isNotNullOrZeroLenght(tvLocation.getText().toString())
                        && GeneralUtils.isNotNullOrZeroLenght(tvPrice.getText().toString()))
                {
                    upLoadAllPics();
                }
                else
                {
                    ToastUtil.makeText(mContext, "请完善所有内容");
                }
                break;
            case R.id.tvEndTime:
                Calendar cal = Calendar.getInstance();//使用日历类

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        Date date = new Date();
                        date.setYear(year - 1900);
                        date.setMonth(monthOfYear);
                        date.setDate(dayOfMonth);
                        tvEndTime.setText(GeneralUtils.getFormatDate(date));
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
                break;
            case R.id.tvLocation:
                bottomDialog= new BottomDialog(PublicTeamBuyActy.this,(int)(DisplayUtil.getWidth(mContext)*640),0);
                bottomDialog.show();
                break;
        }
    }

    private void upLoadAllPics()
    {
        if (!GeneralUtils.isNetworkConnected(mContext))
        {
            ToastUtil.showError(mContext);
            return;
        }
        NetLoadingDialog.getInstance().loading(mContext);
        if (mDataList.size() == 0)
        {
            upLoadInfo();
        }
        else
        {
            uploadManager = new UploadManager();
            for (int i = 0; i < mDataList.size(); i++)
            {
                if (GeneralUtils.isNotNullOrZeroLenght(mDataList.get(i).getSourcePath()))
                {
                    getUpimg(mDataList.get(i).getSourcePath());
                }
            }
        }
    }

    public void getUpimg(final String imagePath)
    {
        new Thread()
        {
            public void run()
            {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager.put(imagePath, "article_" + java.util.UUID.randomUUID().toString() + ".png", Global.getToken(),
                        new UpCompletionHandler()
                        {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res)
                            {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
//                                CMLog.e(Constants.HTTP_TAG, key + ",\r\n " + info + ",\r\n "
//                                        + res);
                                if (res == null)
                                {
                                    return;
                                }
                                try
                                {
                                    // 七牛返回的文件名
                                    String upimg = res.getString("key");
                                    upimg_key_list.add(upimg);//将七牛返回图片的文件名添加到list集合中
                                    if (upimg_key_list.size() == mDataList
                                            .size())
                                    {
                                        for (int i = 0; i < upimg_key_list.size(); i++)
                                        {
                                            if (i == 0)
                                            {
                                                url1 = upimg_key_list.get(0);
                                            }
                                            else if (i == 1)
                                            {
                                                url2 = upimg_key_list.get(1);
                                            }
                                            else if (i == 2)
                                            {
                                                url3 = upimg_key_list.get(2);
                                            }
                                            else if (i == 3)
                                            {
                                                url4 = upimg_key_list.get(3);
                                            }
                                        }
                                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_UPLOAD_PICS_SUCCESS));
                                    }
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , null);
            }
        }.start();
    }

    protected void onPause()
    {
        super.onPause();
        saveTempToPref();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void saveTempToPref()
    {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = gson.toJson(mDataList);
        sp.edit().putString(Constants.PREF_TEMP_IMAGES, prefStr).commit();

    }

    private void getTempFromPref()
    {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(Constants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr))
        {
            List<ImageItem> tempImages = gson.fromJson(prefStr, new TypeToken<List<ImageItem>>()
            {
            }.getType());
            mDataList = tempImages;
        }
    }


    boolean isFirstOpen = true;

    @SuppressWarnings("unchecked")
    private void initData()
    {
        //刚打开
        if (getIntent().getStringExtra(IntentCode.COMMUNITY_PUBLIC).trim().equals("1"))
        {
            MainActivity.getUpLoadImageUrl();
            Global.removeTempFromPref(mContext);
            mDataList.clear();
            isFirstOpen = true;
        }
        else
        {
            //显示
            tvTitle.setText(SharePref.getString(Constants.PUBLIC_SAVE_TITLE, ""));
            tvDes.setText(SharePref.getString(Constants.PUBLIC_SAVE_CONTENT, ""));
            tvEndTime.setText(SharePref.getString(Constants.PUBLIC_SAVE_TIME, ""));
            tvLocation.setText(SharePref.getString(Constants.PUBLIC_SAVE_LOCATION, ""));
            tvPrice.setText(SharePref.getString(Constants.PUBLIC_SAVE_MONEY, ""));
            cetMaxNum.setText(SharePref.getString(Constants.PUBLIC_SAVE_REASON_MAX, ""));
            cetMinNum.setText(SharePref.getString(Constants.PUBLIC_SAVE_REASON_MIN, ""));
            if (SharePref.getString(Constants.PUBLIC_SAVE_INPUT_NUM, "").equals("1"))
            {
                sbn.setChecked(true);
                llSetNum.setVisibility(View.GONE);
            }
            else
            {
                sbn.setChecked(false);
                llSetNum.setVisibility(View.VISIBLE);
            }
            isFirstOpen = false;
            getTempFromPref();
            List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                    .getSerializableExtra(IntentCode.EXTRA_IMAGE_LIST);
            if (incomingDataList != null)
            {
                mDataList.addAll(incomingDataList);
            }
            mAdapter = new ImagePublishSmallAdapter(this, mDataList);
            mGridView.setAdapter(mAdapter);
            new Handler().post(new Runnable()
            {
                @Override
                public void run()
                {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    private int getDataSize()
    {
        return mDataList == null ? 0 : mDataList.size();
    }

    private int getAvailableSize()
    {
        int availSize = 4 - mDataList.size();
        if (availSize >= 0)
        {
            return availSize;
        }
        return 0;
    }

    public String getString(String s)
    {
        String path = null;
        if (s == null)
        {
            return "";
        }
        for (int i = s.length() - 1; i > 0; i++)
        {
            s.charAt(i);
        }
        return path;
    }

    public class PopupWindows extends PopupWindow
    {

        public PopupWindows(Context mContext, View parent)
        {

            View view = View.inflate(mContext, R.layout.item_popupwindow, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, tvDes.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_TITLE, tvTitle.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_TIME, tvEndTime.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_LOCATION, tvLocation.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_MONEY, tvPrice.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_REASON_MAX, cetMaxNum.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_REASON_MIN, cetMinNum.getText().toString());
                    if (sbn.isChecked())
                    {
                        SharePref.saveString(Constants.PUBLIC_SAVE_INPUT_NUM, 1 + "");
                    }
                    else
                    {
                        SharePref.saveString(Constants.PUBLIC_SAVE_INPUT_NUM, "0");
                    }
                    //以及其他信息
                    Intent intent = new Intent(PublicTeamBuyActy.this,
                            ImageBucketChooseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(IntentCode.EXTRA_CAN_ADD_IMAGE_SIZE,
                            getAvailableSize());
                    startActivity(intent);
                    finish();
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;

    private String path = "";

    public void takePhoto()
    {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(FileSystemManager.getImgPath(mContext), String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists())
        {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        }
        else
        {
            if (vFile.exists())
            {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case TAKE_PICTURE:
                if (mDataList.size() < Constants.MAX_IMAGE_SIZE
                        && resultCode == -1 && !TextUtils.isEmpty(path))
                {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                break;
        }
    }

    private void notifyDataChanged()
    {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (GeneralUtils.isNotNullOrZeroLenght(tvTitle.getText().toString())
                    || GeneralUtils.isNotNullOrZeroLenght(tvDes.getText().toString()) || mDataList.size() > 0)
            {
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "是否放弃编辑?", "放弃", "继续编辑", NotiTag.TAG_DIALOG_LEFT1, NotiTag.TAG_DIALOG_RIGHT1);
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        jumpPosition = tvUpload.getTop();

    }
    public static List<LocationBean> locationBeans=new ArrayList<>();
    private LocationService locationService;
    private String alreadySelected;//预留

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = new LocationService(DemoApplication.getInstance());
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private List<String> locationList=new ArrayList<>();

    private BDLocationListener mListener = new BDLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location==null)return;
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                Global.savelatitude(location.getLatitude() + "");
                Global.savelangitude(location.getLongitude() + "");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                Log.e("sub","loca="+sb.toString());
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        CMLog.e("poi", poi.getName());
                        if (!locationList.contains(poi.getName())) {
                            locationList.add(poi.getName());
                        }
                    }
                }
                locationBeans.clear();
                for (int i = 0; i < locationList.size(); i++) {
                    if (locationList.get(i).toString().trim().equals(alreadySelected)) {
                        locationBeans.add(new LocationBean(locationList.get(i), true));
                    } else {
                        locationBeans.add(new LocationBean(locationList.get(i), false));
                    }
                    if(i==2){
                        break;
                    }
                }

                if(bottomDialog!=null) {
                    bottomDialog.mAdapter.setData(locationBeans);
                    bottomDialog.mAdapter.notifyDataSetChanged();
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
            }
        }

    };
    public static class LocationBean {
        private String locationName;
        private boolean isCheck;

        public LocationBean(String locationName, boolean isCheck) {
            this.locationName = locationName;
            this.isCheck = isCheck;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

    }

}
