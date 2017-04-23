package yin.source.com.yinadaptersample.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yin.source.com.yinadapter.CommonAdapter;
import yin.source.com.yinadapter.CommonViewHolder;
import yin.source.com.yinadapter.DataType;
import yin.source.com.yinadaptersample.R;
import yin.source.com.yinadaptersample.bean.PersonBean;

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
            public boolean isMatching(PersonBean data) {
                return data.getSex() == 1;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.<TextView>getView(R.id.tv_name).setText(data.getName());
                viewHolder.<TextView>getView(R.id.tv_sex).setText("男");
            }

            @Nullable
            @Override
            public OnItemClickListener getOnClickListener() {
                return new OnItemClickListener() {
                    @Override
                    public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                        Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                        Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                };
            }
        };

        DataType<PersonBean> femaleDateType = new DataType<PersonBean>() {
            @Override
            public int getLayoutId() {
                return R.layout.item_female;
            }

            @Override
            public boolean isMatching(PersonBean data) {
                return data.getSex() == 0;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.<TextView>getView(R.id.tv_name).setText(data.getName());
                viewHolder.<TextView>getView(R.id.tv_sex).setText("女");
            }

            @Nullable
            @Override
            public OnItemClickListener getOnClickListener() {
                return null;
            }
        };
        List<DataType<PersonBean>> list = new ArrayList<>();
        list.add(maleDateType);
        list.add(femaleDateType);
        return list;
    }
}
