package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.renrui.job.R;
import com.renrui.job.util.UtilityException;
import com.renrui.job.widget.im.chatkeyboard.adapter.IMChatKeyboardSmilyViewPagerAdapter;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMChatEmojiClickCallback;
import com.renrui.job.widget.im.chatkeyboard.models.IMExpressionModel;
import com.renrui.job.widget.im.chatkeyboard.utils.UtilityExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: 表情外部总页面
 */
public class IMChatKeyboardSmilyFragment extends BaseIMFragment {

    private static final int VIEWPAGE_INDEX_1 = 26;
    private static final int VIEWPAGE_INDEX_2 = 53;
    private static final int VIEWPAGE_INDEX_3 = 80;
    private static final int VIEWPAGE_INDEX_4 = 98;

    public static final int RECYCLER_VIEW_SPAN_COUNT = 7;

    public static IMChatKeyboardSmilyFragment getInstance() {
        IMChatKeyboardSmilyFragment fragment = new IMChatKeyboardSmilyFragment();
        return fragment;
    }

    private View root;
    private ViewPager vpfsEmojiContanier;
    private LinearLayout llFsDot;

    private int emoticonType = UtilityExpression.EMOJI_TYPE_CLASSICS;
    private IMChatEmojiClickCallback mEmojiClickListener;
    private List<IMExpressionModel> emojiList;

    // 设置表情显示的fragment
    private IMChatKeyboardSmilyDetailPagerFragment fragment1;
    private IMChatKeyboardSmilyDetailPagerFragment fragment2;
    private IMChatKeyboardSmilyDetailPagerFragment fragment3;
    private IMChatKeyboardSmilyDetailPagerFragment fragment4;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 填充fragment
        try {
            if (fragment1 == null) {
                fragment1 = new IMChatKeyboardSmilyDetailPagerFragment();
                fragments.add(fragment1);
            }
            if (fragment2 == null) {
                fragment2 = new IMChatKeyboardSmilyDetailPagerFragment();
                fragments.add(fragment2);
            }
            if (fragment3 == null) {
                fragment3 = new IMChatKeyboardSmilyDetailPagerFragment();
                fragments.add(fragment3);
            }
            if (fragment4 == null) {
                fragment4 = new IMChatKeyboardSmilyDetailPagerFragment();
                fragments.add(fragment4);
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public int getIMLayoutId() {
        return R.layout.fragment_smily;
    }

    @Override
    public void initViewFindViewById(View root) {
        if (root == null) return;
        try {
            vpfsEmojiContanier = root.findViewById(R.id.vpFsEmojiContanier);
            llFsDot = root.findViewById(R.id.llFsDot);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public void initDataAfterInitLayout() {
        initEmojiDataAfterInitView();
    }

    /**
     * 获取所有的表情图像
     */
    private void initEmojiDataAfterInitView() {
        if (vpfsEmojiContanier == null) return;
        try {
            emojiList = new ArrayList<>();
            // 将所有的表情图像加载到当前页面
            for (String emojiName : UtilityExpression.getInstance().getEmojiNameArray()) {
                emojiList.add(new IMExpressionModel(emojiName, UtilityExpression.getInstance().getEmojiId(emoticonType, emojiName)));
            }
            vpfsEmojiContanier.setAdapter(new IMChatKeyboardSmilyViewPagerAdapter(getChildFragmentManager(), fragments));
            vpfsEmojiContanier.setCurrentItem(0);
            vpfsEmojiContanier.setOnPageChangeListener(new MyPagerChangeListener(getContext(), llFsDot, fragments.size()));

            List<IMExpressionModel> tempEmoji1 = new ArrayList<>();
            List<IMExpressionModel> tempEmoji2 = new ArrayList<>();
            List<IMExpressionModel> tempEmoji3 = new ArrayList<>();
            List<IMExpressionModel> tempEmoji4 = new ArrayList<>();
            for (int i = 0; i < emojiList.size(); i++) {
                if (i <= VIEWPAGE_INDEX_1) {
                    tempEmoji1.add(emojiList.get(i));
                } else if (i <= VIEWPAGE_INDEX_2) {
                    tempEmoji2.add(emojiList.get(i));
                } else if (i <= VIEWPAGE_INDEX_3) {
                    tempEmoji3.add(emojiList.get(i));
                } else if (i <= VIEWPAGE_INDEX_4) {
                    tempEmoji4.add(emojiList.get(i));
                }
            }
            if (mEmojiClickListener != null) {
                fragment1.setData(tempEmoji1, mEmojiClickListener);
                fragment2.setData(tempEmoji2, mEmojiClickListener);
                fragment3.setData(tempEmoji3, mEmojiClickListener);
                fragment4.setData(tempEmoji4, mEmojiClickListener);
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    // 设置表情点击事件
    public void setOnEmojiClickListener(IMChatEmojiClickCallback listener) {
        if (listener == null) return;
        this.mEmojiClickListener = listener;
    }

    /**
     * 设置一个ViewPager的侦听事件
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        private int mPageCount;
        private LinearLayout mLlFsDots;
        private Context mContext;
        private List<ImageView> mImageDots;
        private int img_select;
        private int img_unSelect;

        private final int imageMargin = 10;
        private final int imageSize = 15;

        public MyPagerChangeListener(Context context, LinearLayout llFsDots, int pageCount) {
            try {
                this.mPageCount = pageCount;
                this.mContext = context;
                this.mLlFsDots = llFsDots;
                this.mImageDots = new ArrayList<>();
                // 被选中时圆点样式
                img_select = R.drawable.smily_viewpager_dot_selected;
                // 未被选中时圆点样式
                img_unSelect = R.drawable.smily_viewpager_dot_unselected;
                ImageView imageView;
                LinearLayout.LayoutParams params;
                for (int i = 0; i < mPageCount; i++) {
                    imageView = new ImageView(mContext);
                    params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    // 设置左右间距
                    params.leftMargin = imageMargin;
                    params.rightMargin = imageMargin;
                    // 设置默认大小
                    params.height = imageSize;
                    params.width = imageSize;
                    // 出初始第一次显示的样式
                    if (i == 0) {
                        imageView.setBackgroundResource(img_select);
                    } else {
                        imageView.setBackgroundResource(img_unSelect);
                    }
                    imageView.setLayoutParams(params);
                    mLlFsDots.addView(imageView);
                    mImageDots.add(imageView);
                }
            } catch (Exception ex) {
                UtilityException.catchException(ex);
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            try {
                // 根据当前选中的viewpager设置小圆点的样式
                for (int i = 0; i < mPageCount; i++) {
                    //选中的页面改变小圆点为选中状态，反之为未选中
                    if ((position % mPageCount) == i) {
                        (mImageDots.get(i)).setBackgroundResource(img_select);
                    } else {
                        (mImageDots.get(i)).setBackgroundResource(img_unSelect);
                    }
                }
            } catch (Exception ex) {
                UtilityException.catchException(ex);
            }
        }
    }
}
