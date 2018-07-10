package com.hunter.helloandroid.module.drawer;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.drawer.util.DrawerUtil;

public class DrawerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initView();
    }

    /**
     * 初始化视图
     */
    public void initView() {

        //初始化ToolBar
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) this.findViewById(R.id.dl_drawerlayout);

        DrawerUtil.setupSync(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        System.out.println("...................view:" + view);
    }
}
