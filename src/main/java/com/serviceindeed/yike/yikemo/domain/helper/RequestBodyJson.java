package com.serviceindeed.yike.yikemo.domain.helper;

public class RequestBodyJson<T> {
    HttpPages page;
    T obj;

    public HttpPages getPage() {
        return page;
    }

    public void setPage(HttpPages page) {
        this.page = page;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
