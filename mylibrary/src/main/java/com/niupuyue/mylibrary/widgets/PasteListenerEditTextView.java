package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Desc: 自定义输入框，为了实现监听剪切板复制粘贴操作
 */
public class PasteListenerEditTextView extends AppCompatEditText {

    /**
     * 剪切板操作接口
     */
    public interface OnClipKeyboardDelegateCallback {

        void onPaste();
    }

    private Context mContext;
    private OnClipKeyboardDelegateCallback mClipKeyboardCallback;

    public PasteListenerEditTextView(Context context) {
        super(context);
    }

    public PasteListenerEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasteListenerEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setClipKeyboardCallback(OnClipKeyboardDelegateCallback callback) {
        this.mClipKeyboardCallback = callback;
    }

    /**
     * 输入框菜单操作 包括了复制 粘贴等
     *
     * @param id
     * @return
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            // 粘贴
            if (mClipKeyboardCallback != null) {
                mClipKeyboardCallback.onPaste();
            }
        }
        return super.onTextContextMenuItem(id);
    }
}
