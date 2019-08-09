package com.niupuyue.iyinglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.utils.AndroidUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.utils.LoggerUtility;
import com.niupuyue.mylibrary.utils.NotchScreenUtility;
import com.niupuyue.mylibrary.utils.TimeUtility;
import com.niupuyue.mylibrary.widgets.SimpleDialog;
import com.niupuyue.mylibrary.widgets.datepicker.CustomDatePicker;

import java.sql.Time;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private Button btn01;
    private CustomDatePicker mTimerPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTimerPicker();
        btn01 = findViewById(R.id.btn01);
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTimerPicker.show(System.currentTimeMillis());
                Log.e("NPL", TimeUtility.getDayStartTime(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一天的结束", TimeUtility.getDayEndTime(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一周的开始", TimeUtility.getDayStarByWeek(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一周的结束", TimeUtility.getDayEndByWeek(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一月的开始", TimeUtility.getDayStarByMonth(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一月的结束", TimeUtility.getDayEndByMonth(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一年的开始", TimeUtility.getDayStarByYear(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                Log.e("NPL,一年的结束", TimeUtility.getDayEndByYear(TimeUtility.getCalendar(System.currentTimeMillis())).getTimeInMillis() + "");
                if (NotchScreenUtility.isNotchSupportVersion()) {
                    if (NotchScreenUtility.isNotch(MainActivity.this)) {
                        if (AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Huawei) {
                            //如果是华为手机
                            int[] size = NotchScreenUtility.getNotchSizeForHuawei(MainActivity.this);
                            LoggerUtility.e("NPL", "华为手机刘海屏的宽度是 = " + size[0]);
                            LoggerUtility.e("NPL", "华为手机刘海屏的高度是 = " + size[1]);
                        } else if (AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Xiaomi) {
                            // 如果是小米手机
                            int[] size = NotchScreenUtility.getNotchSizeForXiaomi(MainActivity.this);
                            LoggerUtility.e("NPL", "小米手机刘海屏的宽度是 = " + size[0]);
                            LoggerUtility.e("NPL", "小米手机刘海屏的高度是 = " + size[1]);
                        } else if (AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Vivo || AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Oppo) {
                            int height = NotchScreenUtility.getNotchHeight(MainActivity.this);
                            LoggerUtility.e("NPL", "OPPO或者VIVO手机刘海屏的高度是 = " + height);
                        }
                    }
                }
            }
        }, btn01);
        Log.e("NPL", String.valueOf(TimeUtility.getDay(System.currentTimeMillis())));
    }

    private void showSimpleDialog() {
        SimpleDialog.showSimpleDialog(MainActivity.this, "", "内容", "取消", "确定", Gravity.CENTER, false, new ISimpleDialogButtonClickCallback() {
            @Override
            public void onLeftButtonClick() {
                Log.e("NPL", "左边");
            }

            @Override
            public void onRightButtonClick() {
                Log.e("NPL", "右边");
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = TimeUtility.convertToString(System.currentTimeMillis(), TimeUtility.FORMAT_YYYY_MM_DD_HH_MM);

        Log.e("NPL", endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(MainActivity.this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                Log.e("NPL", TimeUtility.convertToString(timestamp, TimeUtility.FORMAT_YYYY_MM_DD_HH_MM));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }
}
