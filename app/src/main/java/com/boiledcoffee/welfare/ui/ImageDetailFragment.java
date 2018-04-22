package com.boiledcoffee.welfare.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.welfare.R;
import com.boildcoffee.welfare.databinding.FragmentImgDetailBinding;
import com.boiledcoffee.welfare.vm.ImgDetailVm;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by jason on 2018/4/21.
 *
 */

public class ImageDetailFragment extends RxFragment {
    private static final String PARAM_URL = "url";
    ImgDetailVm mImgDetailVm;
    String mUrl;

    public static ImageDetailFragment newInstance(String url){
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImgDetailVm = new ImgDetailVm((BaseActivity) getActivity());
        mUrl = getArguments().getString(PARAM_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentImgDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_img_detail,container,false);
        View view = binding.getRoot();
        mImgDetailVm.startGetImageData(mUrl,image -> binding.setImgDetailVm(mImgDetailVm));
        return view;
    }
}
