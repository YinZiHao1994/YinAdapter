package com.source.yin.yinadaptersample;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import com.source.yin.yinadapter.SectionDecoration;
import com.source.yin.yinadaptersample.adapter.DifferentViewTypeAdapter;
import com.source.yin.yinadaptersample.bean.PersonBean;

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


        SectionDecoration<Integer> sectionDecoration = new SectionDecoration<Integer>
                (this, new SectionDecoration.SectionCallback<Integer>() {
                    @Override
                    public Integer getSectionSign(int position) {
                        return personBeanList.get(position).getSex();
                    }

                    @Override
                    public String getSectionTitle(int position) {
                        return personBeanList.get(position).getName();
                    }
                }, new SectionDecoration.ConfigureCallback() {
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
                        return 60;
                    }

                    @Override
                    public int getTitleMarginStart() {
                        return 10;
                    }
                });

        recyclerView.addItemDecoration(sectionDecoration);


    }


}
