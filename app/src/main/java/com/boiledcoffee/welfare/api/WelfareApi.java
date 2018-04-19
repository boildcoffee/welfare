package com.boiledcoffee.welfare.api;

import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public interface WelfareApi {
    @GET("/{value}")
    Observable<Document> getData(@Path("value") String value);
}
