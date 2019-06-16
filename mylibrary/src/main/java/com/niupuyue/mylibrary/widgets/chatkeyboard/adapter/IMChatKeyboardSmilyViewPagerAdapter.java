package com.niupuyue.mylibrary.widgets.chatkeyboard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.niupuyue.mylibrary.utils.BaseUtility;

import java.util.List;

/**
 * Desc:表情Viewpager的Adapter
 */
public class IMChatKeyboardSmilyViewPagerAdapter extends PagerAdapter {

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public IMChatKeyboardSmilyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this.fragments = fragments;
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        if (BaseUtility.isEmpty(fragments)) return 0;
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView(fragments.get(position).getView());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            Fragment fragment = fragments.get(position);
            if (!fragment.isAdded()) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                //提交事务
                fragmentManager.executePendingTransactions();
            }
            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView());
            }
            return fragment.getView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
