package com.source.yin.yinadaptersample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.source.yin.yinadapter.CommonAdapter;
import com.source.yin.yinadapter.CommonViewHolder;
import com.source.yin.yinadapter.DataType;
import com.source.yin.yinadaptersample.R;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yin on 2017/4/23.
 */

public class DifferentViewTypeAdapter extends CommonAdapter<PersonBean> {

    private Context context;

    public DifferentViewTypeAdapter(Context context, List<PersonBean> dataList) {
        super(context, dataList);
        this.context = context;
    }

    @Override
    public List<DataType<PersonBean>> getDataTypes() {
        DataType<PersonBean> maleDateType = new DataType<PersonBean>() {
            @Override
            public int getLayoutId() {
                return R.layout.item_male;
            }

            @Override
            public boolean isMatching(PersonBean data, int position) {
                return data.getSex() == 1;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
                viewHolder.<TextView>getView(R.id.tv_sex).setText("男");
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
        };

        DataType<PersonBean> femaleDateType = new DataType<PersonBean>() {
            @Override
            public int getLayoutId() {
                return R.layout.item_female;
            }

            @Override
            public boolean isMatching(PersonBean data, int position) {
                return data.getSex() == 0;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
                viewHolder.<TextView>getView(R.id.tv_sex).setText("女");
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        };
        List<DataType<PersonBean>> list = new ArrayList<>();
        list.add(maleDateType);
        list.add(femaleDateType);
        return list;
    }
}
