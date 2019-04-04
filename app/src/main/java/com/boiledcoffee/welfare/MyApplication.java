package com.boiledcoffee.welfare;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;
import com.boiledcoffee.welfare.converter.DocumentConverter;
import com.boiledcoffee.welfare.repository.ImageRepository;

/**
 *
 * Created by jason on 2018/3/17.
 */

public class MyApplication extends BaseApplication{
    public static final String BASE_URL = "https://www.mzitu.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        BFConfig.INSTANCE.init(new BaseConfig.Builder()
                .setBaseUrl(BASE_URL)
                .setPageSize(24)
                .setApiQueryCacheMode(BaseConfig.CacheMode.NETWORK_ELSE_CACHE)
                .setRspCacheTime(1000 * 60 * 60 * 24)
                .setConverter(DocumentConverter.create())
                .setDebug(true)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
