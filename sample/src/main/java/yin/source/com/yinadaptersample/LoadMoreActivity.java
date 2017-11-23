package yin.source.com.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import yin.source.com.yinadapter.BaseAdapter;
import yin.source.com.yinadapter.CommonViewHolder;
import yin.source.com.yinadapter.LoadMoreWrapperAdapter;
import yin.source.com.yinadaptersample.bean.PersonBean;

/**
 * Created by yin on 2017/11/15.
 */

public class LoadMoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PersonBean> personBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_adapter_sample);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        personBeanList = PersonBean.init();

        final BaseAdapter<PersonBean> adapter = new BaseAdapter<PersonBean>(getApplicationContext(), personBeanList, R.layout.item_male) {
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {

            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                return false;
            }
        };
        final LoadMoreWrapperAdapter<PersonBean> loadMoreWrapperAdapter = new LoadMoreWrapperAdapter<>(adapter, R.layout.load_more_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(loadMoreWrapperAdapter);

        recyclerView.addOnScrollListener(new LoadMoreWrapperAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.w("yzh", "load more");
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (personBeanList.size() > 100) {
                            loadMoreWrapperAdapter.noMoreToLoad(true);
                            return;
                        }
                        List<PersonBean> init = PersonBean.init();
                        personBeanList.addAll(init);
                        loadMoreWrapperAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }
}
