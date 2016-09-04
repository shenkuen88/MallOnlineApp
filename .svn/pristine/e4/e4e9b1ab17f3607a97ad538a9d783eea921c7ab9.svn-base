package cn.nj.www.my_module.bean;

import android.app.Dialog;
import android.widget.TextView;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.view.birthdate.MyDayView;
import cn.nj.www.my_module.view.birthdate.MyMonthView;
import cn.nj.www.my_module.view.birthdate.MyYearView;
import cn.nj.www.my_module.view.wheel.widget.WheelView;


public class NoticeDialogInfoEvent extends BaseResponse
{
    private String tag;

    private String text;

    private Dialog mDialog;


    private TextView tvComfirm;
    private TextView tvCancel;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewArea;
    private MyYearView myYearView;
    private MyMonthView myMonthView;
    private MyDayView myDayView;

    public NoticeDialogInfoEvent(String tag, String text, Dialog mDialog) {
        this.setTag(tag);
        this.setText(text);
        setmDialog(mDialog);
    }

    public NoticeDialogInfoEvent(String tag, Dialog dialog, WheelView mViewProvince, WheelView mViewCity, WheelView mViewArea, TextView tvCancel, TextView tvComfirm) {
        this.setTag(tag);
        this.mViewProvince = mViewProvince;
        setmDialog(dialog);
        this.mViewCity = mViewCity;
        setTvComfirm(tvComfirm);
        setTvCancel(tvCancel);
        setmViewArea(mViewArea);
    }

    public NoticeDialogInfoEvent(String tag, Dialog dialog, MyYearView mViewProvince, MyMonthView mViewCity, MyDayView mViewArea) {
        this.setTag(tag);
        setmDialog(dialog);
        setTvComfirm(tvComfirm);
        setTvCancel(tvCancel);
        setMyYearView(myYearView);
        setMyMonthView(myMonthView);
        setMyDayView(myDayView);
    }

    public MyYearView getMyYearView() {
        return myYearView;
    }

    public void setMyYearView(MyYearView myYearView) {
        this.myYearView = myYearView;
    }

    public MyMonthView getMyMonthView() {
        return myMonthView;
    }

    public void setMyMonthView(MyMonthView myMonthView) {
        this.myMonthView = myMonthView;
    }

    public MyDayView getMyDayView() {
        return myDayView;
    }

    public void setMyDayView(MyDayView myDayView) {
        this.myDayView = myDayView;
    }

    public TextView getTvComfirm() {
        return tvComfirm;
    }

    public void setTvComfirm(TextView tvComfirm) {
        this.tvComfirm = tvComfirm;
    }

    public void setTvCancel(TextView tvCancel) {
        this.tvCancel = tvCancel;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public WheelView getmViewCity() {
        return mViewCity;
    }

    public void setmViewCity(WheelView mViewCity) {
        this.mViewCity = mViewCity;
    }

    public WheelView getmViewProvince() {
        return mViewProvince;
    }

    public void setmViewProvince(WheelView mViewProvince) {
        this.mViewProvince = mViewProvince;
    }

    public WheelView getmViewArea() {
        return mViewArea;
    }

    public void setmViewArea(WheelView mViewArea) {
        this.mViewArea = mViewArea;
    }

    public Dialog getmDialog() {
        return mDialog;
    }

    public void setmDialog(Dialog mDialog) {
        this.mDialog = mDialog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
