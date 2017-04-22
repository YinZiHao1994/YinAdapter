package yin.source.com.yinadapter;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by yin on 17/4/21.
 * {@link DataType}管理类 负责将每一个{@link DataType}以key-value的形式储存。
 * 在后续提供根据数据源返回对应的Type、layoutId等方法
 */

class DataTypeManager<T> {

    private SparseArrayCompat<DataType<T>> dataTypeList = new SparseArrayCompat<>();
    private int viewTypeKey;


    int getLayoutId(int viewType) {
        return dataTypeList.get(viewType).getLayoutId();
    }

    void dataBind(CommonViewHolder viewHolder, T data) {
        int size = dataTypeList.size();

        for (int i = 0; i < size; i++) {
            DataType<T> dataType = dataTypeList.get(i);
            boolean matching = dataType.isMatching(data);
            if (matching) {
                dataType.dataBind(viewHolder, data);
            }
        }
    }

    void addViewType(DataType<T> viewType) {
        dataTypeList.append(viewTypeKey, viewType);
        viewTypeKey++;
    }

    SparseArrayCompat<DataType<T>> getDataTypeList() {
        return dataTypeList;
    }

    int getViewType(T data, int position) {
        for (int i = 0; i < dataTypeList.size(); i++) {
            DataType<T> dataType = dataTypeList.get(i);
            if (dataType.isMatching(data)) {
                return dataTypeList.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No DataType Match Tn The Position = " + position + " Of Data Source");
    }

    DataType.OnItemClickListener getOnItemClickListener(int viewType) {
        return dataTypeList.get(viewType).getOnClickListener();
    }

}
