package com.source.yin.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.source.yin.yinadapter.BaseAdapter;
import com.source.yin.yinadapter.CommonViewHolder;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.List;

/**
 * Created by Yin on 2017/4/23.
 */

public class SameViewTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<PersonBean> personBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_view_type);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personBeanList = PersonBean.init();
        recyclerView.setAdapter(new BaseAdapter<PersonBean>(this, personBeanList, R.layout.item_male) {
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }


}
