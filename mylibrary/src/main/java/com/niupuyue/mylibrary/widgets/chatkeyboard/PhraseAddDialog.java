package com.niupuyue.mylibrary.widgets.chatkeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.renrui.job.R;
import com.renrui.job.util.UtilityException;

/**
 * 常用语
 */
public class PhraseAddDialog extends DialogFragment {

    private EditText mEtPhrase;
    private OnAddPhraseListener mOnAddPhraseListener;

    public interface OnAddPhraseListener {
        void OnAddPhrase(String phrase);
    }

    public void setOnAddPhraseListener(OnAddPhraseListener onAddPhraseListener) {
        this.mOnAddPhraseListener = onAddPhraseListener;
    }

    private View.OnClickListener dialogCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener addPhrase = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String content = mEtPhrase.getText().toString().trim();
            if (null != mOnAddPhraseListener && !TextUtils.isEmpty(content)) {
                mOnAddPhraseListener.OnAddPhrase(content);
            }
            dismiss();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getDialog().setCanceledOnTouchOutside(true);

        View root = inflater.inflate(R.layout.fragment_phrase_add, container, false);
        mEtPhrase = root.findViewById(R.id.etPhrase);
        TextView tvConfirm = root.findViewById(R.id.tvConfirm);
        TextView tvCancel = root.findViewById(R.id.tvCancel);

        tvConfirm.setOnClickListener(addPhrase);
        tvCancel.setOnClickListener(dialogCancel);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (getDialog() != null) {
                Window window = getDialog().getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    window.setBackgroundDrawableResource(android.R.color.transparent);
                }
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            UtilityException.catchException(e);
        }
    }

    @Override
    public void dismiss() {
        try {
            dismissAllowingStateLoss();
        } catch (Exception e) {
            UtilityException.catchException(e);
        }
    }
}