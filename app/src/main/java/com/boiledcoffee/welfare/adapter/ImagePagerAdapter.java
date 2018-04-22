package com.boiledcoffee.welfare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.boiledcoffee.welfare.ui.ImageDetailFragment;
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
    List<String> mDetailDatas;

    private boolean isDetail;
    private List<Fragment> fragments = new ArrayList<>();


    private ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static ImagePagerAdapter createDetailPageAdapter(FragmentManager fm,List<String> datas){
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(fm);
        imagePagerAdapter.mDetailDatas = datas;
        imagePagerAdapter.isDetail = true;
        imagePagerAdapter.init();
        return imagePagerAdapter;
    }

    public static ImagePagerAdapter createPageAdapter(FragmentManager fm,List<Type> datas){
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(fm);
        imagePagerAdapter.mDatas = datas;
        imagePagerAdapter.isDetail = false;
        imagePagerAdapter.init();
        return imagePagerAdapter;
    }


    private void init() {
        if (isDetail) {
            for (String url : mDetailDatas) {
                fragments.add(ImageDetailFragment.newInstance(url));
            }
        } else {
            for (Type type : mDatas) {
                fragments.add(ImageListFragment.newInstance(type.getUrl()));
            }
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
        if (!isDetail){
            return mDatas.get(position).getName();
        }else {
            return String.valueOf(position);
        }
    }
}
