package com.source.yin.yinadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yin on 17/4/21.
 * 通用Adapter，适合用于数据源需要根据不同属性返回不同的viewType进行不同显示的情况，如果数据源
 * 只需要一种显示样式，请使用{@link BaseAdapter}
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected List<T> dataList;
    protected Context context;
    private DataTypeManager<T> dataTypeManager;

    public CommonAdapter(Context context, List<T> dataList) {
        if (dataList == null) {
            throw new RuntimeException("dataList can not be null");
        }
        this.context = context;
        this.dataList = dataList;

        dataTypeManager = new DataTypeManager<>();
        addDataTypes(getDataTypes());
    }

    /**
     * @return 子类需要返回实现了{@link DataType}接口的List
     */
    public abstract List<DataType<T>> getDataTypes();


    public void addDataTypes(List<DataType<T>> viewTypes) {
        if (viewTypes != null) {
            for (DataType<T> viewType : viewTypes) {
                dataTypeManager.addViewType(viewType);
            }
        }
    }

    public void addDataType(DataType<T> viewType) {
        if (viewType != null) {
            dataTypeManager.addViewType(viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataTypeManager.getDataTypeList().size() <= 0) {
            return super.getItemViewType(position);
        } else {
            //当包装了 LoadMoreWrapperAdapter 时，LoadMoreWrapperAdapter 中的 getItemCount() 会返回实际数据+1用来展示最后"加载更多"的一栏，
            //多出来的最后一项实际并不存在于 dataList 中，直接做 null 在后续通过位置处理
            T data;
            if (position < dataList.size()) {
                data = dataList.get(position);
            } else {
                data = null;
            }
            return dataTypeManager.getViewType(data, position);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = dataTypeManager.getLayoutId(viewType);
        CommonViewHolder viewHolder = CommonViewHolder.createViewHolder(context, parent, layoutId);
        setOnLickListener(viewHolder, viewType);
        return viewHolder;
    }

    private void setOnLickListener(final CommonViewHolder viewHolder, final int viewType) {
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    dataTypeManager.getDateType(viewType).onItemClick(viewHolder, view, position);
                }
            }
        });

        viewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    return dataTypeManager.getDateType(viewType).onItemLongClick(viewHolder, view, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        T data;
        //当包装了 LoadMoreWrapperAdapter 时，LoadMoreWrapperAdapter 中的 getItemCount() 会返回实际数据+1用来展示最后"加载更多"的一栏，
        //多出来的最后一项实际并不存在于 dataList 中，直接做 null 在后续通过位置处理
        if (position < dataList.size()) {
            data = dataList.get(position);
        } else {
            data = null;
        }
        dataTypeManager.dataBind(holder, data, position);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (dataList != null) {
            itemCount = dataList.size();
        }
        return itemCount;
    }

    public List<T> getDataList() {
        return dataList;
    }
}
