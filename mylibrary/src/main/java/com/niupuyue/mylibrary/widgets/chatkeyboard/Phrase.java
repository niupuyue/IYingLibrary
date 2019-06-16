package com.niupuyue.mylibrary.widgets.chatkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.renrui.job.R;
import com.renrui.job.application.RRApplication;
import com.renrui.job.constant.AppConstant;
import com.renrui.job.im.event.OnPhraseItemClickEvent;
import com.renrui.job.util.UtilityException;
import com.renrui.libraries.util.CustomToast;
import com.renrui.libraries.util.UtilitySecurity;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 常用语
 */
public class Phrase extends FragmentActivity implements PhraseAddDialog.OnAddPhraseListener, AdapterView.OnItemClickListener {
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REMOVE = 1;

    public static final String FILE_TAG = Phrase.class.getSimpleName();
    private static final String HR_TAG = "hrPhrase";
    private static final String HUNTER_TAG = "hunterPhrase";
    private static final String INIT = "init";

    private LinkedList<String> mMemoryPhrases = new LinkedList<>();
    private PhraseAdapter mPhraseAdapter;
    private int state = 0;

    private ImageView ivRemoveState;
    private ImageView ivAddState;
    private TextView tvRemoveState;
    private TextView tvPopCancel;
    private RelativeLayout rlContainer;
    private ListView lvPhrase;

    private View.OnClickListener popCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener entryRemoveState = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (STATE_NORMAL == state) {
                state = STATE_REMOVE;
                UtilitySecurity.resetVisibility(tvRemoveState, View.VISIBLE);
                UtilitySecurity.resetVisibility(ivRemoveState, View.GONE);
                UtilitySecurity.resetVisibility(ivAddState, View.INVISIBLE);
            }
            mPhraseAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener exitRemoveState = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (STATE_REMOVE == state) {
                state = STATE_NORMAL;
                UtilitySecurity.resetVisibility(tvRemoveState, View.GONE);
                UtilitySecurity.resetVisibility(ivAddState, View.VISIBLE);
                UtilitySecurity.resetVisibility(ivRemoveState, View.VISIBLE);
            }
            mPhraseAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener showAddDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhraseAddDialog dialog = new PhraseAddDialog();
            dialog.setOnAddPhraseListener(Phrase.this);
            dialog.show(getSupportFragmentManager(), PhraseAddDialog.class.getSimpleName());
        }
    };

    @Override
    public void OnAddPhrase(String phrase) {
        addPhrase(phrase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phrase);
        initPhrase();
        initView();
        initListener();
    }

    private void initPhrase() {
        initLocalPhrase();
        getPhraseFromLocal();
    }

    private void initLocalPhrase() {
        try {
            SharedPreferences preferences = getSharedPreferences(FILE_TAG, MODE_PRIVATE);
            if (!preferences.contains(INIT)) {
                LinkedList<String> hunterPhrases = new LinkedList<>();
                String[] hunterArray = RRApplication.getAppContext().getResources().getStringArray(R.array.hunterPhrases);
                Collections.addAll(hunterPhrases, hunterArray);
                writePhraseToLocal(HUNTER_TAG, hunterPhrases);

                LinkedList<String> hrPhrases = new LinkedList<>();
                String[] hrArray = RRApplication.getAppContext().getResources().getStringArray(R.array.hrPhrases);
                Collections.addAll(hrPhrases, hrArray);
                writePhraseToLocal(HR_TAG, hrPhrases);
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    private void initView() {
        //initialized pop contentView
        tvPopCancel = findViewById(R.id.tvCancel);
        ivRemoveState = findViewById(R.id.ivRemoveState);
        tvRemoveState = findViewById(R.id.tvRemoveState);
        ivAddState = findViewById(R.id.ivAddState);
        lvPhrase = findViewById(R.id.lvPhrase);
        rlContainer = findViewById(R.id.rlContainer);
        mPhraseAdapter = new PhraseAdapter();
        lvPhrase.setAdapter(mPhraseAdapter);
    }

    private void initListener() {
        tvPopCancel.setOnClickListener(popCancel);
        ivRemoveState.setOnClickListener(entryRemoveState);
        tvRemoveState.setOnClickListener(exitRemoveState);
        ivAddState.setOnClickListener(showAddDialog);
        lvPhrase.setOnItemClickListener(this);
        rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addPhrase(String phrase) {
        if (!mMemoryPhrases.contains(phrase)) {
            mMemoryPhrases.addFirst(phrase);
            mPhraseAdapter.notifyDataSetChanged();
            writePhraseToLocal(mMemoryPhrases);
        }
    }

    private void writePhraseToLocal(String key, LinkedList<String> phrases) {
        SharedPreferences preferences = getSharedPreferences(FILE_TAG, Context.MODE_PRIVATE);
        String phrasesStr = new Gson().toJson(phrases);
        preferences.edit().putString(key, phrasesStr).putString(INIT, INIT).apply();
    }

    private void writePhraseToLocal(LinkedList<String> phrases) {
        SharedPreferences preferences = getSharedPreferences(FILE_TAG, Context.MODE_PRIVATE);
        String phrasesStr = new Gson().toJson(phrases);
        if (AppConstant.getUserInfo().isHr()) {
            preferences.edit().putString(HR_TAG, phrasesStr).putString(INIT, INIT).apply();
        } else {
            preferences.edit().putString(HUNTER_TAG, phrasesStr).putString(INIT, INIT).apply();
        }
    }

    private void getPhraseFromLocal() {
        SharedPreferences preferences = getSharedPreferences(FILE_TAG, Context.MODE_PRIVATE);
        String phraseStr;
        if (AppConstant.getUserInfo().isHr()) {
            phraseStr = preferences.getString(HR_TAG, "");
        } else {
            phraseStr = preferences.getString(HUNTER_TAG, "");
        }
        Type type = new TypeToken<LinkedList<String>>() {
        }.getType();
        mMemoryPhrases = new Gson().fromJson(phraseStr, type);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (STATE_NORMAL == state) {
            OnPhraseItemClickEvent clickEvent = new OnPhraseItemClickEvent();
            clickEvent.phrase = mMemoryPhrases.get(position);
            EventBus.getDefault().post(clickEvent);
            finish();
        }
    }

    private class PhraseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (null == mMemoryPhrases) {
                return 0;
            } else {
                return mMemoryPhrases.size();
            }
        }

        @Override
        public String getItem(int position) {
            return mMemoryPhrases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            PhraseAdapterHolder holder;

            try {
                if (null == convertView) {
                    holder = new PhraseAdapterHolder();
                    convertView = View.inflate(getApplicationContext(), R.layout.item_phrase, null);
                    holder.tvPhrase = convertView.findViewById(R.id.tvPhrase);
                    holder.ivRemove = convertView.findViewById(R.id.ivRemove);
                    holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (STATE_REMOVE == state) {
                                try {
                                    mMemoryPhrases.remove(position);
                                    notifyDataSetChanged();
                                    writePhraseToLocal(mMemoryPhrases);
                                } catch (Exception e) {
                                    CustomToast.makeTextError("删除失败，请尝试重新删除");
                                }
                            }
                        }
                    });
                    convertView.setTag(holder);
                } else {
                    holder = (PhraseAdapterHolder) convertView.getTag();
                }

                if (state == STATE_NORMAL) {
                    UtilitySecurity.resetVisibility(holder.ivRemove, View.GONE);
                } else {
                    UtilitySecurity.resetVisibility(holder.ivRemove, View.VISIBLE);
                }

                UtilitySecurity.setText(holder.tvPhrase, String.valueOf(position + 1 + "." + getItem(position)));
            } catch (Exception ex) {
                UtilityException.catchException(ex);
            }

            return UtilitySecurity.getView(convertView, this.getClass());
        }
    }

    private class PhraseAdapterHolder {
        private TextView tvPhrase;
        private ImageView ivRemove;
    }
}
