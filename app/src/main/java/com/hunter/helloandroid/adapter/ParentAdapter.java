package com.hunter.helloandroid.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.base.BaseApplication;
import com.hunter.helloandroid.bean.CommentsBean;
import com.hunter.helloandroid.bean.DividerGridItemDecoration;
import com.hunter.helloandroid.bean.ImgInfoBean;
import com.hunter.helloandroid.interfaces.OnItemClickListener;
import com.hunter.helloandroid.interfaces.OnNestItemClickListener;
import com.hunter.helloandroid.util.ListUtil;
import com.hunter.helloandroid.view.CircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/9/22 9:23
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {

    private List<CommentsBean> mPostilList;

    public ParentAdapter(List<CommentsBean> mPostilList) {
        this.mPostilList = mPostilList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(BaseApplication.getContext(), R.layout.item_parent, null);
        return new ViewHolder(inflate, onNestItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (ListUtil.isEmpty(mPostilList)) {
            return;
        }

        if (position == 0) {
            holder.lineBetween.setVisibility(View.INVISIBLE);
        } else {
            holder.lineBetween.setVisibility(View.VISIBLE);
        }

        Context context = BaseApplication.getContext();
        TypedArray ar = context.getResources().obtainTypedArray(R.array.postil_colors);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();

        int color = resIds[position % len];
        holder.tv_tab.setColor(color);
        holder.tvUserName.setTextColor(color);

        CommentsBean commentsBean = mPostilList.get(position);
        holder.tvUserName.setText(commentsBean.getCOM_User());
        holder.tvPostilMsg.setText(commentsBean.getCOM_Message());
        holder.tvPostilDate.setText(commentsBean.getCOM_Date());

        List<ImgInfoBean> pathModel = commentsBean.getPathModel();
        if (pathModel != null && holder.pathModel != null) {
            holder.pathModel.addAll(pathModel);
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (!ListUtil.isEmpty(mPostilList)) {
            count = mPostilList.size();
        } else {
            count = 0;
        }
        return count;
    }

    private OnNestItemClickListener onNestItemClickListener;

    public void setOnNestItemClickListener(OnNestItemClickListener onNestItemClickListener) {
        this.onNestItemClickListener = onNestItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {
        private View lineBetween;
        private TextView tvUserName;
        private CircleView tv_tab;
        private TextView tvPostilMsg;
        private RecyclerView rvPhoto;
        private TextView tvPostilDate;
        private OnNestItemClickListener onNestItemClickListener;
        private List<ImgInfoBean> pathModel;

        private ViewHolder(View itemView, OnNestItemClickListener onNestItemClickListener) {
            super(itemView);

            this.onNestItemClickListener = onNestItemClickListener;
            lineBetween = itemView.findViewById(R.id.line_between);
            tv_tab = (CircleView) itemView.findViewById(R.id.ov_tab);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_level_name);
            tvPostilMsg = (TextView) itemView.findViewById(R.id.tv_postil_msg);
            rvPhoto = (RecyclerView) itemView.findViewById(R.id.rv_photo);
            tvPostilDate = (TextView) itemView.findViewById(R.id.tv_postil_date);

            pathModel = new ArrayList<>();
            PhotoAdapter mPhotoAdapter = new PhotoAdapter(pathModel);
            mPhotoAdapter.setOnItemClickListener(this);

            Context context = BaseApplication.getContext();
            rvPhoto.setLayoutManager(new GridLayoutManager(context, 5));
            rvPhoto.setAdapter(mPhotoAdapter);
            rvPhoto.addItemDecoration(new DividerGridItemDecoration(context, 5));
            rvPhoto.setNestedScrollingEnabled(false);//解决ScrollView嵌套RecyclerView导致滑动不流畅的问题
        }

        @Override
        public void onItemClick(View v, int position) {
            if (onNestItemClickListener != null) {
                onNestItemClickListener.onNestItemClick(v, getLayoutPosition(), position);
            }
        }
    }

}
