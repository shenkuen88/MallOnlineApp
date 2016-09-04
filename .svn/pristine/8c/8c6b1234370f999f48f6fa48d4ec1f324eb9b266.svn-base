package com.nannong.mall.activity.order;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nannong.mall.R;
import com.nannong.mall.response.order.RefundResponse;
import com.nannong.mall.view.chooseimage.ImageBucketChooseActivity;
import com.nannong.mall.view.chooseimage.ImageZoomActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.photopicker.adapter.ImagePublishAdapter;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/20.
 * 退款
 */
public class RefundActy extends BaseActivity implements View.OnClickListener
{
    private GridView mGridView;

    private ImagePublishAdapter mAdapter;

    private TextView sendTv;

    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();

    private Gson gson;

    private EditText contentEt;

    private boolean isFirstOpen = true;

    private TextView tvResson;

    private EditText etMoney;

    private EditText etExplain;

    private TextView tvMax;

    private String orderId;

    private UploadManager uploadManager;

    private List<String> uploadUrlList = new ArrayList<>();

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        orderId = Global.getOrderId();
        SharePref.saveString(Constants.PUBLIC_NEED_IMG_ACTY, RefundActy.class.getName());
        gson = new Gson();
        initData();
        initView();
        initTitle();
    }



    public void getUpimg(final String imagePath)
    {
        new Thread()
        {
            public void run()
            {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager.put(imagePath, "refund_" + java.util.UUID.randomUUID().toString() + ".png", Global.getToken(),
                        new UpCompletionHandler()
                        {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res)
                            {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
//                                CMLog.e(Constants.HTTP_TAG, key + ",\r\n " + info + ",\r\n "
//                                        + res);
                                try
                                {
                                    // 七牛返回的文件名
                                    String upimg = res.getString("key");
                                    uploadUrlList.add(upimg);//将七牛返回图片的文件名添加到list集合中
                                    if (uploadUrlList.size() == mDataList
                                            .size())
                                    {
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
        }
                .
                        start();
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("申请退款");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("发送");
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

    private void removeTempFromPref()
    {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(Constants.PREF_TEMP_IMAGES).commit();
        SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, "");//说明
        SharePref.saveString(Constants.PUBLIC_SAVE_MONEY, "");//金额
        SharePref.saveString(Constants.PUBLIC_SAVE_REASON, "");//原因

    }

    @SuppressWarnings("unchecked")
    private void initData()
    {
        //刚打开
        if (getIntent().getStringExtra(IntentCode.COMMUNITY_PUBLIC).trim().equals("1"))
        {
            mDataList.clear();
            isFirstOpen = true;
        }
        else
        {
            isFirstOpen = false;
            getTempFromPref();
            List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                    .getSerializableExtra(IntentCode.EXTRA_IMAGE_LIST);
            if (incomingDataList != null)
            {
                mDataList.addAll(incomingDataList);
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    private Double MIN_MARK = 0.0;

    private Double MAX_MARK = 100.0;

    public void initView()
    {
        contentEt = (EditText) findViewById(R.id.content_et);
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
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

        tvResson = (TextView) findViewById(R.id.choose_reason_tv);
        tvMax = (TextView) findViewById(R.id.max_tv);
        tvMax.setText("(最多" + Global.getRefundMoney() + "元)");
        etMoney = (EditText) findViewById(R.id.money_tv);
        etExplain = (EditText) findViewById(R.id.explain_et);

        if (Global.getRefundMoney().equals("") || Global.getRefundMoney().equals("0"))
        {
            MAX_MARK = 0.0;
        }
        else
        {
            MAX_MARK = Double.valueOf(Global.getRefundMoney());
        }
        etMoney.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (start > 1)
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK)
                        {
                            s = String.valueOf(MAX_MARK);
                            etMoney.setText(s);
                        }
                        else if (num < MIN_MARK)
                        {
                            s = String.valueOf(MIN_MARK);
                        }
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s != null && !s.equals(""))
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        double markVal = 0;
                        try
                        {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK)
                        {
                            ToastUtil.makeText(mContext, "金额不能超过" + MAX_MARK + "元");
                            etMoney.setText(String.valueOf(MAX_MARK));
                            etMoney.setSelection(etMoney.getText().length());//光标停在最右侧
                        }

                        return;
                    }
                }
            }
        });
//        etMoney.addTextChangedListener(new TextWatcher() {
//            int l=0;////////记录字符串被删除字符之前，字符串的长度
//            int location=0;//记录光标的位置
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //限制最多输入两位
////                if (s.toString().contains(".")) {
////                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
////                        s = s.toString().subSequence(0,
////                                s.toString().indexOf(".") + 3);
////                        etMoney.setText(s);
////                        etMoney.setSelection(s.length());
////                    }
////                }
////                if (s.toString().trim().substring(0).equals(".")) {
////                    s = "0" + s;
////                    etMoney.setText(s);
////                    etMoney.setSelection(2);
////                }
////
////                if (s.toString().startsWith("0")
////                        && s.toString().trim().length() > 1) {
////                    if (!s.toString().substring(1, 2).equals(".")) {
////                        etMoney.setText(s.subSequence(0, 1));
////                        etMoney.setSelection(1);
////                        return;
////                    }
////                }
//                l=s.length();
//                location=etMoney.getSelectionStart();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(Global.getRefundMoney().equals("")||Global.getRefundMoney().equals("0")){
////                    etMoney.setSelection(1);
//                    etMoney.setText("0");
//                    Editable etable=etMoney.getText();
//                    Selection.setSelection(etable, location);
//                }else if (GeneralUtils.isNotNullOrZeroLenght(Global.getRefundMoney() + "") &&
//                        (Double.parseDouble(s.toString()) > Double.parseDouble(Global.getRefundMoney()))) {
//                    etMoney.setText(Global.getRefundMoney());
//                    Editable etable=etMoney.getText();
//                    Selection.setSelection(etable, location);
//                }
//            }
//        });
        tvResson.setOnClickListener(this);
        if (!isFirstOpen)
        {
            contentEt.setText(SharePref.getString(Constants.PUBLIC_SAVE_CONTENT, ""));
            //原因
            if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.PUBLIC_SAVE_REASON, "")))
            {
                whitchIndex = Integer.parseInt(SharePref.getString(Constants.PUBLIC_SAVE_REASON, ""));
                tvResson.setText(resonArr[whitchIndex]);
            }
            etMoney.setText(SharePref.getString(Constants.PUBLIC_SAVE_MONEY, ""));
            etExplain.setText(SharePref.getString(Constants.PUBLIC_SAVE_CONTENT, ""));
        }
    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event) {
        {
            if (event instanceof NoticeEvent)
            {
                String tag = ((NoticeEvent) event).getTag();
                //关闭页面
                if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
                {
                    finish();
                }
                else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
                {
                    if (tvResson.getText().toString().equals("请选择"))
                    {
                        ToastUtil.makeText(mContext, "请选择退款原因");
                        return;
                    }
                    else if (GeneralUtils.isNullOrZeroLenght(etMoney.getText().toString()))
                    {
                        ToastUtil.makeText(mContext, "请输入退款金额");
                        return;
                    }
                    if (mDataList.size() == 0)
                    {
                        UserServiceImpl.instance().refund(orderId, whitchIndex + "", etExplain.getText().toString(),
                                etMoney.getText().toString(), "", "", "", "", RefundResponse.class.getName());
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
                else if (NotiTag.equalsTags(mContext, tag, NotiTag.TAG_UPLOAD_PICS_SUCCESS))
                {
                    String nm = 0 + "";
                    String url1 = "", url2 = "", url3 = "", url4 = "";
                    for (int i = 0; i < uploadUrlList.size(); i++)
                    {
                        if (i == 0)
                        {
                            url1 = uploadUrlList.get(i);
                        }
                        else if (i == 1)
                        {
                            url2 = uploadUrlList.get(i);
                        }
                        else if (i == 2)
                        {
                            url3 = uploadUrlList.get(i);
                        }
                        else if (i == 3)
                        {
                            url4 = uploadUrlList.get(i);
                        }
                    }
                    UserServiceImpl.instance().refund(orderId, whitchIndex + "", etExplain.getText().toString(),
                            etMoney.getText().toString(), url1, url2, url3, url4, RefundResponse.class.getName());
                }
            }
            if (event instanceof NetResponseEvent)
            {
                NetLoadingDialog.getInstance().dismissDialog();
                String tag = ((NetResponseEvent) event).getTag();
                String result = ((NetResponseEvent) event).getResult();
                NetLoadingDialog.getInstance().dismissDialog();
                if (tag.equals(RefundResponse.class.getName()))
                {
                    if (GeneralUtils.isNotNullOrZeroLenght(result))
                    {
                        RefundResponse mRefundResponse = GsonHelper.toType(result, RefundResponse.class);
                        if (Constants.SUCESS_CODE.equals(mRefundResponse.getResultCode()))
                        {
                            ToastUtil.makeText(mContext, "申请退款成功！");
                            removeTempFromPref();
                            OrderDetailActivity.needclose = true;
                            finish();
                        }
                        else
                        {
                            ErrorCode.doCode(mContext, mRefundResponse.getResultCode(), mRefundResponse.getDesc());
                        }
                    }
                    else
                    {
                        ToastUtil.showError(mContext);
                    }
                }
            }
        }
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

    private String[] resonArr = new String[]{"未收到货", "质量问题", "其他"};

    private String refundReson = resonArr[0];

    private int whitchIndex = 0;


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.choose_reason_tv:
                AlertDialog Builder = new AlertDialog.Builder(mContext).setTitle("退款原因")
                        .setSingleChoiceItems(
                                resonArr, whitchIndex,
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        refundReson = resonArr[which];
                                        tvResson.setText(refundReson);
                                        whitchIndex = which;
                                        dialog.dismiss();
                                    }
                                }).show();
                break;
        }
    }

    public class PopupWindows extends PopupWindow
    {

        public PopupWindows(final Context mContext, View parent)
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
                    SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, etExplain.getText().toString());//说明
                    SharePref.saveString(Constants.PUBLIC_SAVE_MONEY, etMoney.getText().toString());//金额
                    if (!tvResson.getText().toString().equals("请选择"))
                    {
                        SharePref.saveString(Constants.PUBLIC_SAVE_REASON, whitchIndex + "");//原因
                    }
                    else
                    {
                        SharePref.saveString(Constants.PUBLIC_SAVE_REASON, "");//原因
                    }

                    Intent intent = new Intent(mContext,
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

}
