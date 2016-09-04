package cn.nj.www.my_module.view.nine_image;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.tools.GeneralUtils;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<List<ImageBean>> datalist;
    private NineGridAdapter adapter;

    public MainAdapter(Context context, List<List<ImageBean>> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        List<ImageBean> itemList = datalist.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ninegridlayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMore = (NineGridlayout) convertView.findViewById(R.id.iv_ngrid_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (itemList.isEmpty()) {
            viewHolder.ivMore.setVisibility(View.GONE);
        } else {
            viewHolder.ivMore.setVisibility(View.VISIBLE);
            handlerOneImage(viewHolder, itemList);
        }

        return convertView;
    }

    private void handlerOneImage(ViewHolder viewHolder, List<ImageBean> image) {
        adapter = new DefineNinePicAdapter(context, image);
        viewHolder.ivMore.setAdapter(adapter);
        viewHolder.ivMore.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_LONG).show();
            }
        });
    }


    class ViewHolder {
        public NineGridlayout ivMore;
    }

    class DefineNinePicAdapter extends NineGridAdapter {

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

            ImageView iv = null;
            if (view != null && view instanceof ImageView) {
                iv = (ImageView) view;
            } else {
                iv = new ImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url = getUrl(position);
            GeneralUtils.setImageViewWithUrl(context, getUrl(position), iv,  R.drawable.default_bg);
            if (!TextUtils.isEmpty(url)) {
                iv.setTag(url);
            }
            return iv;
        }
    }
}
