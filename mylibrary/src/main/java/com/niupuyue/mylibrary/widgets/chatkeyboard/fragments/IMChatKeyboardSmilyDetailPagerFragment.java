package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.view.View;


import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.widgets.chatkeyboard.adapter.IMChatKeyboardEmojiAdapter;
import com.niupuyue.mylibrary.widgets.chatkeyboard.adapter.IMChatKeyboardEmojiViewHolder;
import com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks.IMChatEmojiClickCallback;
import com.niupuyue.mylibrary.widgets.chatkeyboard.models.IMExpressionModel;
import com.niupuyue.mylibrary.widgets.chatkeyboard.utils.UtilityExpression;

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
            ex.printStackTrace();
        }
    }


    /**
     * 初始化数据
     */
    public void initDataAfterInitLayout() {
        if (BaseUtility.isEmpty(IMExpressionModels)) return;
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
            ex.printStackTrace();
        }
    }

    public void setData(List<IMExpressionModel> IMExpressionModels, IMChatEmojiClickCallback listener) {
        if (listener == null) return;
        try {
            if (!BaseUtility.isEmpty(IMExpressionModels)) {
                this.IMExpressionModels = IMExpressionModels;
            } else {
                IMExpressionModels = new ArrayList<>();
            }
            this.emojiClickListener = listener;
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }
}
