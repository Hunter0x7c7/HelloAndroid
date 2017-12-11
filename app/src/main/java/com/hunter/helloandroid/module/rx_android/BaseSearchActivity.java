package com.hunter.helloandroid.module.rx_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hunter.helloandroid.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseSearchActivity extends AppCompatActivity {

    protected CheeseSearchEngine mCheeseSearchEngine;
    protected EditText mQueryEditText;
    protected Button mSearchButton;
    private CheeseAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheeses);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter = new CheeseAdapter());

        mQueryEditText = (EditText) findViewById(R.id.query_edit_text);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        List<String> cheeses = Arrays.asList(getResources().getStringArray(R.array.cheeses));
        mCheeseSearchEngine = new CheeseSearchEngine(cheeses);
    }

    protected void showProgressBar(CharSequence text) {
        mProgressBar.setVisibility(View.VISIBLE);

        showToast(text);
    }

    protected void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    protected void showResult(List<String> result) {
        if (result.isEmpty()) {
            showToast(getText( R.string.nothing_found));

            mAdapter.setCheeses(Collections.<String>emptyList());
        } else {
            mAdapter.setCheeses(result);
        }
    }

    private void showToast( CharSequence text) {
        if (mToast != null) {
            mToast.cancel();
        }
         String l = String.format("----%s%s", text, System.currentTimeMillis());
        mToast = Toast.makeText(this, l, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
