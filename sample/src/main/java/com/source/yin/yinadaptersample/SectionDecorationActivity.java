package com.source.yin.yinadaptersample;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.source.yin.yinadapter.BaseAdapter;
import com.source.yin.yinadapter.CommonViewHolder;
import com.source.yin.yinadapter.SectionDecoration;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.List;

/**
 * Created by Yin on 2017/4/23.
 */

public class SectionDecorationActivity extends AppCompatActivity {

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
                viewHolder.getTextView(R.id.tv_sex).setText(data.getSex() == 1 ? "男" : "女");
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(context, "onItemClick position = " + position + "\ndata.name = " + data.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        SectionDecoration<Integer> sectionDecoration = new SectionDecoration<>(this,
                new SectionDecoration.SectionCallback<Integer>() {
                    @Override
                    public Integer getSectionSign(int position) {
                        return personBeanList.get(position).getSex();
                    }

                    @Override
                    public String getSectionTitle(int position) {
                        return personBeanList.get(position).getSex() == 1 ? "男" : "女";
                    }
                },
                new SectionDecoration.ConfigureCallback() {
                    @Override
                    public Paint getTextPaint() {
                        Paint textPaint = new Paint();
                        textPaint.setTextSize(50);
                        textPaint.setColor(Color.RED);
                        return textPaint;
                    }

                    @Override
                    public Paint getSectionBackgroundPaint() {
                        Paint backgroundPaint = new Paint();
                        backgroundPaint.setColor(Color.LTGRAY);
                        return backgroundPaint;
                    }

                    @Override
                    public int getSectionDecorationHeight() {
                        return 30;
                    }

                    @Override
                    public int getTitleMarginStart() {
                        return 10;
                    }
                });

        recyclerView.addItemDecoration(sectionDecoration);

    }


}
