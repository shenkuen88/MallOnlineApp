package com.nannong.mall.adapter.community;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.community.ReplyCommuniyActy;
import com.nannong.mall.view.chooseimage.CommunityImageZoomActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.index.ArticleListBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.view.MyDisappearImageView;
import cn.nj.www.my_module.view.nine_image.ImageBean;
import cn.nj.www.my_module.view.nine_image.NineGridAdapter;
import cn.nj.www.my_module.view.nine_image.NineGridlayout;

/**
 * 单个动态
 */
public class CommunityItemAdapter extends BaseAdapter {
    private List<ArticleListBean> articalList;

    private LayoutInflater inflater;
    private Context mContext;
    private int width = 0;

    //点赞Flag  true 正在删除
    private boolean zanFlag = false;
    /**
     * 九宫格的适配器
     */
    private DefineNinePicAdapter adapter;


    public void backDeleteFlag() {
        this.zanFlag = false;
    }

    public void setarticalList(List<ArticleListBean> articalList) {
        if (articalList != null) {
            this.articalList = articalList;
        } else {
            this.articalList = new ArrayList<>();
        }
    }


    public CommunityItemAdapter(Context context, List<ArticleListBean> articalList, int width) {
        this.inflater = LayoutInflater.from(context);
        mContext = context;
        this.width = width;
        this.setarticalList(articalList);
    }

    public void update(ArrayList<ArticleListBean> articalList) {
        this.setarticalList(articalList);
        notifyDataSetChanged();
    }

    public List<ArticleListBean> getarticalList() {
        return articalList;
    }

    public void addData(ArrayList<ArticleListBean> articalList1) {
        this.articalList.addAll(articalList1);
        notifyDataSetChanged();
    }

    public void deleteZone(int position) {
        this.articalList.remove(position);
        updateList(articalList);
    }

    public void clearList() {
        this.articalList.clear();
    }

    public void updateList(List<ArticleListBean> articalList) {
        this.articalList.addAll(articalList);
    }

    @Override
    public int getCount() {
        return articalList.size();
    }

    @Override
    public ArticleListBean getItem(int position) {
        return articalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.community_zone_item, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.content_tv = (TextView) convertView.findViewById(R.id.content_tv);
            holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.head_iv = (ImageView) convertView.findViewById(R.id.head_iv);
            holder.ivMore = (NineGridlayout) convertView.findViewById(R.id.pic_gv);
            holder.all_tv = (TextView) convertView.findViewById(R.id.all_text);
            holder.itemll = (LinearLayout) convertView.findViewById(R.id.zone_item_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ArticleListBean articel = articalList.get(position);
        CMLog.e(Constants.LOCAL_TAG, position + " " + articel.getThumPortrait());
        GeneralUtils.setRoundImageViewWithUrl(mContext, articel.getThumPortrait(), holder.head_iv, R.drawable.default_head);
        holder.name_tv.setText(articel.getUserNickName());
        holder.content_tv.setText(articel.getContent());
        holder.time_tv.setText(articel.getCreateTime());
        if (textLines(holder.content_tv) > 2) {
            holder.all_tv.setVisibility(View.VISIBLE);
        } else {
            holder.all_tv.setVisibility(View.GONE);
        }
        //点击这个帖子 ，跳转到详情页
        holder.itemll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReplyCommuniyActy.class);
                intent.putExtra(Constants.ARTICLE_ITEM_CONETNT, articel.getArticleID());
                mContext.startActivity(intent);
            }
        });

        if (articel.getThumPicUrlList().isEmpty()) {
            holder.ivMore.setVisibility(View.GONE);
        } else {
            holder.ivMore.setVisibility(View.VISIBLE);
            List<ImageBean> imageList = new ArrayList<>();
            List<ImageBean> imageListALL = new ArrayList<>();
            holder.ivMore.setVisibility(View.VISIBLE);
            if (articel.getThumPicUrlList().size() == 0) {
                holder.ivMore.setVisibility(View.GONE);
            } else if (articel.getThumPicUrlList().size() == 1) {
                for (int i = 0; i < articel.getThumPicUrlList().size(); i++) {
                    imageList.add(new ImageBean(articel.getThumPicUrlList().get(i), 640, 960));
                    imageListALL.add(new ImageBean(articel.getPicUrlList().get(i), 640, 960));
                    handlerOneImage(holder, imageList, imageListALL);
                }
            } else {
                for (int i = 0; i < articel.getThumPicUrlList().size(); i++) {
                    imageList.add(new ImageBean(articel.getThumPicUrlList().get(i), 240, 240));
                    imageListALL.add(new ImageBean(articel.getThumPicUrlList().get(i), 240, 240));
                }
                handlerOneImage(holder, imageList, imageListALL);
            }
        }

        return convertView;
    }

    private void handlerOneImage(ViewHolder viewHolder, final List<ImageBean> image, final List<ImageBean> image1) {

        adapter = new DefineNinePicAdapter(mContext, image);
        viewHolder.ivMore.setAdapter(adapter);
        viewHolder.ivMore.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
            @Override
            public void onItemClick(View view, int position) {
                if (mContext != null) {
                    Intent intent = new Intent(mContext, CommunityImageZoomActivity.class);
                    intent.putExtra(IntentCode.EXTRA_CURRENT_IMG_POSITION, position);
                    intent.putExtra(IntentCode.COMMUNITY_IMAGE_DATA, (Serializable) image1);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    class DefineNinePicAdapter extends NineGridAdapter
    {

        public DefineNinePicAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getCount() {
            return (list == null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position) {
            return getItem(position) == null ? null : ((ImageBean) getItem(position)).getUrl();
        }

        @Override
        public Object getItem(int position) {
            return (list == null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view) {

            MyDisappearImageView iv = null;
            if (view != null && view instanceof MyDisappearImageView) {
                iv = (MyDisappearImageView) view;
            } else {
                iv = new MyDisappearImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url = getUrl(position);
            if (GeneralUtils.isNotNullOrZeroLenght(getUrl(position))) {
                if (getCount() > 1) {
                    GeneralUtils.setImageViewWithUrl(mContext, getUrl(position), iv, R.drawable.default_bg);
                } else {
                    GeneralUtils.setImageViewWithUrl(mContext, getUrl(position), iv,  R.drawable.default_bg);
                }
            }
            if (!TextUtils.isEmpty(url)) {
                iv.setTag(url);
            }
            return iv;
        }
    }

    private class ViewHolder {
        //九宫格图片
        private NineGridlayout ivMore;
        //头像
        private ImageView head_iv;
        //姓名
        private TextView name_tv;
        //内容
        private TextView content_tv;
        //时间
        private TextView time_tv;
        //全文
        private TextView all_tv;
        //整个
        private LinearLayout itemll;

    }

    private int measureTextViewHeight(String text) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    private int textLines(TextView textView) {
        return measureTextViewHeight(textView.getText().toString()) / measureTextViewHeight("测试");
    }

}
