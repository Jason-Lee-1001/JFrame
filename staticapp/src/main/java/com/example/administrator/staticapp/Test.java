package com.example.administrator.staticapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.studio.jframework.adapter.recyclerview.CommonRvAdapter;
import com.studio.jframework.adapter.recyclerview.RvViewHolder;
import com.studio.jframework.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason
 */
public class Test extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView view;
    private List<String> strings;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        view = (RecyclerView) findViewById(R.id.recycler);
        setupRecyclerView(view);
        strings = new ArrayList<>();
        strings.add("abc1");
        strings.add("abc2");
        strings.add("abc3");
        strings.add("abc4");
        strings.add("abc5");
        strings.add("abc6");
        strings.add("abc7");
        strings.add("abc8");
        strings.add("abc9");
        strings.add("abc11a");
        strings.add("abc11b");
        strings.add("abc11c");
        strings.add("abc11d");
        strings.add("abc11e");
        strings.add("abc11f");
        strings.add("abc11g");
        strings.add("abc11h");
        strings.add("abc11i");
        strings.add("abc11j");
        strings.add("abc11k");
        strings.add("abc11l");
        strings.add("abc11m");
        strings.add("abc11n");
        strings.add("abc11o");
        strings.add("abc11p");
        strings.add("abc11q");
        strings.add("abc11r");
        strings.add("abc11s");
        strings.add("abc11t");
        strings.add("abc11u");
        strings.add("abc11v");
        strings.add("abc11w");
        strings.add("abc11x");
        strings.add("abc11y");
        strings.add("abc11z");
        strings.add("abc12a");
        strings.add("abc12b");
        strings.add("abc12c");
        strings.add("abc12d");
        strings.add("abc12e");
        strings.add("abc12f");
        strings.add("abc12g");
        strings.add("abc12h");
        strings.add("abc12i");
        strings.add("abc12j");
        strings.add("abc12k");
        strings.add("abc12l");
        strings.add("abc12m");
        strings.add("abc12n");
        strings.add("abc12o");
        strings.add("abc12p");
        strings.add("abc12q");
        strings.add("abc12r");
        strings.add("abc12s");
        strings.add("abc12t");
        strings.add("abc12u");
        strings.add("abc12v");
        strings.add("abc12w");
        strings.add("abc12x");
        strings.add("abc12y");
        strings.add("abc13");
        strings.add("abc14");
        strings.add("abc15");
        strings.add("abc16");
        strings.add("abc17");
        strings.add("abc18");
        strings.add("abc19");
        strings.add("abc20");
        mAdapter = new Adapter(this, strings);
        view.setAdapter(mAdapter);
        view.setItemAnimator(new DefaultItemAnimator());
        view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        /*自定义itemdecoration，在style的主题下添加一句<item name="android:listDivider">@drawable/divider</item>*/
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                add(1);
                break;
            case R.id.remove:
                remove(2);
                break;
        }
    }

    private void add(int position){
        strings.add(position, "aidid");
        mAdapter.notifyItemInserted(position);
    }

    private void remove(int position){
        strings.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private class Adapter extends CommonRvAdapter<String> {
        public Adapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        public int setItemLayout(int type) {
            if (type == 0) {
                return R.layout.test_item;
            } else {
                return R.layout.test_item2;
            }
        }

        @Override
        public void inflateContent(RvViewHolder holder, int position, String s) {
            holder.setTextByString(R.id.textView, s);
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 1) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
