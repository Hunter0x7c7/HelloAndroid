package com.hunter.helloandroid.module.multi.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/6 14:30
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MultiAdapter extends BaseAdapter {
    private List<Progress> list;
    private Object lockobj = new Object();//对象锁

    private int complteTask = 0;//当前完成下载任务的数量

    private DownloadCompleteCall mCallback;//所有下载任务完成的回调方法


    public interface DownloadCompleteCall {
        void downloadComplete();
    }

    public MultiAdapter( List<Progress> list, DownloadCompleteCall mCallback) {
        this.list = list;
        this.mCallback = mCallback;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.progress_layout, null);
            convertView = View.inflate(parent.getContext(), R.layout.progress_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_progress_title);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.pb_progress);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Progress curProgress = (Progress) getItem(position);
        viewHolder.textView.setText("任务" + position + ":" + curProgress.getProgress() + "%");
        viewHolder.progressBar.setProgress(curProgress.getProgress());
        return convertView;
    }

    public void update(int proBarIndex, int result) {
        if (proBarIndex < 0 || proBarIndex >= list.size()) {
            return;
        }
        Progress curPro = list.get(proBarIndex);
        curPro.setProgress(result);
        if (result == 100) {
            complteTask++;
        }
        if (complteTask == list.size()) {
            mCallback.downloadComplete();//如果完成的任务数等于总数，说明下载完成，回调
        }
        notifyDataSetChanged();
    }

    //获取任务列表中第一个没有在运行的任务
    public int getProBarIndex() {
        synchronized (lockobj) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isRunning()) {
                    list.get(i).setRunning(true);
                    return i;
                }
            }
        }
        return -1;
    }

    class ViewHolder {
        TextView textView;
        ProgressBar progressBar;
    }
}
