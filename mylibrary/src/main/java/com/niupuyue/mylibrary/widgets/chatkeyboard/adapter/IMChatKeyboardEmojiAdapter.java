package com.niupuyue.mylibrary.widgets.chatkeyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.utils.ScreenUtility;
import com.niupuyue.mylibrary.widgets.chatkeyboard.fragments.IMChatKeyboardSmilyFragment;
import com.niupuyue.mylibrary.widgets.chatkeyboard.models.IMExpressionModel;
import com.niupuyue.mylibrary.widgets.chatkeyboard.utils.ScreenUtil;

import java.util.List;

/**
 * Desc:表情Adapter实现类
 */
public class IMChatKeyboardEmojiAdapter extends IMChatKeyboardEmojiBaseAdapter<IMExpressionModel> {
    private static int EMOJI_PADDING_DP = 8;

    private Context context;

    public IMChatKeyboardEmojiAdapter(Context context, List<IMExpressionModel> dataList) {
        super(context, dataList, R.layout.item_emoji);
        this.context = context;
    }

    @Override
    protected IMExpressionModel clone(IMExpressionModel data) {
        return new IMExpressionModel(data.getName(), data.getId());
    }

    @Override
    protected boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @NonNull
    @Override
    protected Bundle getChangePayload(int oldItemPosition, int newItemPosition) {
        return null;
    }

    @Override
    protected void partialBindData(IMChatKeyboardEmojiViewHolder holder, @NonNull Bundle bundle) {

    }

    /**
     * 设置表情图片
     *
     * @param holder   Holder
     * @param data     Data
     * @param position
     */
    @Override
    protected void entirelyBindData(IMChatKeyboardEmojiViewHolder holder, IMExpressionModel data, int position) {
        try {
            // 每行显示表情个数
            final int spanCount = IMChatKeyboardSmilyFragment.RECYCLER_VIEW_SPAN_COUNT;
            // 屏幕宽度
            int screenWidth = ScreenUtil.getScreenWidth((Activity) context);
            // 设置表情间距
            int spacing = ScreenUtility.dp2px(EMOJI_PADDING_DP);
            // 设置表情间距偏移量
            int temp = spacing * (spanCount + 1);
            // 设置表情图片实际宽高
            int emoticonSideLength = (screenWidth - temp) / spanCount;
            // 填充图片
            holder.setImageResource(R.id.ivIeIcon, data.getId())
                    .setViewPadding(R.id.ivIeIcon, temp / spanCount, temp / spanCount, 0, 0)
                    .setImageViewLayoutParams(R.id.ivIeIcon, emoticonSideLength, emoticonSideLength);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
