package yin.source.com.yinadaptersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDifferentViewType;
    private Button btnSameViewType;
    private Button btnLoadMoreAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSameViewType = (Button) findViewById(R.id.btn_same_view_type);
        btnDifferentViewType = (Button) findViewById(R.id.btn_different_view_type);
        btnLoadMoreAdapter = (Button) findViewById(R.id.btn_load_more_adapter);
        btnDifferentViewType.setOnClickListener(this);
        btnSameViewType.setOnClickListener(this);
        btnLoadMoreAdapter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_same_view_type:
                launchActivity(SameViewTypeActivity.class);
                break;
            case R.id.btn_different_view_type:
                launchActivity(DifferentViewTypeActivity.class);
                break;
            case R.id.btn_load_more_adapter:
                launchActivity(LoadMoreActivity.class);
                break;
        }
    }

    private void launchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
