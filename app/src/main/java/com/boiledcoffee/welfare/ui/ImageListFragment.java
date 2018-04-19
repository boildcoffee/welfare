package com.boiledcoffee.welfare.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by jason on 2018/3/17.
 */

public class ImageListFragment extends RxFragment {
    private static final String PARAM_VALUE = "value";

    public static ImageListFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString(PARAM_VALUE,value);
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
