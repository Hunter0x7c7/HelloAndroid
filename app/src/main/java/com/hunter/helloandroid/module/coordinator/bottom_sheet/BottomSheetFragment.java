package com.hunter.helloandroid.module.coordinator.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.DensityUtil;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private CoordinatorLayout ll_bottom_sheet;
    private static Context mContext;
    private static LinearLayout mll_bottom;
    private BottomSheetBehavior mBehavior;

    public static BottomSheetFragment getInstance(Context context) {
        mContext = context;
//        mll_bottom = ll_bottom;
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ll_bottom_sheet_layout = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        //设定弹出评论区域高度
        ll_bottom_sheet = (CoordinatorLayout) ll_bottom_sheet_layout.findViewById(R.id.ll_bottom_sheet);
        ll_bottom_sheet.setLayoutParams(new CoordinatorLayout.LayoutParams(-1, DensityUtil.dip2px(300)));
//        ll_bottom_sheet.setLayoutParams(new CoordinatorLayout.LayoutParams(mll_bottom.getWidth(), mll_bottom.getHeight()));
//        mBehavior = BottomSheetBehavior.from(ll_bottom_sheet);
//        mBehavior.setPeekHeight(mll_bottom.getHeight());
        return ll_bottom_sheet_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_test);
        new ListAdapter(mRecyclerView);
    }

    private final class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        public ListAdapter(RecyclerView recyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_bottom, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText("item" + (++position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textview);
            }
        }
    }
}
