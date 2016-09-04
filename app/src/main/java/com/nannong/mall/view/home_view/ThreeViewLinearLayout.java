package com.nannong.mall.view.home_view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.NewProductActy;
import com.nannong.mall.response.index.ColumnListBean;
import com.nannong.mall.response.index.ContentListBean;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * Created by huqing on 2016/7/18.
 */
public class ThreeViewLinearLayout extends LinearLayout
{

    private ImageView top_left_icon;

    private TextView title_tv;

    private ColumnListBean result;

    private String title;

    private TextView style3_tv, style1_tv, style2_tv;

    private TextView en_style3_tv, en_style1_tv, en_style2_tv;

    private LinearLayout good1_ll, good3_ll, good2_ll;

    private ImageView iv3, iv1, iv2;

    private Context context;

    private RelativeLayout topView;

    private List<ContentListBean> dataList = new ArrayList<>();

    public ThreeViewLinearLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    public ThreeViewLinearLayout(Context context)
    {
        super(context);
        init(context);
    }

    public ThreeViewLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public void setData(ColumnListBean result, String title, String icon)
    {
        title_tv.setText(title);
        if (this.result != null && this.result.equals(result))
        {
            return;
        }
        this.result = result;
        this.title = title;
        if (GeneralUtils.isNotNullOrZeroLenght(icon))
        {
//            ImageLoaderUtil.getInstance().initImage(context, icon, top_left_icon, Constants.DEFAULT_IMAGE_F_LOAD);
            GeneralUtils.setImageViewWithUrl(context, icon, top_left_icon, R.drawable.default_bg);
        }
        else
        {
            top_left_icon.setVisibility(GONE);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(GsonHelper.toJson(result)))
        {
            viewWithData(context, result);
        }
        else
        {
            setVisibility(GONE);
        }
    }

    private void init(final Context context)
    {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.home_column_three, this);
        top_left_icon = (ImageView) findViewById(R.id.top_left_icon);
        title_tv = (TextView) findViewById(R.id.title_tv);
        style1_tv = (TextView) findViewById(R.id.style1_tv);
        style2_tv = (TextView) findViewById(R.id.style2_tv);
        style3_tv = (TextView) findViewById(R.id.style3_tv);
        en_style1_tv = (TextView) findViewById(R.id.en_style1_tv);
        en_style2_tv = (TextView) findViewById(R.id.en_style2_tv);
        en_style3_tv = (TextView) findViewById(R.id.en_style3_tv);
        good3_ll = (LinearLayout) findViewById(R.id.good3_ll);
        good2_ll = (LinearLayout) findViewById(R.id.good2_ll);
        good1_ll = (LinearLayout) findViewById(R.id.good1_ll);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        topView = (RelativeLayout) findViewById(R.id.top);
    }

    private void viewWithData(final Context context, final ColumnListBean mColumnContentResponse)
    {
        dataList = mColumnContentResponse.getContentList();
        final ContentListBean data1 = dataList.get(0);
        final ContentListBean data2 = dataList.get(1);
        final ContentListBean data3 = dataList.get(2);

        style1_tv.setText(data1.getContentName());
        style2_tv.setText(data2.getContentName());
        style3_tv.setText(data3.getContentName());

        en_style1_tv.setText(data3.getAbstracts());
        en_style2_tv.setText(data3.getAbstracts());
        en_style3_tv.setText(data3.getAbstracts());

        if (GeneralUtils.isNotNullOrZeroLenght(data1.getThumPicUrl1()))
        {
            GeneralUtils.setImageViewWithUrl(context, data1.getThumPicUrl1(),
                    iv1,
                    R.drawable.default_bg);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(data2.getThumPicUrl1()))
        {
            GeneralUtils.setImageViewWithUrl(context, data2.getThumPicUrl1(),
                    iv2,
                    R.drawable.default_bg);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(data3.getThumPicUrl1()))
        {
            GeneralUtils.setImageViewWithUrl(context, data3.getThumPicUrl1(),
                    iv3,
                    R.drawable.default_bg);
        }


        good1_ll.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int type = data1.getServiceType();
                jumpToDetail(type, context, data1.getContentID(), data1.getShopID());
            }
        });
        good2_ll.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int type = data2.getServiceType();
                jumpToDetail(type, context, data2.getContentID(), data1.getShopID());
            }
        });
        good3_ll.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int type = data3.getServiceType();
                jumpToDetail(type, context, data3.getContentID(), data1.getShopID());
            }
        });


    }

    private void jumpToDetail(int type, Context context, String contentId, String shopId)
    {
        Intent intent = new Intent(context, NewProductActy.class);
        intent.putExtra(IntentCode.contentID, contentId);
        intent.putExtra(IntentCode.shopId, shopId);
        context.startActivity(intent);
    }


}
