package com.xb.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 对象字段过滤器
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
public class FieldsFilter {
    public static Map<String,Object> filter(Object object, List<String> fieldNames){
        if(object == null){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        //把object转换成json对象
        JSONObject json = JSON.parseObject(JSON.toJSONString(object));
        //判断要过滤的字段
        for (String fieldName : fieldNames) {
            Object value = json.get(fieldName);
            map.put(fieldName,value);
        }
        return map;
    }

    public static List<Map<String,Object>> filter(List<Object> list, List<String> fields){
        if(list == null || list.size() == 0){
            return null;
        }
        List<Map<String,Object>> maps = new ArrayList<>();
        for (Object object : list) {
            Map<String, Object> filter = filter(object, fields);
            maps.add(filter);
        }
        return maps;
    }
}
