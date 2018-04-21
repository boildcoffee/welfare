package com.boiledcoffee.welfare.repository;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.imageloader.GlideApp;
import com.boildcoffee.base.imageloader.ImageLoaderManager;
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
    public static final String BASE_URL = "http://www.mzitu.com/";
    private ImageRepository(){}

    public static ImageRepository create(){
        return new ImageRepository();
    }

    public static final String DEFAULT_TYPE_VALUE = "";
    private static List<Type> mTypeList;
    private static Map<String,String> mTypeMap;
    private static String mCurrentTypeValue;
    private Map<String,Integer> mTypeMaxPageMap;
    private int currentLastPage = -1;

    @BindingAdapter(value = {
            "loadImageUrl"
    })
    public static void loadWelfareImg(ImageView v,String url){
        Map<String,String> headers = new HashMap<>();
        headers.put("referer",BFConfig.INSTANCE.getConfig().getBaseUrl() + mCurrentTypeValue);
        headers.put("user-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
        ImageLoaderManager.getInstance().loadImg(GlideApp.with(v.getContext()),url,headers,null,null)
                .into(v);
    }

    /**
     * 获取图片所属类型 如：首页 最新 性感
     * @return
     */
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
        String value = currentPage  <= 1 ? typeValue : typeValue + "/page/" + currentPage;
        return getData(value)
                .map(document -> parseDocument(document,currentPage));
    }

    private Observable<Document> getData(String value) {
        return RetrofitManager
                .getInstance()
                .createReq(WelfareApi.class)
                .getData(value)
                .compose(TransformerHelper.observableToMainThreadTransformer());
    }

    private List<ImageWelfare> parseDocument(Document document,int currentPage) {
        List<ImageWelfare> imageWelfareList = new ArrayList<>();
        if (mTypeList == null){
            mTypeList = parseTypes(document);
        }
        if (!hasPage(document,currentPage)){
            return imageWelfareList;
        }
        Elements elements = document.select(".postlist > ul > li > a");
        for (Element element : elements){
            ImageWelfare imageWelfare = new ImageWelfare();
            imageWelfare.setTypeName(mTypeMap.get(mCurrentTypeValue));
            imageWelfare.setTypeValue(parseTypeValue(element.attr("href")));
            Element imgElement = element.child(0);
            imageWelfare.setCover(imgElement.attr("data-original"));
            imageWelfare.setTitle(imgElement.attr("alt"));
            imageWelfareList.add(imageWelfare);
        }
        return imageWelfareList;
    }

    /**
     *  判断当前页数是否小于等于最大页数
     * @param document
     * @param currentPage
     * @return
     */
    private boolean hasPage(Document document,int currentPage) {
        if (mTypeMaxPageMap == null){
            mTypeMaxPageMap = new HashMap<>();
        }
        if (mTypeMaxPageMap.get(mCurrentTypeValue) != null){
            return currentPage <= mTypeMaxPageMap.get(mCurrentTypeValue);
        }

        Elements elements = document.select("nav .nav-links .page-numbers");
        int maxPage = -1;
        for (Element element : elements){
            if (element.hasClass("dots")){
                continue;
            }
            String text = element.text();
            try {
                int page = Integer.parseInt(text);
                if (page > maxPage){
                    maxPage = page;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mTypeMaxPageMap.put(mCurrentTypeValue,maxPage);
        return currentPage <= maxPage;
    }

    private List<Type> parseTypes(Document document) {
        Elements elements = document.select(".mainnav > ul > li > a");
        List<Type> typeList = new ArrayList<>();
        mTypeMap = new HashMap<>();
        Elements subElements = document.select(".main > .main-content > .subnav > a");
        if (subElements != null && !subElements.isEmpty()){
            addType(subElements,typeList);
        }
        addType(elements, typeList);
        return typeList;
    }

    private void addType(Elements elements, List<Type> typeList) {
        for (Element element : elements){
            Type type = new Type();
            String text = element.text();
            String value = element.attr("href").replaceAll(BASE_URL,"");
            if ("首页".equals(text) || "zipai/".equals(value) || "all/".equals(value) || "best/".equals(value) || "zhuanti/".equals(value)){
                continue;
            }
            type.setName(text);
            type.setValue(value);
            typeList.add(type);
            mTypeMap.put(value,text);
        }
    }

    private String parseTypeValue(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
