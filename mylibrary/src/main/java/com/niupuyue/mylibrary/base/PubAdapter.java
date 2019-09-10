package com.niupuyue.mylibrary.base;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/23
 * Time: 16:12
 * Desc: ListView通用Adapter
 * Version:
 */
public class PubAdapter extends BaseAdapter {
    // 上下文对象
    private Context mContext = null;
    // 数据对象
    private List<?> listData = null;


    //////////////////////////////////////////当前adapter的状态//////////////////////////////////////
    // 初始状态
    public boolean isNone = false;
    // 数据为空
    public boolean isEmpty = false;
    // 是否是加载更多
    public boolean isLoadMore = false;
    // 是否已经结束
    public boolean isEnd = false;
    // 是否加载失败
    public boolean isLoadFaild = false;
    //////////////////////////////////////////当前adapter的状态//////////////////////////////////////


    // 展示视图的类型
    public int viewType = 0;


    /////////////////////////////////////////////adapter的状态//////////////////////////////////////
    private static final int VIEWTYPE_EMPTY = 0; // 加载数据为空
    private static final int VIEWTYPE_ITEM = 1; // 加载正常数据
    private static final int VIEWTYPE_LOADING = 2; // 数据正在加载中
    private static final int VIEWTYPE_LOADFAILD = 3; // 数据加载失败
    private static final int VIEWTYPE_END = 4; // 数据加载结束
    private static final int VIEWTYPE_LOADMORE = 5; // 数据加载更多
    /////////////////////////////////////////////adapter的状态///////////////////////////////////////


    //////////////////////////////////////////不同状态显示不同的view///////////////////////////////////
    private View viewEmpty;
    private View viewFootLoading;
    private View viewFootLoadFail;
    private View viewFootEnd;
    private View viewFootLoadMore;
    //////////////////////////////////////////不同状态显示不同的view///////////////////////////////////


    // 声明公共的ViewHolder
    private PubViewHolder holder;
    // 设置ViewHolder的类型，这里我们需要传递一个Class
    public Class<?> holderClass;
    // 执行listener
    private IAdapter iAdapter = null;

    /**
     * 构造方法，传递上下文对象
     */
    public PubAdapter(Context context) {
        this.mContext = context;
        // 加载预加载布局样式
        initPreView();
        // 初始化监听事件
        initListener();
    }

    /**
     * 预加载数据加载的不同情况的样式
     */
    private void initPreView() {
        try {
            this.viewEmpty = View.inflate(mContext, R.layout.view_pubadapter_load_empty, null);
            this.viewFootEnd = View.inflate(mContext, R.layout.view_pubadapter_load_end, null);
            this.viewFootLoading = View.inflate(mContext, R.layout.view_pubadapter_load_loading, null);
            this.viewFootLoadFail = View.inflate(mContext, R.layout.view_pubadapter_load_fail, null);
            this.viewFootLoadMore = View.inflate(mContext, R.layout.view_pubadapter_load_more, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initListener() {
        // 设置加载失败时点击"加载失败页面"重新加载数据
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iAdapter != null) {
                    iAdapter.faildReload();
                }
            }
        }, this.viewFootLoadFail);
        // 加载更多
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iAdapter) {
                    iAdapter.loadMore();
                }
            }
        }, this.viewFootLoadMore);
    }

    ////////////////////////////////////////////对外暴露的功能方法/////////////////////////////////////

    /**
     * 设置数据
     *
     * @param listData
     */
    public void setData(List<?> listData) {
        if (BaseUtility.isEmpty(listData)) {
            this.listData = new ArrayList<>();
        } else {
            this.listData = listData;
        }
    }

    /**
     * 设置Class
     */
    public void setViewHolder(Class<?> holderClass) {
        this.holderClass = holderClass;
    }

    /**
     * 设置监听
     */
    public void setListener(IAdapter iAdapter) {
        this.iAdapter = iAdapter;
    }

    /**
     * 设置空数据时的view(有默认值)
     */
    public void setEmptyView(View emptyView) {
        this.viewEmpty = emptyView;
    }

    /**
     * 设置数据加载完的footend(有默认值)
     */
    public void setFootEndView(View viewFootEnd) {
        this.viewFootEnd = viewFootEnd;
    }

    /**
     * 设置数据加载失败的footfail(有默认值)
     */
    public void setFootLoadFailView(View viewFootLoadFail) {
        this.viewFootLoadFail = viewFootLoadFail;
    }

    /**
     * 设置数据加载中的footloading(有默认值)
     */
    public void setFootLoadingView(View viewFootLoading) {
        this.viewFootLoading = viewFootLoading;
    }

    /**
     * 设置数据加载更多的footloadmore(有默认值)
     */
    public void setFootLoadMoreView(View viewFootLoadMore) {
        this.viewFootLoadMore = viewFootLoadMore;
    }
    ////////////////////////////////////////////对外暴露的功能方法/////////////////////////////////////


    /////////////////////////////////////////////需要继承的方法//////////////////////////////////////

    @Override
    public int getCount() {
        if (isNone) {
            return 0;
        } else {
            return BaseUtility.isEmpty(this.listData) ? 0 : BaseUtility.size(this.listData) + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 无数据
        if (isEmpty) {
            viewType = VIEWTYPE_EMPTY;
        } else if (BaseUtility.size(this.listData) == position) {// 最后一条数据
            // 加载结束
            if (isEnd) {
                viewType = VIEWTYPE_END;
            } else if (!isLoadFaild) {
                // 加载中
                viewType = VIEWTYPE_LOADFAILD;
            } else if (isLoadMore) {
                // 加载更多
                viewType = VIEWTYPE_LOADMORE;
            } else {
                // 加载错误
                viewType = VIEWTYPE_LOADFAILD;
            }
        } else {
            // 正常数据
            viewType = VIEWTYPE_ITEM;
        }
        return viewType;
    }

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return this.listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            switch (getItemViewType(position)) {
                case VIEWTYPE_EMPTY:
                    // 空数据
                    convertView = viewEmpty;
                    break;
                case VIEWTYPE_ITEM:
                    // 正常数据
                    if (null != convertView && convertView instanceof IAdapterItem) {
                        holder = (PubViewHolder) convertView.getTag();
                    } else {
                        holder = (PubViewHolder) this.holderClass.newInstance();
                        convertView = (View) holder.getItem(this.mContext);
                        convertView.setTag(holder);
                    }
                    holder.getItem(this.mContext).setData(listData.get(position), position, false);
                    holder.getItem(this.mContext).setListener(iAdapter);
                    break;
                case VIEWTYPE_LOADING:
                    // 加载中
                    convertView = viewFootLoading;
                    break;
                case VIEWTYPE_LOADFAILD:
                    // 加载失败
                    convertView = viewFootLoadFail;
                    break;
                case VIEWTYPE_LOADMORE:
                    // 加载更多
                    convertView = viewFootLoadMore;
                    break;
                case VIEWTYPE_END:
                    // 加载结束
                    convertView = viewFootEnd;
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        try {
            super.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
