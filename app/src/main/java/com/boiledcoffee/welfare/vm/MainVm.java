package com.boiledcoffee.welfare.vm;

import android.databinding.ObservableField;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.vo.Type;

import java.util.List;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public class MainVm {
    private BaseActivity mActivity;
    private ImageRepository mRepository;
    private List<Type> typeList;

    public MainVm(BaseActivity activity){
        mActivity = activity;
        mRepository = ImageRepository.create();
    }

    public void startGetData(){
        mRepository.getTypes()
                .subscribe(types -> typeList = types);
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public List<Type> getTypeList() {
        return typeList;
    }
}
