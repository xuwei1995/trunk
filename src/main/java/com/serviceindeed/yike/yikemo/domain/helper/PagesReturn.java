package com.serviceindeed.yike.yikemo.domain.helper;

import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;

public class PagesReturn<T> {
    //默认分页查询要用的数据
    public  List<T> list=new ArrayList<>();
    public  Page<T> page=null;
    public  Long count=0L;
}
