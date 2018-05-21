package com.boiledcoffee.welfare.repository;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.imageloader.GlideApp;
import com.boildcoffee.base.imageloader.ImageLoaderManager;
import com.boildcoffee.base.network.RetrofitManager;
import com.boildcoffee.base.network.rx.TransformerHelper;
import com.boiledcoffee.welfare.api.WelfareApi;
import com.boiledcoffee.welfare.vo.Atlas;
import com.boiledcoffee.welfare.vo.Image;
import com.boiledcoffee.welfare.vo.Type;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.boiledcoffee.welfare.MyApplication.BASE_URL;

/**
 * Created by zjh on 2018/3/17.
 *
 */

public class ImageRepository {
    private ImageRepository(){}

    public static ImageRepository create(){
        return new ImageRepository();
    }

    private static final String DEFAULT_URL = "";
    private static List<Type> mTypeList;
    private static Map<String,String> mTypeMap;
    private static String mCurrentUrl;
    private Map<String,Integer> mTypeMaxPageMap;

    @BindingAdapter(value = {
            "loadWelfareImg"
    })
    public static void loadWelfareImg(ImageView v,String url){
        if (url == null) return;

        Map<String,String> headers = new HashMap<>();
        headers.put("referer",BFConfig.INSTANCE.getConfig().getBaseUrl() + mCurrentUrl);
        headers.put("user-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
        ImageLoaderManager.getInstance().loadImg(GlideApp.with(v.getContext()),url,headers,null,null)
                .into(v);
    }

    /**
     * 获取图片所属类型 如：最新 性感
     * @return
     */
    public Observable<List<Type>> getTypes(){
        return getHtmlData(DEFAULT_URL)
                .map(document -> {
                    if (mTypeList == null){
                        mTypeList = parseTypes(document);
                    }
                    return mTypeList;
                });
    }

    public Observable<List<String>> getDetailPageUrls(String url){
        mCurrentUrl = url;
        return getHtmlData(url)
                .map(this::parseDetailUrl);
    }

    /**
     * 获取详情大图 图片地址
     * @param pageUrl
     * @return
     */
    public Observable<Image> getImage(String pageUrl){
        mCurrentUrl = pageUrl;
        return getHtmlData(pageUrl)
                .map(this::parseImageUrl);
    }

    /**
     * 通过对应类型的url后去图册对象
     * @param url
     * @param currentPage
     * @return
     */
    public Observable<List<Atlas>> getAtlas(String url, int currentPage){
        mCurrentUrl = url;
        String value = currentPage  <= 1 ? url : url + "page/" + currentPage;
        return getHtmlData(value)
                .map(document -> parseAtlasData(document,currentPage));
    }

    /**
     * 获取html数据
     * @param url
     * @return
     */
    private Observable<Document> getHtmlData(String url) {
        return RetrofitManager
                .getInstance()
                .createReq(WelfareApi.class)
                .getData(url)
                .compose(TransformerHelper.observableToMainThreadTransformer());
    }

    /**
     * 解析对应类型的图册对象
     * @param document
     * @param currentPage
     * @return
     */
    private List<Atlas> parseAtlasData(Document document, int currentPage) {
        List<Atlas> atlasList = new ArrayList<>();
        if (mTypeList == null){
            mTypeList = parseTypes(document);
        }
        if (!hasPage(document,currentPage)){
            return atlasList;
        }
        Elements elements = document.select(".postlist > ul > li");
        for (Element element : elements){
            if (element.children().size() < 1){
                continue;
            }
            Element aElement = element.child(0);
            Element spanElement = element.child(1);
            Atlas atlas = new Atlas();
            atlas.setTypeName(mTypeMap.get(mCurrentUrl));
            atlas.setUrl(parseUrl(aElement.attr("href")));
            atlas.setDetailUrl(parseUrl(spanElement.child(0).attr("href")) + "/");
            Element imgElement = aElement.child(0);
            atlas.setCover(imgElement.attr("data-original"));
            atlas.setTitle(imgElement.attr("alt"));
            atlasList.add(atlas);
        }
        return atlasList;
    }

    /**
     * 解析详情详情页图片对象
     * @param document
     * @return
     */
    private Image parseImageUrl(Document document) {
        Image image = new Image();
        Elements elements = document.select(".main-image > p > a > img");
        if (elements.size() > 0){
            Element element = elements.get(0);
            image.setUrl(element.attr("src"));
            image.setDesc(element.attr("alt"));
        }
        return image;
    }

    /**
     * 获取对应图册所有图片所在页面的Url地址
     * @param document
     * @return
     */
    private List<String> parseDetailUrl(Document document) {
        List<String> list = new ArrayList<>();
        int maxPage = 0;
        Elements elements = document.select(".pagenavi > a > span");
        for (Element element : elements){
            String strPage = element.text();
            if (strPage.matches("^[0-9]*$")){
                int page = Integer.valueOf(strPage);
                if (page > maxPage){
                    maxPage = page;
                }
            }
        }
        for (int i=1; i<=maxPage; i++){
            list.add(mCurrentUrl + i);
        }
        return list;
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
        if (mTypeMaxPageMap.get(mCurrentUrl) != null){
            return currentPage <= mTypeMaxPageMap.get(mCurrentUrl);
        }

        Elements elements = document.select("nav .nav-links .page-numbers");
        int maxPage = -1;
        for (Element element : elements){
            if (element.hasClass("dots")){
                continue;
            }
            String text = element.text();
            if (text.matches("[\\u4e00-\\u9fa5]")){
                continue;
            }
            try {
                int page = Integer.parseInt(text);
                if (page > maxPage){
                    maxPage = page;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mTypeMaxPageMap.put(mCurrentUrl,maxPage);
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
            type.setUrl(value);
            typeList.add(type);
            mTypeMap.put(value,text);
        }
    }

    private String parseUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
