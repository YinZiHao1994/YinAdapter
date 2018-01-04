package com.source.yin.yinadapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yin on 2017/4/22.
 * 适合数据源只需要一种显示样式的Adapter
 */

public abstract class BaseAdapter<T> extends CommonAdapter<T> {

    private int layoutId;

    public BaseAdapter(Context context, List<T> dataList, int layoutId) {
        super(context, dataList);
        this.layoutId = layoutId;
    }

    @Override
    public List<DataType<T>> getDataTypes() {
        List<DataType<T>> dataTypes = new ArrayList<>();
        DataType<T> dataType = new DataType<T>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isMatching(T data, int position) {
                //当包装了 LoadMoreWrapperAdapter 时，LoadMoreWrapperAdapter 中的 getItemCount() 会返回实际数据+1用来展示最后"加载更多"的一栏，
                //当调用到此方法时，判断 position 是否属于真实列表数据中位置。如果是，认为数据源viewType全部相同，直接返回true表示所有数据都用此样式显示
                if (position < getItemCount()) {
                    return true;
                }
                return false;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, T data, int position) {
                onDataBind(viewHolder, data, position);
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                BaseAdapter.this.onItemClick(commonViewHolder, view, position);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                return BaseAdapter.this.onItemLongClick(commonViewHolder, view, position);
            }

        };

        dataTypes.add(dataType);
        return dataTypes;
    }

    //子类只需要实现此方法进行数据的绑定显示
    public abstract void onDataBind(CommonViewHolder viewHolder, T data, int position);


    public abstract void onItemClick(CommonViewHolder commonViewHolder, View view, int position);

    public abstract boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position);

}
