package cn.nj.www.my_module.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.NoticeDialogInfoEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.view.birthdate.MyDayView;
import cn.nj.www.my_module.view.birthdate.MyMonthView;
import cn.nj.www.my_module.view.birthdate.MyYearView;
import cn.nj.www.my_module.view.wheel.widget.WheelView;
import de.greenrobot.event.EventBus;

/**
 * <弹出框公共类> <功能详细描述>
 *
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialogUtil {

    public static void showNoTipTwoBnttonDialog(Context context, String title, String left, String right, final String leftTag, final String rightTag) {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.person_save_barcode_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvDialogName = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button leftBn = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        Button rightBn = (Button) dialog.findViewById(R.id.transfer_cancel_bn);

        tvDialogName.setText(title);
        leftBn.setText(left);
        rightBn.setText(right);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setWindowAnimations(R.style.main_dialog);
        leftBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EventBus.getDefault().post(new NoticeEvent(leftTag));
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NoticeEvent(rightTag));
                dialog.dismiss();
            }
        });
    }

    public static void exitAccountDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.person_save_barcode_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvDialogName = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button leftBn = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        Button rightBn = (Button) dialog.findViewById(R.id.transfer_cancel_bn);

        tvDialogName.setText("确定退出登录");
        leftBn.setText("取消");
        rightBn.setText("确定");
        rightBn.setTextColor(context.getResources().getColor(R.color.app_title_blue1));
        leftBn.setTextColor(context.getResources().getColor(R.color.app_state_red1));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setWindowAnimations(R.style.main_dialog);
        leftBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Global.loginOut(context);
                dialog.dismiss();
            }
        });
    }



    /**
     * 省市选择的Dialog
     */
    public static void chooseCityDialog(final Context context, final String dialogTag) {
        final Dialog dialog = initDialog(context, R.layout.dialog_location);
        WheelView provinceWV = (WheelView) dialog.findViewById(R.id.id_province);
        WheelView cityWV = (WheelView) dialog.findViewById(R.id.id_city);
        WheelView arwaWV = (WheelView) dialog.findViewById(R.id.id_district);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.location_cancel_tv);
        TextView tvComfirm = (TextView) dialog.findViewById(R.id.location_comfirm_tv);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        EventBus.getDefault().post(new NoticeDialogInfoEvent(dialogTag, dialog, provinceWV, cityWV, arwaWV, tvCancel, tvComfirm));
    }

    /**
     * 日期选择的Dialog
     */
    public static void chooseDateDialog(final Context context, final String dialogTag) {
        final Dialog dialog = initDialog(context, R.layout.dialog_birth_time);
        final MyYearView yearWV = (MyYearView) dialog.findViewById(R.id.year_wv);
        final MyMonthView monthWV = (MyMonthView) dialog.findViewById(R.id.month_wv);
        final MyDayView dayWV = (MyDayView) dialog.findViewById(R.id.day_wv);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.location_cancel_tv);
        TextView tvComfirm = (TextView) dialog.findViewById(R.id.location_comfirm_tv);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String dateStr = yearWV.getTime()+ "-";
                if (monthWV.getTime().length() == 1) {
                    dateStr = dateStr + "0" + monthWV.getTime()+ "-";
                } else {
                    dateStr = dateStr + monthWV.getTime()+ "-";
                }
                if (dayWV.getTime().length() == 1) {
                    dateStr = dateStr + "0" + dayWV.getTime();
                } else {
                    dateStr += dayWV.getTime();
                }
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHOOSE_TIME_OK, dateStr));
            }
        });
        EventBus.getDefault().post(new NoticeDialogInfoEvent(dialogTag, dialog, yearWV, monthWV, dayWV));
    }


    public static Dialog initDialog(Context context, int layout) {
        Dialog oneButtonDialog = new Dialog(context, R.style.from_bottom_dialog);
        oneButtonDialog.setContentView(layout);
        oneButtonDialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        oneButtonDialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        oneButtonDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        oneButtonDialog.getWindow().setWindowAnimations(R.style.dialog_style);
        return oneButtonDialog;
    }

}
