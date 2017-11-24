package yin.source.com.yinadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by yin on 2017/11/24.
 */

public class SideMenuAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private CommonAdapter<T> commonAdapter;

    public SideMenuAdapter(CommonAdapter<T> commonAdapter) {
        if (commonAdapter == null) {
            throw new RuntimeException("Adapter can not be null");
        }
        this.commonAdapter = commonAdapter;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return commonAdapter.onCreateViewHolder(parent, viewType);
    }



    @Override
    public int getItemViewType(int position) {
        return commonAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        commonAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return commonAdapter.getItemCount();
    }
}
