package com.hunter.acp;

import android.content.Context;

import com.hunter.acp.interfaces.AcpListener;
import com.hunter.acp.util.LogUtil;

public class Acp {

    private static Acp mInstance;
    private AcpManager mAcpManager;

    public static Acp getInstance(Context context) {
        if (mInstance == null)
            synchronized (Acp.class) {
                if (mInstance == null) {
                    mInstance = new Acp(context);
                }
            }
        return mInstance;
    }

    private Acp(Context context) {
        mAcpManager = AcpManager.getInstance(context.getApplicationContext());
    }

    /**
     * 开始请求
     */
    public void request(AcpOptions options, AcpListener acpListener) {
        if (options == null) throw new NullPointerException("AcpOptions is null...");
        if (acpListener == null) throw new NullPointerException("AcpListener is null...");
        mAcpManager.request(options, acpListener);
    }

    AcpManager getAcpManager() {
        return mAcpManager;
    }

    public static void setDebug(boolean isDebug) {
        LogUtil.setDebug(isDebug);
    }

}
