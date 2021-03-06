package com.source.yin.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.source.yin.yinadapter.BaseAdapter;
import com.source.yin.yinadapter.CommonViewHolder;
import com.source.yin.yinadapter.LoadMoreWrapperAdapter;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

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

        personBeanList = new ArrayList<>();

        final BaseAdapter<PersonBean> adapter = new BaseAdapter<PersonBean>(getApplicationContext(), personBeanList, R.layout.item_male) {
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(context, "data.name = " + data.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                return false;
            }
        };
        final LoadMoreWrapperAdapter<PersonBean> loadMoreWrapperAdapter = new LoadMoreWrapperAdapter<>(adapter, R.layout.load_more_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(loadMoreWrapperAdapter);


        personBeanList.addAll(PersonBean.init());
        loadMoreWrapperAdapter.notifyDataSetChanged();
        recyclerView.addOnScrollListener(new LoadMoreWrapperAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.w("yzh", "load more");
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (personBeanList.size() > 22 * 10) {
                            loadMoreWrapperAdapter.noMoreToLoad();
                            return;
                        }
                        List<PersonBean> init = PersonBean.init();
                        personBeanList.addAll(init);
                        loadMoreWrapperAdapter.loadFinish();
                        loadMoreWrapperAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }
}
