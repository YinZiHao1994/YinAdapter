package yin.source.com.yinadapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yin on 17/4/21.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private View itemView;
    private SparseArrayCompat<View> childViews;

    private CommonViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        childViews = new SparseArrayCompat<>();
    }

    static CommonViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return createViewHolder(itemView);
    }

    static CommonViewHolder createViewHolder(View view) {
        return new CommonViewHolder(view);
    }

    public <T extends View> T getView(int viewId) {
        View childView = childViews.get(viewId);
        if (childView == null) {
            childView = itemView.findViewById(viewId);
            if (childView == null) {
                throw new IllegalArgumentException("This viewId is not match a childView in the itemView");
            }
            childViews.put(viewId, childView);
        }
        return (T) childView;
    }

    View getItemView() {
        return itemView;
    }


    /**
     *  以下为{@link CommonViewHolder#getView(int)}} 的帮助方法
     * @param viewId
     * @return
     */
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    public Button getButton(int viewId) {
        return getView(viewId);
    }
}
