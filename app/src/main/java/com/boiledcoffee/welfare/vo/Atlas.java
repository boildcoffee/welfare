package com.boiledcoffee.welfare.vo;

import java.util.List;

/**
 *
 * Created by jason on 2018/3/17.
 */

public class Atlas {
    private String title; //图册标题
    private String cover; //封面地址
    private String typeName;//所属类型名
    private String detailUrl;// 图册页面地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
