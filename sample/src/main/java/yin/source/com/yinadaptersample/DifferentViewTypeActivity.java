package yin.source.com.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import yin.source.com.yinadapter.BaseAdapter;
import yin.source.com.yinadapter.CommonViewHolder;
import yin.source.com.yinadapter.DataType;
import yin.source.com.yinadaptersample.adapter.DifferentViewTypeAdapter;
import yin.source.com.yinadaptersample.bean.PersonBean;

/**
 * Created by Yin on 2017/4/23.
 */

public class DifferentViewTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_view_type);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DifferentViewTypeAdapter(this, PersonBean.init()));

    }


}
