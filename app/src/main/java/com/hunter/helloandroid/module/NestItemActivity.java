package com.hunter.helloandroid.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.adapter.ParentAdapter;
import com.hunter.helloandroid.bean.CommentsBean;
import com.hunter.helloandroid.bean.ImgInfoBean;
import com.hunter.helloandroid.interfaces.OnNestItemClickListener;
import com.hunter.helloandroid.util.ToastUtil;

import java.util.ArrayList;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/9/22 11:26
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class NestItemActivity extends AppCompatActivity implements OnNestItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);

        ImgInfoBean imgInfoBean11 = new ImgInfoBean();
        imgInfoBean11.setImg_MiniUrl("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        imgInfoBean11.setImg_Url("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        ImgInfoBean imgInfoBean12 = new ImgInfoBean();
        imgInfoBean12.setImg_MiniUrl("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        imgInfoBean12.setImg_Url("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");

        ArrayList<ImgInfoBean> pathModel1 = new ArrayList<>();
        pathModel1.add(imgInfoBean11);
        pathModel1.add(imgInfoBean12);

        ImgInfoBean imgInfoBean21 = new ImgInfoBean();
        imgInfoBean21.setImg_MiniUrl("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        imgInfoBean21.setImg_Url("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        ImgInfoBean imgInfoBean22 = new ImgInfoBean();
        imgInfoBean22.setImg_MiniUrl("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");
        imgInfoBean22.setImg_Url("http://file.51yyh.com/18918721130/Img/imgfile201744201444259059.jpg");

        ArrayList<ImgInfoBean> pathModel2 = new ArrayList<>();
        pathModel2.add(imgInfoBean21);
        pathModel2.add(imgInfoBean22);

        CommentsBean bean1 = new CommentsBean();
        bean1.setCOM_Date("2017-09-21 15:22");
        bean1.setCOM_Message("发现一个新项目1");
        bean1.setCOM_User("黄先生1");
        bean1.setPathModel(pathModel1);

        CommentsBean bean2 = new CommentsBean();
        bean2.setCOM_Date("2017-09-22 15:22");
        bean2.setCOM_Message("发现一个新项目2");
        bean2.setCOM_User("黄先生2");
        bean2.setPathModel(pathModel2);

        ArrayList<CommentsBean> mPostilList = new ArrayList<>();
        mPostilList.add(bean1);
        mPostilList.add(bean2);

        ParentAdapter parentAdapter = new ParentAdapter(mPostilList);
        parentAdapter.setOnNestItemClickListener(this);

        RecyclerView rv_parent = (RecyclerView) findViewById(R.id.rv_parent);
        rv_parent.setLayoutManager(new LinearLayoutManager(this));
        rv_parent.setAdapter(parentAdapter);
    }

    @Override
    public void onNestItemClick(View view, int row, int position) {

        ToastUtil.showPrompt("row:" + row + " position:" + position);
    }
}
