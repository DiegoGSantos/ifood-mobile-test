package com.diego.tweetssentimentsanalyzer.twitterRestClient.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {

    private JsonUtils(){}

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL,
                            Modifier.TRANSIENT,
                            Modifier.STATIC)
                    .create();
        }
        return gson;
    }

    public static <T> String toJson(T bodyObject) {
        return getGson().toJson(bodyObject);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, V> T parseMap(String json, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> resultMap = new HashMap<>();
        if (keyClass == null || valueClass == null) {
            return (T) resultMap;
        }
        Gson gson = getGson();
        Map<K, V> map = gson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());

        for (Map.Entry<K, V> entry : map.entrySet()) {
            String keyJson = gson.toJson(entry.getKey());
            K k = gson.fromJson(keyJson, keyClass);

            String valueJson = gson.toJson(entry.getValue());
            V v = gson.fromJson(valueJson, valueClass);
            resultMap.put(k, v);
        }
        return (T) resultMap;
    }

    @SuppressWarnings("unchecked")
    public static <T, K> T parseList(String json, Class<K> keyClass) {
        List<K> resultList = new ArrayList<>();
        if (keyClass == null) {
            return (T) resultList;
        }

        Gson gson = getGson();
        List<K> list = gson.fromJson(json, new TypeToken<List<K>>() {
        }.getType());

        for (K value : list) {
            String valueJson = gson.toJson(value);
            resultList.add(gson.fromJson(valueJson, keyClass));
        }
        return (T) resultList;
    }

    @SuppressWarnings("unchecked")
    public static <T, K> T parseSet(String json, Class<K> keyClass) {
        Set<K> resultSet = new HashSet<>();
        if (keyClass == null) {
            return (T) resultSet;
        }

        Gson gson = getGson();
        Set<K> set = gson.fromJson(json, new TypeToken<Set<K>>() {
        }.getType());

        for (K value : set) {
            String valueJson = gson.toJson(value);
            resultSet.add(gson.fromJson(valueJson, keyClass));
        }
        return (T) resultSet;
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String json, Class<?> keyClass) {
        if(json == null){
            return null;
        }
        return (T) getGson().fromJson(json, keyClass);
    }
}
