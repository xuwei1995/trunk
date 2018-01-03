package com.serviceindeed.yike.yikemo.domain;

public class PageType {
    public int code;  //0为新增，1为修改
    public String type;

    public PageType(int code, String type)
    {
        this.code = code;
        this.type = type;
    }
}
