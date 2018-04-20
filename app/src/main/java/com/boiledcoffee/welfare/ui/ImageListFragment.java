package com.boiledcoffee.welfare.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.welfare.R;
import com.boildcoffee.welfare.databinding.FragmentImgListBinding;
import com.boiledcoffee.welfare.vm.ImgListVm;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by jason on 2018/3/17.
 *
 */

public class ImageListFragment extends RxFragment {
    private static final String PARAM_VALUE = "value";
    ImgListVm mImgListVm;

    public static ImageListFragment newInstance(String typeValue) {
        Bundle args = new Bundle();
        args.putString(PARAM_VALUE,typeValue);
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String typeValue = getArguments().getString(PARAM_VALUE);
        mImgListVm = new ImgListVm((BaseActivity) getActivity(),typeValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentImgListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_img_list,container,false);
        View view = binding.getRoot();
        binding.setImgListVm(mImgListVm);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImgListVm.startGetData();
    }
}
