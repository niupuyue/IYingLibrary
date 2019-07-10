package com.niupuyue.iyinglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;


import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.SimpleDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn01 = findViewById(R.id.btn01);
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        }, btn01);
    }
}
