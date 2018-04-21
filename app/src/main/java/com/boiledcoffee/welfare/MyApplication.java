package com.boiledcoffee.welfare;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;
import com.boiledcoffee.welfare.converter.DocumentConverter;
import com.boiledcoffee.welfare.repository.ImageRepository;

/**
 * Created by jason on 2018/3/17.
 */

public class MyApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        BFConfig.INSTANCE.init(new BaseConfig.Builder()
                .setBaseUrl(ImageRepository.BASE_URL)
                .setPageSize(24)
                .setConverter(DocumentConverter.create())
                .setDebug(true)
                .build()
        );
    }
}
