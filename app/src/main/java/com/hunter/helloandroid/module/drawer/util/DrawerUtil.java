package com.hunter.helloandroid.module.drawer.util;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class DrawerUtil {

    public static void setupSync(Activity activity, final DrawerLayout drawerLayout,
                                 Toolbar toolbar, @StringRes int open, @StringRes int close) {
        if (activity == null || drawerLayout == null || toolbar == null) {
            return;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, open, close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                View content = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                if (content != null) {
                    if ("left".equalsIgnoreCase(String.valueOf(drawerView.getTag()))) {
                        //动画
                        ViewHelper.setTranslationX(content, drawerView.getMeasuredWidth() * (1 - scale));
                        content.invalidate();
                    }
                }
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
