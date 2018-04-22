package com.boiledcoffee.welfare.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.welfare.R;
import com.boildcoffee.welfare.databinding.ActivityImageDetailBinding;
import com.boiledcoffee.welfare.vm.ImgDetailVm;

public class ImageDetailActivity extends BaseActivity {
    ImgDetailVm imgDetailVm;
    private static final String EXTRA_URL = "url";

    public static void startActivity(Context context,String url){
        Intent intent = new Intent(context,ImageDetailActivity.class);
        intent.putExtra(EXTRA_URL,url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail);
        imgDetailVm = new ImgDetailVm(this);
        String url = getIntent().getStringExtra(EXTRA_URL);
        imgDetailVm.startGetPageData(url, strings -> binding.setImgListVm(imgDetailVm));
    }
}
