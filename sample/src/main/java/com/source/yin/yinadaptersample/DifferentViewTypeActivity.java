package com.source.yin.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.source.yin.yinadaptersample.adapter.DifferentViewTypeAdapter;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.List;

/**
 * Created by Yin on 2017/4/23.
 */

public class DifferentViewTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<PersonBean> personBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_view_type);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personBeanList = PersonBean.init();
        recyclerView.setAdapter(new DifferentViewTypeAdapter(this, personBeanList));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }



}
