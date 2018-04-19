package com.boiledcoffee.welfare.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jason on 2018/3/17.
 */

public class DocumentBodyConverter implements Converter<ResponseBody, Document> {
    @Override
    public Document convert(ResponseBody value) throws IOException {
        return Jsoup.parse(value.string());
    }
}
