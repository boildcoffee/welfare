package com.boiledcoffee.welfare.vo;

/**
 * Created by jason on 2018/3/17.
 */

public class Type {
    private String name; //类型名称
    private String url; //页面地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (name != null ? !name.equals(type.name) : type.name != null) return false;
        return url != null ? url.equals(type.url) : type.url == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
