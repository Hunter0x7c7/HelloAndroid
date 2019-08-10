package com.hunter.helloandroid.module.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuange(c)2019
 * <p>
 * 作    者： Hunter_112507007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2019/8/10 12:14
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class HttpActivity extends AppCompatActivity {

    private String urlString = "https://www.baidu.com/";
    private String imgUrl = "https://up.enterdesk.com/edpic/88/c7/c2/88c7c244a063ed9dd71f8a7c5638ee75.jpg";
    //                String imgUrl = "http://www.dnzhuti.com/uploads/allimg/170503/95-1F503163525.jpg";
//                String imgUrl = "http://www.shixiu.net/d/file/p/2bc22002a6a61a7c5694e7e641bf1e6e.jpg";

    private Button visitWebBtn = null;
    private Button downImgBtn = null;
    private TextView showTextView = null;
    private ImageView showImageView = null;
    private String resultStr = "";
    private ProgressBar progressBar = null;
    private ViewGroup viewGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        initUI();
        visitWebBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showImageView.setVisibility(View.GONE);
                showTextView.setVisibility(View.VISIBLE);
                Thread visitBaiduThread = new Thread(new VisitWebRunnable());
                visitBaiduThread.start();
                try {
                    visitBaiduThread.join();
                    if (!resultStr.equals("")) {
                        showTextView.setText(resultStr);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        downImgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showImageView.setVisibility(View.VISIBLE);
                showTextView.setVisibility(View.GONE);
                new DownImgAsyncTask().execute(imgUrl);
            }
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public void initUI() {
        showTextView = (TextView) findViewById(R.id.textview_show);
        showImageView = (ImageView) findViewById(R.id.imagview_show);
        downImgBtn = (Button) findViewById(R.id.btn_download_img);
        visitWebBtn = (Button) findViewById(R.id.btn_visit_web);
    }

    /**
     * 获取指定URL的响应字符串
     */
    private String getURLResponse(String urlString) {
        HttpURLConnection conn = null; //连接对象
        InputStream is = null;
        String resultData = "";
        try {
            URL url = new URL(urlString); //URL对象
            conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setDoOutput(true); //允许输出流，即允许上传
            conn.setUseCaches(false); //不使用缓冲
            conn.setRequestMethod("GET"); //使用get请求
            is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return resultData;
    }

    /**
     * 从指定URL获取图片
     */
    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    class VisitWebRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(".......VisitWebRunnable.........urlString:" + urlString);
            resultStr = getURLResponse(urlString);
        }

    }

    private class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showImageView.setImageBitmap(null);
            showProgressBar();//显示进度条提示框

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getImageBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                dismissProgressBar();
                showImageView.setImageBitmap(result);
            }
        }
    }

    /**
     * 在母布局中间显示进度条
     */
    private void showProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setVisibility(View.VISIBLE);
        Context context = getApplicationContext();
        viewGroup = (ViewGroup) findViewById(R.id.parent_view);
        //		MainActivity.this.addContentView(progressBar, params);
        viewGroup.addView(progressBar, params);
    }

    /**
     * 隐藏进度条
     */
    private void dismissProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            viewGroup.removeView(progressBar);
            progressBar = null;
        }
    }

}
