package com.hunter.helloandroid.module.multi.list;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;

import com.hunter.helloandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/6 14:33
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MultiListActivity extends AppCompatActivity implements MultiAdapter.DownloadCompleteCall {

    private final static int DEFAULT_DOWNLOAD_CONTHREAD = 3;//默认并发线程为3
    private final static int DEFAULT_DOWNLOAD_SUM = 20;//下载任务总数

    private final static int ACTION_UPDATE_PROGRESS = 0;
    private final static int ACTION_START_DOWNLOAD = 1;

    @BindView(R.id.countEditText)
    EditText countEditText;
    @BindView(R.id.listview)
    ListView listview;

    private MultiAdapter myAdapter;
    private int conThread;
    private boolean hasRunning = false;

    ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "DownloadTask#" + mCount.getAndIncrement());//线程的名称
        }
    };
    BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);//队列最多接收128个任务的缓存
    private ExecutorService threadPool;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == ACTION_START_DOWNLOAD) {
                startDownload(conThread);//开始模拟下载
            } else if (msg.what == ACTION_UPDATE_PROGRESS) {
                myAdapter.update(msg.arg1, msg.arg2);//更新adapter
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        ButterKnife.bind(this);

        initListView();
    }

    private void initListView() {
        List<Progress> progresses = new ArrayList<>();
        for (int i = 0; i < DEFAULT_DOWNLOAD_SUM; i++) {
            progresses.add( new Progress(i, 0, false));
        }
        myAdapter = new MultiAdapter(progresses, this);
        listview.setAdapter(myAdapter);
    }

    @OnClick(R.id.btn_start_thread)
    void setThread() {
        if (hasRunning) {
            return;
        }
        conThread = DEFAULT_DOWNLOAD_CONTHREAD;
        try {
            conThread = Integer.parseInt(countEditText.getText().toString());
            if (conThread > DEFAULT_DOWNLOAD_SUM) {
                conThread = DEFAULT_DOWNLOAD_SUM;
            } else if (conThread < 0) {
                conThread = DEFAULT_DOWNLOAD_CONTHREAD;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        hasRunning = true;
        handler.sendEmptyMessage(ACTION_START_DOWNLOAD);

    }

    private void startDownload(int conThread) {
        if (threadPool != null) {
            threadPool.shutdown();
            threadPool = null;
        }
        //此时conThread个线程并发，如果还有任务进来，就放入sPoolWorkQueue队列中等待运行
        threadPool = new ThreadPoolExecutor(conThread, conThread, 0, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);

        for (int i = 0; i < DEFAULT_DOWNLOAD_SUM; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    int result = 0;
                    int proBarIndex = myAdapter.getProBarIndex();
                    while (true) {
                        try {
                            //result等于100的时候，结束该下载任务
                            if (result == 100) {
                                break;
                            }
                            int sleepTime = (int) (Math.random() * 1000);
                            Thread.sleep(sleepTime);
                            result += 10;
                            Message msg = new Message();
                            msg.what = ACTION_UPDATE_PROGRESS;
                            msg.arg1 = proBarIndex;
                            msg.arg2 = result;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            shutDownThreadPool();//如果想要back键退出应用，仍后台下载，注释该行
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void downloadComplete() {
        shutDownThreadPool();//下载完成之后，关闭线程池，可以看到所有下载任务完成之后，并行的线程DownloadTask#1到3，就结束了
    }

    private void shutDownThreadPool() {
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }
}
