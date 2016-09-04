package com.nannong.mall.view.home_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.adapter.index.FourViewAdapter;
import com.nannong.mall.response.index.ColumnListBean;

import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.view.MyGridView;


/**
 * Created by huqing on 2016/7/18.
 */
public class FourViewLinearLayout extends android.widget.LinearLayout {

    private ImageView top_left_icon;
    private TextView title_tv;
    private MyGridView six_gv;
    private ColumnListBean columnBean;
    private String title;
    private Context context;
    private RelativeLayout topView;

    public FourViewLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FourViewLinearLayout(Context context) {
        super(context);
        this.title = title;
        init(context);
    }

    public FourViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.home_column_four, this);
        top_left_icon = (ImageView) findViewById(R.id.top_left_icon);
        title_tv = (TextView) findViewById(R.id.title_tv);
        six_gv = (MyGridView) findViewById(R.id.four_gv);
        topView = (RelativeLayout) findViewById(R.id.top);
    }

    public void setData(  final ColumnListBean mColumnBean , String title, String icon) {
        title_tv.setText(title);
        if (mColumnBean != null &&mColumnBean.equals(this.columnBean)) {
            return;
        } else {
            this.columnBean = mColumnBean;
            //标题左侧的图标
            if (GeneralUtils.isNotNullOrZeroLenght(icon)) {
//                ImageLoaderUtil.getInstance().initImage(context, icon, top_left_icon, Constants.DEFAULT_IMAGE_F_LOAD);
                GeneralUtils.setImageViewWithUrl(context,icon, top_left_icon,  R.drawable.default_bg);
            } else {
                top_left_icon.setVisibility(GONE);
            }
            //
            if (GeneralUtils.isNotNullOrZeroLenght(GsonHelper.toJson(this.columnBean))) {
                six_gv.setAdapter(new FourViewAdapter(context,mColumnBean));
                topView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(context, ColumnListActy.class);
//                        intent.putExtra(IntentCode.SEARCH_KEYORD, mColumnBean.getColumnID());
//                        intent.putExtra(IntentCode.EXTRA_SERVICETYPE, mColumnBean.getServiceType());
//                        intent.putExtra(IntentCode.SEARCH_TITLE, title_tv.getText().toString());
//                        context.startActivity(intent);
                    }
                });
            } else {
                setVisibility(GONE);
            }
        }
    }


}
