package com.niupuyue.iyinglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.niupuyue.mylibrary.widgets.filepicker.IYFilePicker;
import com.niupuyue.mylibrary.widgets.filepicker.callback.IYFilePickerCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn01 = findViewById(R.id.btn01);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IYFilePicker().with(MainActivity.this)
                        .withFileFilter(new String[]{"doc", "docx"})
                        .withMutilyMode(false)
                        .withTitle("文件遍历")
                        .withFilePickerCallback(new IYFilePickerCallback() {
                            @Override
                            public void onChooseFiles(List<String> filePathes) {
                                Log.e("npl", filePathes.toString());
                            }
                        })
                        .start();
            }
        });
    }
}
