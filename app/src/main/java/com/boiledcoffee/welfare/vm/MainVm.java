package com.boiledcoffee.welfare.vm;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.adapter.ImagePagerAdapter;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.vo.Type;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by zjh on 2018/3/17.
 *
 */

public class MainVm extends BaseVm{
    private ImageRepository mRepository;
    private List<Type> typeList = new ArrayList<>();

    public MainVm(BaseActivity activity){
        super(activity);
        mRepository = ImageRepository.create();
    }

    @BindingAdapter(value = {
            "activity","data","tab_layout_id"
    })
    public static void bindPagerAdapterToTabLayout(ViewPager viewPager, BaseActivity activity, List<Type> datas, @IdRes int id){
        if (datas == null || activity == null) return;
        PagerAdapter pagerAdapter = ImagePagerAdapter.createPageAdapter(activity.getSupportFragmentManager(),datas);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = activity.findViewById(id);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void startGetData(Consumer<List<Type>> loadComplete){
        showProgressDialog(LOADING_MSG);
        mRepository.getTypes()
                .subscribe(types -> {
                    typeList.clear();
                    typeList.addAll(types);
                    dismissProgressDialog();
                    if (loadComplete != null){
                        loadComplete.accept(typeList);
                    }
                },throwable -> {
                    dismissProgressDialog();
                    showShortToast(throwable.getMessage());
                });
    }

    public List<Type> getTypeList() {
        return typeList;
    }
}
