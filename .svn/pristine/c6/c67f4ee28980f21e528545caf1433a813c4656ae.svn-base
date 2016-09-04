package com.nannong.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nannong.mall.R;
import com.nannong.mall.activity.index.PublicTeamBuyActy;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.tools.V;


/**
 * 自定义Dialog
 */
public class BottomDialog extends Dialog {
    private String TAG = "BottomDialog";
    private ListView my_listview;
    public static CommonAdapter<PublicTeamBuyActy.LocationBean> mAdapter;

    public BottomDialog(Activity ctx, int width, int height) {
        this(ctx, ctx.getLayoutInflater().inflate(R.layout.bottom_item_dialog, null), width, height);
    }

    public BottomDialog(Activity ctx, View view, int width, int height) {
        this(ctx, view, width, height, R.style.BottomDialog);
    }

    public BottomDialog(final Activity ctx, View view, int width, int height, int style) {
        super(view.getContext(), style);
        // // 透明背景
        // Drawable myDrawable =
        // context.getResources().getDrawable(R.drawable.dialog_root_bg);
        // myDrawable.setAlpha(150);
        // view.setBackgroundDrawable(myDrawable);
        setContentView(view);
        my_listview = V.f(view, R.id.my_listview);
        Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // params.height = (int) (default_height * density);
        // view.measure(0, 0);
        // params.width = view.getMeasuredWidth();
        // params.height = view.getMeasuredHeight();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.BOTTOM);
        if (ctx instanceof PublicTeamBuyActy) {
            mAdapter = new CommonAdapter<PublicTeamBuyActy.LocationBean>(ctx, PublicTeamBuyActy.locationBeans, R.layout.loca_item) {
                @Override
                public void convert(ViewHolder helper, PublicTeamBuyActy.LocationBean item) {
                    helper.setText(R.id.loca_name, item.getLocationName());
                }
            };
            my_listview.setAdapter(mAdapter);

            my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PublicTeamBuyActy.LocationBean locationBean = (PublicTeamBuyActy.LocationBean) parent.getItemAtPosition(position);
                    PublicTeamBuyActy.tvLocation.setText(locationBean.getLocationName());
                }
            });
        }
        // 添加动画
//		 window.setWindowAnimations(R.style.popup_in_out);
    }

    /**
     * 为指定id控件设置点击监听
     *
     * @param id
     * @param listener
     */
    public BottomDialog setOnClickListener(int id, View.OnClickListener listener) {
        View view = findViewById(id);
        if (view != null && listener != null)
            view.setOnClickListener(listener);
        return this;
    }

    public void setOutsideTouchable(boolean touchable) {
    }

    public void setBackgroundDrawable(Drawable background) {
    }

    public void setAnimationStyle(int animationStyle) {
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        show();
    }
}
