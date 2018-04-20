package com.boiledcoffee.welfare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.boiledcoffee.welfare.ui.ImageListFragment;
import com.boiledcoffee.welfare.vo.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public class ImagePagerAdapter extends FragmentPagerAdapter{
    List<Type> mDatas;
    private List<Fragment> fragments = new ArrayList<>();


    public ImagePagerAdapter(FragmentManager fm, List<Type> datas) {
        super(fm);
        mDatas = datas;
        init();
    }

    private void init() {
        for (Type type : mDatas){
            fragments.add(ImageListFragment.newInstance(type.getValue()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position).getName();
    }
}
