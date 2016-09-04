package cn.nj.www.my_module.view.birthdate;//package com.zhongchao.mall.view.birthdate;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.zhongchao.mall.R;
//
//public class MainActivity extends AppCompatActivity {
//    private MyYearView myYearView;
//    private MyMonthView myMonthView;
//    private MyDayView myDayView;
//    private int year;
//    private int month;
//    private int day;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        myYearView=(MyYearView)findViewById(R.id.year);
//        myMonthView=(MyMonthView)findViewById(R.id.month);
//        myDayView=(MyDayView)findViewById(R.id.day);
//
//        myMonthView.setMonthListener(new MyMonthView.MonthScollListener() {
//            @Override
//            public void monthScoll() {
//                initDay();
//            }
//        });
//        myYearView.setYearListener(new MyYearView.YearScollListener() {
//            @Override
//            public void yearScoll() {
//                initDay();
//            }
//        });
//    }
//    private void initDay(){
//        if (myMonthView.getTime().equals("1月")||myMonthView.getTime().equals("3月")
//                ||myMonthView.getTime().equals("5月")||myMonthView.getTime().equals("7月")
//                ||myMonthView.getTime().equals("8月")||myMonthView.getTime().equals("10月")||myMonthView.getTime().equals("12月")){
//            myDayView.initData(31);
//        }else {
//            myDayView.initData(30);
//            if (myMonthView.getTime().equals("2月")){
//                String stringYear=myYearView.getTime();
//                year= Integer.valueOf(stringYear.substring(0,stringYear.length()-1));
//                if (year%4==0){
//                    myDayView.initData(29);
//                }else {
//                    myDayView.initData(28);
//                }
//            }
//        }
//    }
//
//}
