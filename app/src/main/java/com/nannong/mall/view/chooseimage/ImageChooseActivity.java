package com.nannong.mall.view.chooseimage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nannong.mall.activity.friend.PublishActivity;
import com.nannong.mall.activity.index.PublicTeamBuyActy;
import com.nannong.mall.activity.order.PublicCommentActy;
import com.nannong.mall.activity.order.RefundActy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.view.photopicker.adapter.ImageGridAdapter;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;


/**
 * 图片选择
 */
public class ImageChooseActivity extends BaseActivity
{
    private List<ImageItem> mDataList = new ArrayList<ImageItem>();

    private String mBucketName;

    private int availableSize;

    private GridView mGridView;

    private TextView mBucketNameTv;

    private ImageGridAdapter mAdapter;

    private Button mFinishBtn;

    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();

    private HeadView headView;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_image_choose);

        mDataList = (List<ImageItem>) getIntent().getSerializableExtra(
                IntentCode.EXTRA_IMAGE_LIST);
        if (mDataList == null)
        {
            mDataList = new ArrayList<ImageItem>();
        }
        mBucketName = getIntent().getStringExtra(
                IntentCode.EXTRA_BUCKET_NAME);

        if (TextUtils.isEmpty(mBucketName))
        {
            mBucketName = "请选择";
        }
        availableSize = getIntent().getIntExtra(
                IntentCode.EXTRA_CAN_ADD_IMAGE_SIZE,
                Constants.MAX_IMAGE_SIZE);

        initTitle();
        initView();
        initListener();

    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        headView = new HeadView((ViewGroup) view);
        headView.setRightText("取消");
        headView.setLeftImage(R.mipmap.app_title_back);
    }

    public void initView()
    {
        mBucketNameTv = (TextView) findViewById(R.id.title);
        headView.setTitleText(mBucketName);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
        mGridView.setAdapter(mAdapter);
        mFinishBtn = (Button) findViewById(R.id.finish_btn);

        mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/"
                + availableSize + ")");
        mAdapter.notifyDataSetChanged();
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
                back();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                back();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        back();
    }

    private void back()
    {
        String className = SharePref.getString(Constants.PUBLIC_NEED_IMG_ACTY, "");
        Intent intent = new Intent();
        if (className.equals(PublishActivity.class.getName()))
        {//发帖
            intent.setClass(mContext, PublishActivity.class);
        }
        else if (className.equals(PublicTeamBuyActy.class.getName()))
        {//团购
            intent.setClass(mContext, PublicTeamBuyActy.class);
        }
        else if (className.equals(PublicCommentActy.class.getName()))
        {//评论
            intent.setClass(mContext, PublicCommentActy.class);
        }
        else if (className.equals(RefundActy.class.getName()))
        {//退款
            intent.setClass(mContext, RefundActy.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "0");
        startActivity(intent);
        finish();
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

    private void initListener()
    {
        mFinishBtn.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                String className = SharePref.getString(Constants.PUBLIC_NEED_IMG_ACTY, "");
                Intent intent = new Intent();
                if (className.equals(PublishActivity.class.getName()))
                {//发帖
                    intent.setClass(mContext, PublishActivity.class);
                }
                else if (className.equals(PublicTeamBuyActy.class.getName()))
                {//发帖
                    intent.setClass(mContext, PublicTeamBuyActy.class);
                }
                else if (className.equals(PublicCommentActy.class.getName()))
                {//评论
                    intent.setClass(mContext, PublicCommentActy.class);
                }
                else if (className.equals(RefundActy.class.getName()))
                {//退款
                    intent.setClass(mContext, RefundActy.class);
                }
                intent.putExtra(
                        IntentCode.EXTRA_IMAGE_LIST,
                        (Serializable) new ArrayList<ImageItem>(selectedImgs
                                .values()));
                intent.putExtra(IntentCode.COMMUNITY_PUBLIC, "0");
                startActivity(intent);
                finish();
            }

        });

        mGridView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

                ImageItem item = mDataList.get(position);
                if (item.isSelected)
                {
                    item.isSelected = false;
                    selectedImgs.remove(item.imageId);
                }
                else
                {
                    if (selectedImgs.size() >= availableSize)
                    {
                        Toast.makeText(ImageChooseActivity.this,
                                "最多选择" + availableSize + "张图片",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected = true;
                    selectedImgs.put(item.imageId, item);
                }

                mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/"
                        + availableSize + ")");
                mAdapter.notifyDataSetChanged();
            }

        });


    }
}
