package com.boiledcoffee.welfare.vm;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.base.bindingadapter.common.ReplyCommand;
import com.boildcoffee.welfare.R;
import com.boiledcoffee.welfare.adapter.ImagePagerAdapter;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.view.PinchImageView;
import com.boiledcoffee.welfare.vo.Image;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 *
 * Created by jason on 2018/4/21.
 */

public class ImgDetailVm extends BaseVm{
    private ImageRepository mRepository;
    private List<String> mUrls = new ArrayList<>();
    private Image mImage;
    private ObservableField<String> mPageIndicator = new ObservableField<>("");

    public ImgDetailVm(BaseActivity activity){
        super(activity);
        mRepository = ImageRepository.create();
    }

    @BindingAdapter(value = {
            "activity","detailPageUrls"
    })
    public static void bindPagerAdapter(ViewPager viewPager, BaseActivity activity, List<String> datas){
        if (datas == null || activity == null) return;
        PagerAdapter pagerAdapter = ImagePagerAdapter.createDetailPageAdapter(activity.getSupportFragmentManager(),datas);
        viewPager.setAdapter(pagerAdapter);
    }

    @BindingAdapter(value = {
            "onPageSelected"
    })
    public static void bindPagerSelectedListener(ViewPager viewPager, ReplyCommand<Integer> replyCommand){
        if (replyCommand == null) return;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                replyCommand.execute(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ReplyCommand<Integer> onPageSelectedListener = new ReplyCommand<>((position) -> setPageIndicator(position + 1,mUrls.size()));



    public void startGetPageData(String url, Consumer<List<String>> loadComplete){
        showProgressDialog(LOADING_MSG);
        mRepository.getDetailPageUrls(url)
                .subscribe(urls -> {
                    mUrls.clear();
                    mUrls.addAll(urls);
                    dismissProgressDialog();
                    setPageIndicator(1,mUrls.size());
                    if (loadComplete != null){
                        loadComplete.accept(mUrls);
                    }
                },throwable -> {
                    showShortToast(throwable.getMessage());
                    dismissProgressDialog();
                });
    }

    public void startGetImageData(String url, Consumer<Image> loadComplete){
        mRepository.getImage(url).subscribe(image -> {
            mImage = image;
            if (loadComplete != null){
                loadComplete.accept(mImage);
            }
        },throwable -> showShortToast(throwable.getMessage()));
    }

    private void setPageIndicator(int currentPage,int totalPage){
        mPageIndicator.set(mActivity.getString(R.string.text_page_indicator,currentPage,totalPage));
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public String getImageUrl() {
        return mImage.getUrl();
    }

    public String getImageDesc(){
        return mImage.getDesc();
    }

    public ObservableField<String> getPageIndicator() {
        return mPageIndicator;
    }

    public ReplyCommand<Integer> getOnPageSelectedListener() {
        return onPageSelectedListener;
    }

    public void clickBackListener(View v){
        mActivity.finish();
    }
}
