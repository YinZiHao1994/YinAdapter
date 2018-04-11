package com.source.yin.yinadapter;

import android.view.View;

/**
 * Created by yin on 17/4/21.
 * 当数据源根据不同属性有实现各自的点击事件、显示样式……时，用此类实现各自的功能
 */

public interface DataType<T> {

    /**
     * 返回不同数据对应的itemView布局的layoutId
     */
    int getLayoutRes();

    /**
     * @param data
     * @param position
     * @return 最重要的，用于分辨某一数据是否匹配当前{@link DataType}实现类。
     * 若此data是当前实现类的目标数据类型，返回true
     */
    boolean isMatching(T data, int position);

    /**
     * 实现数据与viewHolder的绑定
     *
     * @param viewHolder
     * @param data
     */
    void dataBind(CommonViewHolder viewHolder, T data, int position);

    void onItemClick(CommonViewHolder commonViewHolder, View view, T data, int position);

    boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, T data, int position);

}
