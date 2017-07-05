package yin.source.com.yinadapter;

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
        this.context = context;
        this.dataList = dataList;

        dataTypeManager = new DataTypeManager<>();
        addDataTypes(getDataTypes());
    }

    /**
     * @return 子类需要返回实现了{@link DataType}接口的List
     */
    public abstract List<DataType<T>> getDataTypes();


    private void addDataTypes(List<DataType<T>> viewTypes) {
        for (DataType<T> viewType : viewTypes) {
            dataTypeManager.addViewType(viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataTypeManager.getDataTypeList().size() <= 0) {
            return super.getItemViewType(position);
        } else {
            T data = dataList.get(position);
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
                dataTypeManager.getDateType(viewType).onItemClick(viewHolder, view, position);
            }
        });

        viewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = viewHolder.getAdapterPosition();
                return dataTypeManager.getDateType(viewType).onItemLongClick(viewHolder, view, position);
            }
        });
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        T data = dataList.get(position);
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


}
