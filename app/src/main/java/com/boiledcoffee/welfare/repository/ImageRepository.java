package com.boiledcoffee.welfare.repository;

import com.boildcoffee.base.network.RetrofitManager;
import com.boildcoffee.base.network.rx.TransformerHelper;
import com.boiledcoffee.welfare.api.WelfareApi;
import com.boiledcoffee.welfare.vo.ImageWelfare;
import com.boiledcoffee.welfare.vo.Type;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public class ImageRepository {
    private ImageRepository(){}

    public static ImageRepository create(){
        return new ImageRepository();
    }

    public static final String DEFAULT_TYPE_VALUE = "gallery";
    private static List<Type> mTypeList;
    private static Map<String,String> mTypeMap;
    private String mCurrentTypeValue;
    private Map<String,Integer> totalPageMap = new HashMap<>(); //当前类型的总页数

    public Observable<List<Type>> getTypes(){
        return getData(DEFAULT_TYPE_VALUE)
                .map(document -> {
                    if (mTypeList == null){
                        mTypeList = parseTypes(document);
                    }
                    return mTypeList;
                });
    }

    public Observable<List<ImageWelfare>> getImageWelfare(String typeValue, int currentPage){
        mCurrentTypeValue = typeValue;
        if (!totalPageMap.isEmpty() && currentPage > totalPageMap.get(typeValue)){ //没有更多数据
            return Observable.just(new ArrayList<ImageWelfare>())
                    .compose(TransformerHelper.observableToMainThreadTransformer());
        }
        String value = currentPage == 0 ? typeValue : typeValue + "/" + currentPage + ".html";
        return getData(value)
                .map(this::parseDocument);
    }

    private Observable<Document> getData(String value) {
        return RetrofitManager
                .getInstance()
                .createReq(WelfareApi.class)
                .getData(value)
                .compose(TransformerHelper.observableToMainThreadTransformer());
    }

    private List<ImageWelfare> parseDocument(Document document) {
        List<ImageWelfare> imageWelfareList = new ArrayList<>();
        if (mTypeList == null){
            mTypeList = parseTypes(document);
        }
        totalPageMap.put(mCurrentTypeValue,parseTotalPage(document));
        Elements elements = document.select(".listdiv > ul > li > .galleryli_div > .galleryli_link > img");
        for (Element element : elements){
            ImageWelfare imageWelfare = new ImageWelfare();
            imageWelfare.setTypeName(mTypeMap.get(mCurrentTypeValue));
            imageWelfare.setTypeValue(mCurrentTypeValue);
            imageWelfare.setCover(element.attr("data-original"));
            imageWelfare.setTitle(element.attr("title"));
            imageWelfareList.add(imageWelfare);
        }
        return imageWelfareList;
    }

    private Integer parseTotalPage(Document document) {
        Elements elements = document.select(".pagesYY > div > a");
        int totalPage = 0;
        for (Element element : elements){
            String text = element.text();
            if (text.contains("上一页") || text.contains("下一页")){
                continue;
            }
            totalPage ++;
        }
        return totalPage;
    }

    private List<Type> parseTypes(Document document) {
        Elements elements = document.select(".tag_div > ul > li > a");
        List<Type> typeList = new ArrayList<>();
        mTypeMap = new HashMap<>();
        for (Element element : elements){
            Type type = new Type();
            String text = element.text();
            String value = element.attr("href");
            type.setName(text);
            type.setValue(value);
            typeList.add(type);
            mTypeMap.put(value,text);
        }
        return typeList;
    }
}
