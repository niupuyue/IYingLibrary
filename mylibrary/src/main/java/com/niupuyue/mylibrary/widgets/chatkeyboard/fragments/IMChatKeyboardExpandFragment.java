package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.renrui.job.R;
import com.renrui.job.activity.hr.HrAddressChooseActivity;
import com.renrui.job.constant.RequestJobPermissions;
import com.renrui.job.im.event.OnSendLocationEvent;
import com.renrui.job.im.model.content.LocationContent;
import com.renrui.job.util.CheckRecordPermission;
import com.renrui.job.util.UtilityException;
import com.renrui.job.util.UtilityJobPermission;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMChatSendMessageCallback;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMShowRecordViewCallback;
import com.renrui.libraries.util.LibUtility;
import com.renrui.libraries.util.UtilityPermission;
import com.renrui.libraries.util.UtilitySecurityListener;

import org.greenrobot.eventbus.Subscribe;


/**
 * Desc: 更多页面
 */
public class IMChatKeyboardExpandFragment extends BaseIMFragment implements
        View.OnClickListener {

    public static IMChatKeyboardExpandFragment getInstance() {
        IMChatKeyboardExpandFragment fragment = new IMChatKeyboardExpandFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private View root;
    private IMShowRecordViewCallback mShowRecorderUIListener;
    private IMChatSendMessageCallback mSendMessageListener;
    private AppCompatActivity mActivity;

    private ImageView ivFeAlbum;
    private ImageView ivFePhoto;
    private ImageView ivFePosition;
    private ImageView ivFeRecorder;

    @Override
    public int getIMLayoutId() {
        return R.layout.fragment_expand;
    }

    @Override
    public void initViewFindViewById(View root) {
        if (root == null) return;
        try {
            ivFeAlbum = root.findViewById(R.id.ivFeAlbum);
            ivFePhoto = root.findViewById(R.id.ivFePhoto);
            ivFePosition = root.findViewById(R.id.ivFePosition);
            ivFeRecorder = root.findViewById(R.id.ivFeRecorder);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    public void initViewListener() {
        UtilitySecurityListener.setOnClickListener(ivFeAlbum, this);
        UtilitySecurityListener.setOnClickListener(ivFePhoto, this);
        UtilitySecurityListener.setOnClickListener(ivFePosition, this);
        UtilitySecurityListener.setOnClickListener(ivFeRecorder, this);
    }

    /**
     * 发送定位
     *
     * @param activity 上下文对象
     */
    private void gotoSetLocation(Activity activity) {
        // 调用高德地图获取当前位置，发送消息
        try {
            Intent intent = HrAddressChooseActivity.getIntent(activity, null, HrAddressChooseActivity.EXTRA_LOCATION_TYPE_SETLOCATION);
            activity.startActivity(intent);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 发送语音消息操作方法
     */
    private void recorderOperation() {
        // 执行语音操作之前先自己起一个recorder，判断是否有权限，有执行下一步操作，没有则不执行
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final String[] arrRequestPermission = UtilityPermission.getRequestPermission(getActivity(), Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (arrRequestPermission.length > 0) {
                    ActivityCompat.requestPermissions(getActivity(), arrRequestPermission, RequestJobPermissions.REQUEST_CODE_Open_Record);
                } else {
                    replaceExpandContainer();
                }
            } else {
                replaceExpandContainer();
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 设置显示录音页面的UI接口  设置发送消息的接口
     *
     * @param listener
     */
    public void setShowRecorderUIListener(IMShowRecordViewCallback listener, AppCompatActivity activity, IMChatSendMessageCallback callback) {
        if (listener == null || activity == null || callback == null) return;
        try {
            this.mActivity = activity;
            this.mShowRecorderUIListener = listener;
            this.mSendMessageListener = callback;
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 替换之前rlFeExpandContainer中的内容
     */
    public void replaceExpandContainer() {
        if (!CheckRecordPermission.isHasPermission(getContext())) return;
        if (mShowRecorderUIListener == null) return;
        try {
            mShowRecorderUIListener.showRecorderView();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 选择位置之后在此处发送定位消息
     *
     * @param event
     */
    @Subscribe
    public void onEvent(OnSendLocationEvent event) {
        if (event == null) return;
        try {
            final PoiItem poiItem = event.poiItem;
            String address = "";
            if (null != poiItem) {
                address = poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet();
            }

            // 发送定位信息
            if (mSendMessageListener == null) return;
            LocationContent locationContent = new LocationContent();
            locationContent.loc = address;
            locationContent.lat = poiItem.getLatLonPoint().getLatitude();
            locationContent.lon = poiItem.getLatLonPoint().getLongitude();
            mSendMessageListener.sendLocationMessage(locationContent);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public void onClick(View v) {
        if (LibUtility.isFastDoubleClick())
            return;
        switch (v.getId()) {
            case R.id.ivFeAlbum:
                // 相册
                UtilityJobPermission.toMultipleImage(getActivity(), false, 6, null);
                break;
            case R.id.ivFePhoto:
                // 拍照
                UtilityJobPermission.openCamera(getActivity(), LibUtility.dp2px(150), LibUtility.dp2px(150), false);
                break;
            case R.id.ivFePosition:
                // 点击之后跳转到定位页面
                gotoSetLocation(getActivity());
                break;
            case R.id.ivFeRecorder:
                // 语音消息
                recorderOperation();
                break;
        }
    }
}
