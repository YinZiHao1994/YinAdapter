package yin.source.com.yinadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Yin on 2017/4/24.
 */

public class LoadMoreWrapperAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter innerAdapter;

    public LoadMoreWrapperAdapter(RecyclerView.Adapter innerAdapter) {
        this.innerAdapter = innerAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position >= innerAdapter.getItemCount()) {
//            return
//        }
        return innerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
