package com.boiledcoffee.welfare.bindadapter;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.vo.Type;

import java.util.List;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public class TabLayoutBindAdapter {
    @BindingAdapter(value = {"data"})
    public static void bindTab(TabLayout tabLayout, List<Type> datas){
        for (Type type : datas){
            tabLayout.addTab(tabLayout.newTab().setText(type.getName()));
        }
    }

    @BindingAdapter(value = {"activity","view_pager_id"})
    public static void setupWithViewPager(TabLayout tabLayout, BaseActivity activity,@IdRes int id){
        ViewPager viewPager = activity.findViewById(id);
        tabLayout.setupWithViewPager(viewPager);
    }
}
