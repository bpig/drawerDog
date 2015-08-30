package bpig.drawerdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.adapter.LeftItemAdapter;
import bpig.drawerdog.dao.LeftMenuItem;

import static bpig.drawerdog.R.id.item_recycler;

public class MainActivity extends Activity {

    private DrawerLayout mMainDrawerLayout;
    private RelativeLayout mLeftMenuLayout;
    private List<LeftMenuItem> mLeftItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        mMainDrawerLayout = (DrawerLayout)findViewById(R.id.act_main_drawerlayout);
//        getActionBar().hide();
        RecyclerView recyclerView = (RecyclerView) findViewById(item_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemAdapter adapter = new ItemAdapter(Item.items);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ItemAdapter.Listener() {
            @Override
            public void onClick(int position) {

            }
        });

        initLeftMenuLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private static final int PREVIEW_REQUEST_CODE = 1;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PREVIEW_REQUEST_CODE);
                }
                return true;
            }
            case R.id.action_left_menu:
                mMainDrawerLayout.openDrawer(mLeftMenuLayout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PREVIEW_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras= data.getExtras();
            Intent intent = new Intent(this, LabelActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            Log.w("xxxx", "error " + resultCode);
        }
    }

    private void initLeftMenuLayout() {
        mLeftMenuLayout = (RelativeLayout)findViewById(R.id.act_left_menu);
        ListView leftItemListView = (ListView)findViewById(R.id.act_left_item_list);
        mLeftItemList = new ArrayList<LeftMenuItem>();
        mLeftItemList.add(new LeftMenuItem(android.R.drawable.ic_menu_gallery, "首页"));
        mLeftItemList.add(new LeftMenuItem(android.R.drawable.star_big_off, "我的药箱"));
        mLeftItemList.add(new LeftMenuItem(android.R.drawable.ic_menu_manage, "设置"));
        mLeftItemList.add(new LeftMenuItem(android.R.drawable.ic_menu_close_clear_cancel, "退出登录"));

        LeftItemAdapter adapter = new LeftItemAdapter(this, mLeftItemList);
        leftItemListView.setAdapter(adapter);
    }

    private class LeftDrawerItemClick implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mMainDrawerLayout.closeDrawer(mLeftMenuLayout);
                    break;
                default:
                    break;
            }
        }
    }
}
