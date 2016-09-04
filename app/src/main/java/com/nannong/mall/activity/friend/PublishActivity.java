package com.nannong.mall.activity.friend;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nannong.mall.R;
import com.nannong.mall.activity.MainActivity;
import com.nannong.mall.response.friend.PublicTZResponse;
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
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.photopicker.adapter.ImagePublishAdapter;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;
import de.greenrobot.event.EventBus;


public class PublishActivity extends BaseActivity
{
    private TextView sendTv;

    private GridView mGridView;

    private ImagePublishAdapter mAdapter;


    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();

    private Gson gson;

    private EditText titleEt;

    private EditText contentEt;

    private String contentStr = "";

    private String titleStr = "";

    private List<String> upimg_key_list = new ArrayList<>();

    private UploadManager uploadManager;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish);
        SharePref.saveString(Constants.PUBLIC_NEED_IMG_ACTY, PublishActivity.class.getName());
        gson = new Gson();
        initData();
        initView();
        initTitle();
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
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "是否放弃编辑?", "放弃", "继续编辑", NotiTag.TAG_DIALOG_LEFT1, NotiTag.TAG_DIALOG_RIGHT1);
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                upLoadAllPics();
            }
            else if (NotiTag.equalsTags(mContext, tag, NotiTag.TAG_UPLOAD_PICS_SUCCESS))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(titleEt.getText().toString()) || upimg_key_list.size() > 0)
                {
                    UserServiceImpl.instance().publicCommunity("1", titleEt.getText().toString(),
                            contentEt.getText().toString(), upimg_key_list, PublicTZResponse.class.getName());
                }
                else
                {
                    ToastUtil.makeText(mContext, "请添加内容！");
                }
            }
            else if (NotiTag.TAG_DIALOG_LEFT1.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                Global.removeTempFromPref(mContext);
                finish();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(PublicTZResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    PublicTZResponse mPublicTZResponse = GsonHelper.toType(result, PublicTZResponse.class);
                    if (Constants.SUCESS_CODE.equals(mPublicTZResponse.getResultCode()))
                    {
                        ToastUtil.makeText(mContext, "发布成功!");
                        finish();
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mPublicTZResponse.getResultCode(), mPublicTZResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
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
            UserServiceImpl.instance().publicCommunity("1", titleEt.getText().toString(),
                    contentEt.getText().toString(), PublicTZResponse.class.getName());
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

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("发帖子");
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
        SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, "");
        SharePref.saveString(Constants.PUBLIC_SAVE_TITLE, "");
        SharedPreferences sp = getSharedPreferences(
                Constants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(Constants.PREF_TEMP_IMAGES).commit();
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

    public void initView()
    {
        titleEt = (EditText) findViewById(R.id.title_et);
        contentEt = (EditText) findViewById(R.id.content_et);
        if (!isFirstOpen)
        {
            titleStr = SharePref.getString(Constants.PUBLIC_SAVE_TITLE, "");
            contentStr = SharePref.getString(Constants.PUBLIC_SAVE_CONTENT, "");
            titleEt.setText(titleStr);
            contentEt.setText(contentStr);
        }
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener()
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

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    private int getDataSize()
    {
        return mDataList == null ? 0 : mDataList.size();
    }

    private int getAvailableSize()
    {
        int availSize = Constants.MAX_IMAGE_SIZE - mDataList.size();
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

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
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
            bt1.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    SharePref.saveString(Constants.PUBLIC_SAVE_CONTENT, contentEt.getText().toString());
                    SharePref.saveString(Constants.PUBLIC_SAVE_TITLE, titleEt.getText().toString());
                    Intent intent = new Intent(PublishActivity.this,
                            ImageBucketChooseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(IntentCode.EXTRA_CAN_ADD_IMAGE_SIZE,
                            getAvailableSize());
                    startActivity(intent);
                    finish();
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener()
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
            if (GeneralUtils.isNotNullOrZeroLenght(titleEt.getText().toString())
                    || GeneralUtils.isNotNullOrZeroLenght(contentEt.getText().toString()) || mDataList.size() > 0)
            {
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "是否放弃编辑?", "放弃", "继续编辑", NotiTag.TAG_DIALOG_LEFT1, NotiTag.TAG_DIALOG_RIGHT1);
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
