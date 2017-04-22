package yin.source.com.yinadapter;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yin on 2017/4/22.
 * 适合数据源只需要一种显示样式的Adapter
 */

public abstract class BaseAdapter<T> extends CommonAdapter<T> {

    private int layoutId;

    public BaseAdapter(Context context, List<T> dataList,int layoutId) {
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
            public boolean isMatching(T data) {
                //认为数据源viewType全部相同，这里直接全部返回true表示所有数据都用此样式显示
                return true;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, T data) {
                onDataBind(viewHolder, data);
            }

            @Nullable
            @Override
            public OnItemClickListener getOnClickListener() {
                return getOnItemClickListener();
            }
        };

        dataTypes.add(dataType);
        return dataTypes;
    }

    //子类只需要实现此方法进行数据的绑定显示
    public abstract void onDataBind(CommonViewHolder viewHolder, T data);

    @Nullable public abstract DataType.OnItemClickListener getOnItemClickListener();


}
