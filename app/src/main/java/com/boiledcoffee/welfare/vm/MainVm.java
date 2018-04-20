package com.boiledcoffee.welfare.vm;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.vo.Type;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by zjh on 2018/3/17.
 *
 */

public class MainVm {
    private BaseActivity mActivity;
    private ImageRepository mRepository;
    private List<Type> typeList = new ArrayList<>();

    public MainVm(BaseActivity activity){
        mActivity = activity;
        mRepository = ImageRepository.create();
    }

    public void startGetData(Consumer<List<Type>> loadComplete){
        mRepository.getTypes()
                .subscribe(types -> {
                    typeList.clear();
                    typeList.addAll(types);
                    if (loadComplete != null){
                        loadComplete.accept(typeList);
                    }
                });
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

}
