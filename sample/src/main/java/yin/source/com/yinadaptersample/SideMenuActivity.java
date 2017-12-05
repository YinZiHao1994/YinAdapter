package yin.source.com.yinadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import yin.source.com.yinadapter.BaseAdapter;
import yin.source.com.yinadapter.CommonViewHolder;
import yin.source.com.yinadapter.SideMenuLayout;
import yin.source.com.yinadaptersample.bean.PersonBean;

/**
 * Created by yin on 2017/12/4.
 */

public class SideMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PersonBean> personBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        recyclerView = findViewById(R.id.recycler_side_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personBeanList = PersonBean.init();
        recyclerView.setAdapter(new BaseAdapter<PersonBean>(this, personBeanList, R.layout.side_menu_item) {
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                SideMenuLayout itemView = (SideMenuLayout) viewHolder.getItemView();
                itemView.setOnMenuClickListener(new SideMenuLayout.OnMenuClickListener() {
                    @Override
                    public void onMenuClickListener() {
                        Toast.makeText(SideMenuActivity.this, "click", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.getTextView(R.id.tv_content).setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                SideMenuLayout itemView = (SideMenuLayout) commonViewHolder.getItemView();
                itemView.closeMenu();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                return false;
            }
        });
    }
}
