package com.source.yin.yinadapter;

/**
 * Created by yin on 17/4/21.
 * 当数据源根据不同属性有实现各自的点击事件、显示样式……时，用此类实现各自的功能
 */

public abstract class DataTypeForTheRest<T> implements DataType<T>{
    @Override
    public boolean isMatching(T data, int position) {
        return false;
    }
}
