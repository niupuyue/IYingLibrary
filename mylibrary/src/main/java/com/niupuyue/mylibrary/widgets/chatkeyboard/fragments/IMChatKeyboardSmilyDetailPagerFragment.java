package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.renrui.job.R;
import com.renrui.job.util.UtilityException;
import com.renrui.job.widget.im.chatkeyboard.adapter.IMChatKeyboardEmojiAdapter;
import com.renrui.job.widget.im.chatkeyboard.adapter.IMChatKeyboardEmojiViewHolder;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMChatEmojiClickCallback;
import com.renrui.job.widget.im.chatkeyboard.models.IMExpressionModel;
import com.renrui.job.widget.im.chatkeyboard.utils.UtilityExpression;
import com.renrui.libraries.util.UtilitySecurity;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: 表情详情页面
 */
public class IMChatKeyboardSmilyDetailPagerFragment extends BaseIMFragment implements IMChatKeyboardEmojiViewHolder.onExpressionClickLinstener {

    private static final int OFFSET_EMOJI_NOT_FULL = 2;
    private static final int COUNT_EMOJI_FULL = 27;
    private static final int FAKE_EMOJI_RESID = -1;

    private View root;
    private RecyclerView rvSdpfSmilyContainer;
    private List<IMExpressionModel> IMExpressionModels;
    private IMChatKeyboardEmojiAdapter emojiAdapter;
    private IMChatEmojiClickCallback emojiClickListener;

    @Override
    public int getIMLayoutId() {
        return R.layout.fragment_smily_detail_pager;
    }

    @Override
    public void initViewFindViewById(View root) {
        try {
            if (root != null) {
                rvSdpfSmilyContainer = root.findViewById(R.id.rvFsdpSmilyContainer);
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }


    /**
     * 初始化数据
     */
    public void initDataAfterInitLayout() {
        if (UtilitySecurity.isEmpty(IMExpressionModels)) return;
        try {
            if (IMExpressionModels.size() != COUNT_EMOJI_FULL) {
                int addCount = (IMExpressionModels.size() - OFFSET_EMOJI_NOT_FULL) % IMChatKeyboardSmilyFragment.RECYCLER_VIEW_SPAN_COUNT;
                for (int i = 0; i < addCount; i++) {
                    IMExpressionModel IMExpressionModelEmpty = new IMExpressionModel("", FAKE_EMOJI_RESID);
                    IMExpressionModels.add(IMExpressionModelEmpty);
                }
            }
            // 需要创建删除表情按钮
            IMExpressionModel IMExpressionModelDelete = new IMExpressionModel(UtilityExpression.EMOJI_TYPE_DELETE, R.drawable.aliwx_shanchu_nm);
            IMExpressionModels.add(IMExpressionModelDelete);
            emojiAdapter = new IMChatKeyboardEmojiAdapter(getContext(), IMExpressionModels);
            emojiAdapter.setClickListener(this);
            rvSdpfSmilyContainer.setAdapter(emojiAdapter);
            rvSdpfSmilyContainer.setLayoutManager(new GridLayoutManager(getContext(), IMChatKeyboardSmilyFragment.RECYCLER_VIEW_SPAN_COUNT));
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    public void setData(List<IMExpressionModel> IMExpressionModels, IMChatEmojiClickCallback listener) {
        if (listener == null) return;
        try {
            if (!UtilitySecurity.isEmpty(IMExpressionModels)) {
                this.IMExpressionModels = IMExpressionModels;
            } else {
                IMExpressionModels = new ArrayList<>();
            }
            this.emojiClickListener = listener;
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 表情点击事件
     *
     * @param position
     */
    @Override
    public void onClick(int position) {
        if (emojiClickListener == null) return;
        try {
            emojiClickListener.onEmojiClick(UtilityExpression.EMOJI_TYPE_CLASSICS, IMExpressionModels.get(position).getName());
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }
}
