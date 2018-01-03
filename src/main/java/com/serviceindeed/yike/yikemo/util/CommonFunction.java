package com.serviceindeed.yike.yikemo.util;

import java.util.*;

public class CommonFunction {

    /**
     * 返回到前台Datatable的数据封装
     * @param status
     * @param draw
     * @param recordsTotal
     * @param recordsFiltered
     * @param data
     * @return
     */
    public static Map<String,Object> getDataTableResult(String status,Integer draw,Long recordsTotal,Long recordsFiltered,Object data){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status", status);
        //以下返回参数必须是固定的
        map.put("draw", draw);
        map.put("recordsTotal",recordsTotal);//数据总条数
        map.put("recordsFiltered",recordsFiltered);//显示的条数
        map.put("aData",data);//数据集合
        return map;
    }

    /**
     * 返回改前台数据的封装数据形式
     * @param status
     * @param result
     * @param newFilePath
     * @param zipFileName
     * @param urlPath
     * @return
     */
    public static Map<String,Object> getResult2(String status,boolean result,String newFilePath,String zipFileName,String urlPath){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("result",result);
        map.put("newFilePath",newFilePath);
        map.put("zipFileName",zipFileName);
        map.put("urlPath",urlPath);
        return map;
    }

    /**
     * 生成随机数
     * @param n
     * @return
     */
    public static String getRandom(int n){
        Random random = new Random();
        String result = "";
        for(int i = 0;i < n;i++){
            result+=random.nextInt(10);
        }
        return result;
    }
    public static Map<String,Object> getResult(String status,String result,Long total,Object rows){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("result",result);
        map.put("total",total);
        map.put("rows",rows);
        return map;
    }
    /**
     * 接口统一返回标准
     *@Author xw
     *@Date 2017/12/14 11:42
     */
    public static Map<String,Object> unifiedResult(String status,Object object,String  msg)
    {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",status);
        map.put("data",object);
        map.put("msg",msg);
        return map;
    }
}
