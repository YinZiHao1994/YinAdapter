package yin.source.com.yinadapter;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;
import java.util.List;

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
        DataType<T> matchedDataType = getMatchedDataType(data);
        matchedDataType.dataBind(viewHolder, data);

    }

    /**
     * 根据data获取它所匹配的Type类型，检查是否存在多匹配和无匹配的情况
     * @param data
     * @return
     */
    private DataType<T> getMatchedDataType(T data) {
        int size = dataTypeList.size();
        List<DataType<T>> matchedDataTypeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            DataType<T> dataType = dataTypeList.get(i);
            boolean matching = dataType.isMatching(data);
            if (matching) {
                matchedDataTypeList.add(dataType);
            }
        }
        int matchedDataTypeSize = matchedDataTypeList.size();
        if (matchedDataTypeSize == 0) {
            throw new IllegalArgumentException("No DataType Match Of Data Source :" + data.toString());
        } else if (matchedDataTypeSize > 1) {
            throw new IllegalArgumentException(matchedDataTypeSize + " DataType Class ( " + matchedDataTypeList.toString() + " )Matched the Data Source :" + data.toString());
        } else {
            return matchedDataTypeList.get(0);
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
        DataType<T> matchedDataType = getMatchedDataType(data);
        int indexOfValue = dataTypeList.indexOfValue(matchedDataType);
        return dataTypeList.keyAt(indexOfValue);
    }

    DataType.OnItemClickListener getOnItemClickListener(int viewType) {
        return dataTypeList.get(viewType).getOnClickListener();
    }

}
