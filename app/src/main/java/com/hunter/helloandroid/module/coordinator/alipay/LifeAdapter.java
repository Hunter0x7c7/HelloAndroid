package com.hunter.helloandroid.module.coordinator.alipay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/27 15:59
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.ViewHolder> {
    private List<String> mList;

    public LifeAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_simple, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList != null) {
            try {
                String str = mList.get(position);
                holder.mText1.setText(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView mText1;
        @BindView(R.id.text2)
        TextView mText2;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public static class LifeItem {
        public static List<String> getDefault() {
            return Arrays.asList("daf324s", "ffd432saf", "ffdsa543f", "ff654dsaf"
                    , "ff564dsaf", "ffds765af", "ff756dsaf", "ff7865dsaf"
                    , "f756fdsaf", "ffd543saf", "ff867dsaf",  "ffd432saf", "ffdsa543f", "ff654dsaf"
                    , "ff564dsaf", "ffds765af", "ff756dsaf", "ff7865dsaf"
                    , "f756fdsaf", "ffd543saf", "ff867dsaf", "ffd675saf");
        }
    }
}
