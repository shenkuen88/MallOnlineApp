package com.nannong.mall.view.home_view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.NewProductActy;
import com.nannong.mall.response.index.ColumnListBean;

import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;


/**
 * Created by huqing on 2016/7/18.
 */
public class OneViewImageView extends android.widget.LinearLayout
{

    private ImageView one_iv;

    private ColumnListBean result;

    private String title;

    private String icon;

    private Context context;


    public OneViewImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        init(context);
    }

    public OneViewImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public OneViewImageView(Context context)
    {
        super(context);
        this.context = context;
        init(context);
    }

    public void setData(ColumnListBean result, String title)
    {
        if (this.result != null && this.result.equals(result))
        {
            return;
        }
        else
        {
            this.result = result;
            this.title = title;
            if (GeneralUtils.isNotNullOrZeroLenght(GsonHelper.toJson(result)))
            {
                viewWithData(context, result);
            }
            else
            {
                setVisibility(GONE);
            }
        }

    }


    private void viewWithData(final Context context, final ColumnListBean mColumnContentResponse)
    {
        if (GeneralUtils.isNotNullOrZeroLenght(mColumnContentResponse.getContentList().get(0).getPicUrl1()))
        {
            GeneralUtils.setImageViewWithUrl(context, mColumnContentResponse.getContentList().get(0).getThumPicUrl1(),
                    one_iv,
                    R.drawable.default_bg);
//        ImageLoaderUtil.getInstance().initImage(context, mColumnContentResponse.getContentList().get(0).getPicUrl1(), one_iv, Constants.DEFAULT_IMAGE_LOAD);
        }
        else
        {
            one_iv.setImageResource(R.drawable.default_bg);
        }
        one_iv.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, NewProductActy.class);
                intent.putExtra(IntentCode.contentID, mColumnContentResponse.getContentList().get(0).getShopID());
                intent.putExtra(IntentCode.shopId, mColumnContentResponse.getContentList().get(0).getShopID());
                context.startActivity(intent);
            }
        });

    }

    private void init(final Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.home_column_one, this);
        one_iv = (ImageView) findViewById(R.id.one_iv);

    }
}
