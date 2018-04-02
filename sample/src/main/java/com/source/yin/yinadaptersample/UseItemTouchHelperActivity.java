package com.source.yin.yinadaptersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.source.yin.yinadapter.BaseAdapter;
import com.source.yin.yinadapter.CommonViewHolder;
import com.source.yin.yinadaptersample.bean.PersonBean;

import java.util.List;

public class UseItemTouchHelperActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    private List<PersonBean> personBeanList;
    private BaseAdapter<PersonBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_item_touch_helper);
        recyclerView = findViewById(R.id.recycler_view);
        personBeanList = PersonBean.init();
        adapter = new BaseAdapter<PersonBean>(getApplicationContext(), personBeanList, R.layout.item_female) {
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                TextView textView = viewHolder.getTextView(R.id.tv_name);
                textView.setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                return false;
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        MyOnTouchHelper myOnTouchHelper = new MyOnTouchHelper();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myOnTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    class MyOnTouchHelper extends ItemTouchHelper.Callback {

        private RecyclerView recyclerView;

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            this.recyclerView = recyclerView;
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int startPosition = viewHolder.getAdapterPosition();
            int targetPosition = target.getAdapterPosition();
            PersonBean remove = personBeanList.remove(startPosition);
            personBeanList.add(targetPosition, remove);
            adapter.notifyItemMoved(startPosition, targetPosition);
            Log.d("yzh", "startPosition = " + startPosition +
                    "\ttargetPosition = " + targetPosition +
                    "\npersonBeanList = " + personBeanList);
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int adapterPosition = viewHolder.getAdapterPosition();
            personBeanList.remove(adapterPosition);
            recyclerView.getAdapter().notifyItemRemoved(adapterPosition);
        }
    }

}
