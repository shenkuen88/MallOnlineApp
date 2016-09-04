package com.nannong.mall.adapter.index;

/**
 * Created by huqing on 2016/7/18.
 */


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.NewProductActy;
import com.nannong.mall.response.index.ColumnListBean;
import com.nannong.mall.response.index.ContentListBean;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.tools.GeneralUtils;

public class FourViewAdapter extends BaseAdapter {
    private List<ContentListBean> contentList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private ColumnListBean mColumnContentResponse;

    public FourViewAdapter(Context context, ColumnListBean result) {
        this.mContext = context;
        this.mColumnContentResponse = result;

        contentList = mColumnContentResponse.getContentList();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public ContentListBean getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.home_column_four_item, null);
            holder.good_img_iv = (ImageView) convertView.findViewById(R.id.good_img_iv);
            holder.good_name_tv = (TextView) convertView.findViewById(R.id.good_name_tv);
            holder.four_rl = (RelativeLayout) convertView.findViewById(R.id.four_rl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //名称
        holder.good_name_tv.setText(getItem(position).getContentName());
        //图片
        if (GeneralUtils.isNotNullOrZeroLenght(getItem(position).getThumPicUrl1())) {
            GeneralUtils.setImageViewWithUrl(mContext,getItem(position).getThumPicUrl1(), holder.good_img_iv,  R.drawable.default_bg);
        }
        holder.four_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewProductActy.class);
                intent.putExtra(IntentCode.contentID, mColumnContentResponse.getContentList().get(position).getContentID());
                intent.putExtra(IntentCode.shopId, mColumnContentResponse.getContentList().get(position).getShopID());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView good_name_tv;
        private ImageView good_img_iv;
        private RelativeLayout four_rl;
    }


}