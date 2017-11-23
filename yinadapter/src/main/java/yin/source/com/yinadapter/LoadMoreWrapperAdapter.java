package yin.source.com.yinadapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 装饰者模式的加载更多 Adapter
 * Created by yin on 2017/11/15.
 */

public class LoadMoreWrapperAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private CommonAdapter<T> commonAdapter;

    @LayoutRes
    private int loadMoreLayoutRes;

    private static final int STATUS_LOADING_MORE = 0;
    private static final int STATUS_NO_MORE = 1;
    //    private static final int STATUS_HIND = -1;
    private int status = STATUS_LOADING_MORE;

    private LoadMoreDataType loadMoreDataType;

    public LoadMoreWrapperAdapter(CommonAdapter<T> commonAdapter) {
        this(commonAdapter, 0);
    }

    public LoadMoreWrapperAdapter(CommonAdapter<T> commonAdapter, int loadMoreLayoutRes) {
        if (commonAdapter == null) {
            throw new RuntimeException("Adapter can not be null");
        }
        this.commonAdapter = commonAdapter;
        this.loadMoreLayoutRes = loadMoreLayoutRes;

        loadMoreDataType = new LoadMoreDataType();
        commonAdapter.addDataType(loadMoreDataType);
    }

    @Override
    public int getItemViewType(int position) {
        return commonAdapter.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return commonAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        commonAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int itemCount;
        switch (status) {
            case STATUS_NO_MORE:
                //没有更多可加载时，不需要在最后多加一栏用来显示加载更多
                itemCount = commonAdapter.getItemCount();
                break;
            default:
                itemCount = commonAdapter.getItemCount() + 1;
                break;
        }
        return itemCount;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        //todo 此方法只在 RecyclerView 的 setAdapter 后调用到一次，此时若还没有调用 setLayoutManager ,则获取到的 layoutManager 为 null
        //对于网格布局的 Adapter 加载更多栏需要横跨与每一行单元格数量相同的数值
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (status != STATUS_NO_MORE) {
                        if (loadMoreDataType.isMatching(null, position)) {
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                    return 1;
                }
            });
        }
    }

    public void noMoreToLoad(boolean noMoreToLoad) {
        status = noMoreToLoad ? STATUS_NO_MORE : STATUS_LOADING_MORE;
        if (noMoreToLoad) {
            //用来刷新列表最后的加载更多栏，使其消失
            notifyItemChanged(commonAdapter.getItemCount());
        }
    }

    private class LoadMoreDataType implements DataType<T> {

        @Override
        public int getLayoutId() {
            if (loadMoreLayoutRes == 0) {
                return R.layout.default_load_more_layout;
            }
            return loadMoreLayoutRes;
        }

        @Override
        public boolean isMatching(T data, int position) {
            if (status != STATUS_NO_MORE) {
                return position == getItemCount() - 1;
            } else {
                //当没有更多可加载时，itemCount 的数量回到等于 dataList 的数量，没有任何一项匹配加载更多栏
                return false;
            }
        }

        @Override
        public void dataBind(CommonViewHolder viewHolder, T data, int position) {
            switch (status) {
//                case STATUS_HIND:
//                    viewHolder.getItemView().setVisibility(View.GONE);
//                    break;
                case STATUS_LOADING_MORE:

                    break;
                case STATUS_NO_MORE:
//                    viewHolder.getItemView().setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {

        }

        @Override
        public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
            return false;
        }

    }

    public int getStatus() {
        return status;
    }

    public static abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

        private boolean wantToLoadMore;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                // 当不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount = linearLayoutManager.getItemCount();
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    boolean haveMore = true;
                    if (adapter instanceof LoadMoreWrapperAdapter) {
                        LoadMoreWrapperAdapter loadMoreWrapperAdapter = (LoadMoreWrapperAdapter) adapter;
                        int status = loadMoreWrapperAdapter.getStatus();
                        if (status == STATUS_NO_MORE) {
                            haveMore = false;
                        }
                    }
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition >= (itemCount - 2) && wantToLoadMore && haveMore) {
                        //加载更多
                        onLoadMore();
                    }
                }
            }
        }

        public abstract void onLoadMore();

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
            wantToLoadMore = dy > 0;
        }
    }

}
