package com.hunter.helloandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.base.BaseApplication;
import com.hunter.helloandroid.bean.ImgInfoBean;
import com.hunter.helloandroid.interfaces.OnItemClickListener;
import com.hunter.helloandroid.util.ImageUtil;
import com.hunter.helloandroid.util.ListUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * 创建日期： 2017/3/28 9:29
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<ImgInfoBean> mDatas;

    public PhotoAdapter(List<ImgInfoBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = BaseApplication.getContext();
        return new ViewHolder(View.inflate(context, R.layout.item_picture, null), mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (ListUtil.isEmpty(mDatas)) {
            return;
        }
        ImgInfoBean imgInfoBean = mDatas.get(position);
        String imgMiniUrl = imgInfoBean.getImg_MiniUrl();
        ImageUtil.display(holder.ivItemPicture, imgMiniUrl);

        holder.ivItemPicture.post(new Runnable() {
            @Override
            public void run() {
                //限制高度=宽度
                ViewGroup.LayoutParams layoutParams = holder.ivItemPicture.getLayoutParams();
                layoutParams.height = holder.ivItemPicture.getWidth();
                holder.ivItemPicture.setLayoutParams(layoutParams);
            }
        });

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (!ListUtil.isEmpty(mDatas)) {
            count = mDatas.size();
        }
        return count;
    }


    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivItemPicture;
        private OnItemClickListener mListener;

        public ViewHolder(View itemView, OnItemClickListener mListener) {
            super(itemView);
            this.mListener = mListener;

            ivItemPicture = (ImageView) itemView.findViewById(R.id.iv_item_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    /**
     * 保存方法
     */
    private boolean saveBitmap(Bitmap bitmap, String picPath) {

        boolean isSave = false;
        if (bitmap != null) {
            File f = new File(picPath);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        isSave = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSave;
    }

    /**
     //     * 生成缩略图
     //     */
//    private Bitmap generateThumbnail(String path) {
//        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
//        mmr.setDataSource(path);
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
//        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
//        Bitmap bitmap = mmr.getFrameAtTime(0, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
//        mmr.getEmbeddedPicture();
//        mmr.release();
//        return bitmap;
//    }

}
