package com.serviceindeed.yike.yikemo.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 一些json与对象转换的工具集合类
 */
public class JsonUtils {

    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 使用Jackson 数据绑定 将对象转换为 json字符串
     * <p>
     * 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
     *
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            log.error("转换为json字符串失败", e);
        } catch (JsonMappingException e) {
            log.error("转换为json字符串失败", e);
        } catch (IOException e) {
            log.error("转换为json字符串失败", e);
        }
        return null;
    }

    /**
     * json字符串转化为 JavaBean
     * <p>
     * 还可以直接JsonUtils.getInstance().readValue(String content,Class valueType)用这种方式
     *
     * @param <T>
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T toJavaBean(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonParseException e) {
            log.error("json字符串转化为 javabean失败", e);
        } catch (JsonMappingException e) {
            log.error("json字符串转化为 javabean失败", e);
        } catch (IOException e) {
            log.error("json字符串转化为 javabean失败", e);
        }
        return null;
    }

    /**
     * json字符串转化为list
     * <p>
     * 还可以 直接使用  JsonUtils.getInstance().readValue(String content, new TypeReference<List<T>>(){})方式
     *
     * @param <T>
     * @param content
     * @param typeReference
     * @return
     * @throws IOException
     */
    public static <T> List<T> toJavaBeanList(String content, TypeReference<List<T>> typeReference) throws IOException {
        try {
            return objectMapper.readValue(content, typeReference);
        } catch (JsonParseException e) {
            log.error("json字符串转化为 list失败,原因:", e);
            throw new RuntimeException("json字符串转化为 list失败");
        } catch (JsonMappingException e) {
            log.error("json字符串转化为 list失败,原因", e);
            throw new JsonMappingException("json字符串转化为 list失败");
        } catch (IOException e) {
            log.error("json字符串转化为 list失败,原因", e);
            throw new IOException("json字符串转化为 list失败");
        }
    }

}