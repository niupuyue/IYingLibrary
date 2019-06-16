package com.niupuyue.mylibrary.widgets.chatkeyboard.adapter;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Desc: 表情显示Viewholder
 */
public class IMChatKeyboardEmojiViewHolder extends RecyclerView.ViewHolder {

    /**
     * 表情点击事件接口
     */
    public interface onExpressionClickLinstener {
        void onClick(int position);
    }

    /**
     * 表情长按事件接口
     */
    public interface OnLongClickListener {
        void onLongClick(int position);
    }

    private onExpressionClickLinstener clickListener;

    private OnLongClickListener longClickListener;

    //用来存放View以减少findViewById的次数
    private SparseArray<View> viewSparseArray;

    IMChatKeyboardEmojiViewHolder(View view) {
        super(view);
        viewSparseArray = new SparseArray<>();
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(getAdapterPosition());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setClickListener(onExpressionClickLinstener clickListener) {
        this.clickListener = clickListener;
    }

    void setLongClickListener(OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(@IdRes int viewId) {
        // 先从缓存中找，找到的话则直接返回
        // 如果找不到则findViewById，再把结果存入缓存中
        View view = null;
        try {
            view = viewSparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                if (view == null) return null;
                viewSparseArray.put(viewId, view);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (T) view;
    }

    /**
     * 设置文本信息
     *
     * @param viewId
     * @param text
     * @return
     */
    public IMChatKeyboardEmojiViewHolder setText(@IdRes int viewId, CharSequence text) {
        try {
            TextView textView = getView(viewId);
            if (textView == null) return this;
            textView.setText(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param resourceId
     * @return
     */
    public IMChatKeyboardEmojiViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resourceId) {
        try {
            ImageView imageView = getView(viewId);
            if (imageView == null) return this;
            imageView.setImageResource(resourceId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public IMChatKeyboardEmojiViewHolder setImageResource(@IdRes int viewId, Bitmap bitmap) {
        try {
            ImageView imageView = getView(viewId);
            if (imageView == null) return this;
            imageView.setImageBitmap(bitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    /**
     * 设置组件是否可见
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public IMChatKeyboardEmojiViewHolder setViewVisibility(@IdRes int viewId, int visibility) {
        try {
            View view = getView(viewId);
            if (view == null) return this;
            view.setVisibility(visibility);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    /**
     * 设置内边距
     *
     * @param viewId
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public IMChatKeyboardEmojiViewHolder setViewPadding(@IdRes int viewId, int left, int top, int right, int bottom) {
        try {
            View view = getView(viewId);
            if (view == null) return this;
            view.setPadding(left, top, right, bottom);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    /**
     * 设置图片尺寸
     *
     * @param viewId
     * @param width
     * @param height
     * @return
     */
    public IMChatKeyboardEmojiViewHolder setImageViewLayoutParams(@IdRes int viewId, int width, int height) {
        try {
            ImageView imageView = getView(viewId);
            if (imageView == null) return this;
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
            imageView.setLayoutParams(params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public IMChatKeyboardEmojiViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener clickListener) {
        try {
            View view = getView(viewId);
            if (view == null) return this;
            view.setOnClickListener(clickListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public IMChatKeyboardEmojiViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener clickListener) {
        try {
            View view = getView(viewId);
            if (view == null) return this;
            view.setOnLongClickListener(clickListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

}
