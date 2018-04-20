package com.boiledcoffee.welfare.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by jason on 2018/3/17.
 *
 */

public class DocumentConverter extends  Converter.Factory{
    public static DocumentConverter create() {
        return new DocumentConverter();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DocumentBodyConverter();
    }
}
