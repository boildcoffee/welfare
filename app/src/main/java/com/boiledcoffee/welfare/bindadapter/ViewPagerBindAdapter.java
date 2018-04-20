package com.boiledcoffee.welfare.bindadapter;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.adapter.ImagePagerAdapter;
import com.boiledcoffee.welfare.vo.Type;

import java.util.List;

/**
 * Created by jason on 2018/3/17.
 *
 */

public class ViewPagerBindAdapter {
    @BindingAdapter(value = {
            "activity","data","tab_layout_id"
    })
    public static void bindPagerAdapterToTabLayout(ViewPager viewPager, BaseActivity activity, List<Type> datas,@IdRes  int id){
        if (datas == null || activity == null) return;
        PagerAdapter pagerAdapter = new ImagePagerAdapter(activity.getSupportFragmentManager(),datas);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = activity.findViewById(id);
        tabLayout.setupWithViewPager(viewPager);
    }
}
