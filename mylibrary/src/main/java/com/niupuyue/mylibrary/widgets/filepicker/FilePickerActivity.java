package com.niupuyue.mylibrary.widgets.filepicker;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.FileUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.filepicker.callback.IYFilePickerCallback;
import com.niupuyue.mylibrary.widgets.filepicker.filter.IYFileFilter;
import com.niupuyue.mylibrary.widgets.filepicker.widget.EmptyRecycler;

import java.io.File;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/6/25
 * Time: 10:53
 * Desc:
 * Version:
 */
public class FilePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = FilePickerActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mTvCurrentPath;
    private TextView mTvBack;
    private TextView mTvConfirm;
    private Toolbar mToolbar;

    private IYFilePickerCallback mChooseFileCallback;

    private String mPath;
    private List<File> mListFiles;
    private List<String> mChooseFilePathes;
    private FilePickerModel mModel;
    private IYFileFilter mFileFilter;
    private boolean isSelectedAll = false;
    private Menu mMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initExtras();
        setTheme(R.style.IYFilePickerTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        initViewByFindViewById();
        initViewBySetListener();
        initSupportActionBar();
        if (!checkSDState()) {
            CustomToastUtility.makeTextError("外部存储设备不可用");
            return;
        }
        // 获取开始路径
        if (mModel != null) {
            mPath = mModel.getStartPath();
        }
        BaseUtility.setText(mTvCurrentPath, mPath);
        mFileFilter = new IYFileFilter(mModel.getFilters());
        mListFiles = FileUtility.getFileList(mPath, mFileFilter, mModel.getTargetSize());

        initListenerAfterInitData();
    }

    private void initExtras() {
        if (getIntent() == null || getIntent().getExtras() == null) {
            return;
        }
        mModel = (FilePickerModel) getIntent().getExtras().getSerializable("params");
//        mChooseFileCallback = mModel.getCallback();
    }

    private void initViewByFindViewById() {
        mRecyclerView = findViewById(R.id.rvFilePickerRecyclerView);
        mEmptyView = findViewById(R.id.viewFilePickerEmptyView);
        mTvCurrentPath = findViewById(R.id.tvFilePickerCurrentPath);
        mTvBack = findViewById(R.id.tvFilePickerBack);
        mToolbar = findViewById(R.id.toolbar);
        mTvConfirm = findViewById(R.id.tvFilePickerComfirm);

        initView();
    }

    private void initView() {
        if (mModel == null) return;
        BaseUtility.resetVisibility(mTvConfirm, mModel.isMutilyMode());
    }

    private void initViewBySetListener() {
        ListenerUtility.setOnClickListener(mTvBack, this);
        ListenerUtility.setOnClickListener(mTvCurrentPath, this);
        ListenerUtility.setOnClickListener(mTvConfirm, this);
    }

    private void initSupportActionBar() {
        if (mModel == null || mToolbar == null) return;

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 设置标题
        if (mModel == null || mToolbar == null) return;
        if (!BaseUtility.isEmpty(mModel.getTitle())) {
            mToolbar.setTitle(mModel.getTitle());
        }
        // 设置标题颜色
        if (mModel.getTitleColor() > 0) {
            mToolbar.setTitleTextColor(mModel.getTitleColor());
        }
        // 点击关闭当前页面
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListenerAfterInitData() {

    }

    /**
     * 检测SD卡是否可用
     */
    private boolean checkSDState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    public void onClick(View v) {
        if (v == null) return;
        if (v.getId() == R.id.tvFilePickerBack) {
            // 返回上级目录
        } else if (v.getId() == R.id.tvFilePickerCurrentPath) {
            // 回到默认根目录
        } else if (v.getId() == R.id.tvFilePickerComfirm) {
            // 选择选中的文件
            if (null != mChooseFileCallback && !BaseUtility.isEmpty(mChooseFilePathes)) {
                mChooseFileCallback.onChooseFiles(mChooseFilePathes);
            }
        }
    }
}
