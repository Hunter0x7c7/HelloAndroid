package com.hunter.helloandroid.module.coordinator.alipay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlipayActivity extends AppCompatActivity {
    @BindView(R.id.img_zhangdan)
    ImageView mImgZhangdan;
    @BindView(R.id.img_zhangdan_txt)
    TextView mImgZhangdanTxt;
    @BindView(R.id.toolbar1)
    View toolbar1;
    @BindView(R.id.toolbar2)
    View toolbar2;
    @BindView(R.id.jiahao)
    ImageView mJiahao;
    @BindView(R.id.tongxunlu)
    ImageView mTongxunlu;
    @BindView(R.id.img_shaomiao)
    ImageView mImgShaomiao;
    @BindView(R.id.img_fukuang)
    ImageView mImgFukuang;
    @BindView(R.id.img_search)
    ImageView mImgSearch;
    @BindView(R.id.img_zhaoxiang)
    ImageView mImgZhaoxiang;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.activity_coordinator)
    CoordinatorLayout mActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        ButterKnife.bind(this);

        try {
            mRv.setLayoutManager(new LinearLayoutManager(this));
            mRv.setAdapter(new  LifeAdapter(LifeAdapter.LifeItem.getDefault()));
            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    //                System.out.println("verticalOffset = [" + verticalOffset + "]" + "{" + Math.abs(verticalOffset) + "}" + "{:" + appBarLayout.getTotalScrollRange() + "}");
                    if (verticalOffset == 0) {
                        //完全展开
                        toolbar1.setVisibility(View.VISIBLE);
                        toolbar2.setVisibility(View.GONE);
                        setToolbar1Alpha(255);
                    } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                        //appBarLayout.getTotalScrollRange() == 200
                        //完全折叠
                        toolbar1.setVisibility(View.GONE);
                        toolbar2.setVisibility(View.VISIBLE);
                        setToolbar2Alpha(255);
                    } else {//0~200上滑下滑
                        if (toolbar1.getVisibility() == View.VISIBLE) {
                            //                        //操作Toolbar1
                            int alpha = 300 - 155 - Math.abs(verticalOffset);
                            Log.i("alpha:", alpha + "");
                            setToolbar1Alpha(alpha);

                        } else if (toolbar2.getVisibility() == View.VISIBLE) {
                            if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 200) {
                                toolbar1.setVisibility(View.VISIBLE);
                                toolbar2.setVisibility(View.GONE);
                                setToolbar1Alpha(255);
                            }
                            //                        //操作Toolbar2
                            int alpha = (int) (255 * (Math.abs(verticalOffset) / 100f));
                            setToolbar2Alpha(alpha);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setToolbar1Alpha(int alpha) {
        try {
            mImgZhangdan.getDrawable().setAlpha(alpha);
            mImgZhangdanTxt.setTextColor(Color.argb(alpha, 255, 255, 255));
            mTongxunlu.getDrawable().setAlpha(alpha);
            mJiahao.getDrawable().setAlpha(alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setToolbar2Alpha(int alpha) {
        try {
            mImgShaomiao.getDrawable().setAlpha(alpha);
            mImgFukuang.getDrawable().setAlpha(alpha);
            mImgSearch.getDrawable().setAlpha(alpha);
            mImgZhaoxiang.getDrawable().setAlpha(alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
