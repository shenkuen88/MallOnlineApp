package com.nannong.mall.activity.order;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nannong.mall.R;
import com.nannong.mall.response.order.AddProductCommentResponse;
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
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.photopicker.adapter.ImagePublishSmallAdapter;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/20.
 * 评价
 */
public class PublicCommentActy extends BaseActivity {
    private GridView mGridView;
    private ImagePublishSmallAdapter mAdapter;
    private TextView sendTv;
    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private Gson gson;
    private EditText contentEt;
    private boolean isFirstOpen = true;
    private String orderId = "";
    private String contentId = "1";
    private CheckBox cb;
    private RatingBar rb;
    private UploadManager uploadManager;
    private List<String> uploadUrlList = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_comment);
        orderId = Global.getOrderId();
        SharePref.saveString(Constants.PUBLIC_NEED_IMG_ACTY, PublicCommentActy.class.getName());
        gson = new Gson();
        initData();
        initView();
        initTitle();
    }

    private void upLoadInfo() {
        CMLog.e(Constants.HTTP_TAG,"---"+mDataList.size());
        if (GeneralUtils.isNotNullOrZeroLenght(contentEt.getText().toString())) {
            String nm = 0 + "";
            if (cb.isChecked()) {
                nm = "1";
            } else {
                nm = "0";
            }
            if (mDataList.size() == 0) {
                UserServiceImpl.instance().addProductComment(orderId, nm, rb.getNumStars() + "",
                        contentEt.getText().toString(), "", "", "", "", AddProductCommentResponse.class.getName());
            } else {
                uploadManager = new UploadManager();
                for (int i = 0; i < mDataList.size(); i++) {
                    if (GeneralUtils.isNotNullOrZeroLenght(mDataList.get(i).getSourcePath())) {
                        getUpimg(mDataList.get(i).getSourcePath());
                    }
                }
            }
        }else if (mDataList.size()>0){
            uploadManager = new UploadManager();
            for (int i = 0; i < mDataList.size(); i++) {
                if (GeneralUtils.isNotNullOrZeroLenght(mDataList.get(i).getSourcePath())) {
                    getUpimg(mDataList.get(i).getSourcePath());
                }
            }
        }else {ToastUtil.makeText(mContext,"请输入内容");}

    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("发表评论");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    public void getUpimg(final String imagePath) {
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                uploadManager.put(imagePath, "apprise_" + java.util.UUID.randomUUID().toString() + ".png", Global.getToken(),
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
//                                CMLog.e(Constants.HTTP_TAG, key + ",\r\n " + info + ",\r\n "
//                                        + res);
                                try {
                                    // 七牛返回的文件名
                                    String upimg = res.getString("key");
                                    uploadUrlList.add(upimg);//将七牛返回图片的文件名添加到list集合中
                                    if (uploadUrlList.size() == mDataList
                                            .size()) {
                                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_UPLOAD_PICS_SUCCESS));
                                    }
                                } catch (JSONException e) {
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

    protected void onPause() {
        super.onPause();
        saveTempToPref();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void saveTempToPref() {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = gson.toJson(mDataList);
        sp.edit().putString(Constants.PREF_TEMP_IMAGES, prefStr).commit();

    }

    private void getTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(Constants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr)) {
            List<ImageItem> tempImages = gson.fromJson(prefStr, new TypeToken<List<ImageItem>>() {
            }.getType());
            mDataList = tempImages;
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(Constants.PREF_TEMP_IMAGES).commit();
        SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, "");
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        //刚打开
        if (getIntent().getStringExtra(IntentCode.COMMUNITY_PUBLIC).trim().equals("1")) {
            mDataList.clear();
            isFirstOpen = true;
        } else {
            isFirstOpen = false;
            getTempFromPref();
            List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                    .getSerializableExtra(IntentCode.EXTRA_IMAGE_LIST);
            if (incomingDataList != null) {
                mDataList.addAll(incomingDataList);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    public void initView() {
        rb = (RatingBar) findViewById(R.id.star_rb);
        contentEt = (EditText) findViewById(R.id.content_et);
        if (!isFirstOpen) {
            contentEt.setText(SharePref.getString(Constants.PUBLIC_SAVE_CONTENT, ""));
        }
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishSmallAdapter(this, mDataList);

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final int pos = position;
                //权限
                checkPermission(new CheckPermListener() {
                                    @Override
                                    public void superPermission() {
                                        if (pos == getDataSize()) {
                                            new PopupWindows(mContext, mGridView);
                                        } else {
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
        cb = (CheckBox) findViewById(R.id.btn_ck);
        findViewById(R.id.nm_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {
                    cb.setChecked(false);
                } else {
                    cb.setChecked(true);
                }
            }
        });
        findViewById(R.id.public_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              upLoadInfo();
            }
        });
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                removeTempFromPref();
                finish();
            } else if (NotiTag.equalsTags(mContext, tag, NotiTag.TAG_UPLOAD_PICS_SUCCESS)) {
                String nm = 0 + "";
                if (cb.isChecked()) {
                    nm = "1";
                } else {
                    nm = "0";
                }
                String url1 = "", url2 = "", url3 = "", url4 = "";
                for (int i = 0; i < uploadUrlList.size(); i++) {
                    if (i == 0) {
                        url1 = uploadUrlList.get(i);
                    } else if (i == 1) {
                        url2 = uploadUrlList.get(i);
                    } else if (i == 2) {
                        url3 = uploadUrlList.get(i);
                    } else if (i == 3) {
                        url4 = uploadUrlList.get(i);
                    }
                }
                UserServiceImpl.instance().addProductComment(orderId, nm, rb.getNumStars() + "",
                        contentEt.getText().toString(), url1, url2, url3, url4, AddProductCommentResponse.class.getName());
            } else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                upLoadInfo();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(AddProductCommentResponse.class.getName())) {
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    AddProductCommentResponse mAddProductCommentResponse = GsonHelper.toType(result, AddProductCommentResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddProductCommentResponse.getResultCode())) {
                        ToastUtil.makeText(mContext, "评价成功！");
                        removeTempFromPref();
                        finish();
                    } else {
                        ErrorCode.doCode(mContext, mAddProductCommentResponse.getResultCode(), mAddProductCommentResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private int getAvailableSize() {
        int availSize = 4 - mDataList.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    public String getString(String s) {
        String path = null;
        if (s == null) return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(final Context mContext, View parent) {

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
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, contentEt.getText().toString());
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
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void takePhoto() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File vFile = new File(FileSystemManager.getTemporaryPath(mContext), String.valueOf(System.currentTimeMillis())
//                + ".jpg");
        File vFile = new File(FileSystemManager.getImgPath(mContext), String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (mDataList.size() < Constants.MAX_IMAGE_SIZE
                        && resultCode == -1 && !TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                break;
        }
    }

    private void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }
}
