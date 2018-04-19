package com.hunter.helloandroid.module.multi.list;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/6 14:32
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class Progress {
    private int index;
    private int progress;
    private boolean running;

    public Progress(int index, int progress, boolean running) {
        this.index = index;
        this.progress = progress;
        this.running = running;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}


