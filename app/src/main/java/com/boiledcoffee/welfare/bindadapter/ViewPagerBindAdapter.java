package com.boiledcoffee.welfare.bindadapter;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.boildcoffee.base.BaseActivity;
import com.boiledcoffee.welfare.adapter.ImagePagerAdapter;
import com.boiledcoffee.welfare.vo.Type;

import java.util.List;

/**
 * Created by jason on 2018/3/17.
 */

public class ViewPagerBindAdapter {
    @BindingAdapter(value = {
            "activity","data"
    },requireAll = true)
    public static void bindPagerAdapter(ViewPager viewPager, BaseActivity activity, List<Type> datas){
        PagerAdapter pagerAdapter = new ImagePagerAdapter(activity.getSupportFragmentManager(),datas);
        viewPager.setAdapter(pagerAdapter);
    }
}
