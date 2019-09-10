package com.niupuyue.iyinglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.niupuyue.mylibrary.base.IAdapter;
import com.niupuyue.mylibrary.base.PubAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IAdapter {

    private ListView listview;

    private PubAdapter adapter;

    private List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listview);

        adapter = new PubAdapter(this);
        for (int i = 0; i < 30; i++) {
            datas.add("第" + (i + 1) + "个数据");
        }
        adapter.setData(datas);
        adapter.setListener(this);
        adapter.setViewHolder(TestViewHolder.class);

        listview.setAdapter(adapter);
        adapter.isNone = false;
        adapter.isEnd = true;
        adapter.isEmpty = false;
        adapter.isLoadFaild = false;
    }

    @Override
    public void faildReload() {

    }

    @Override
    public void loadMore() {

    }
}
