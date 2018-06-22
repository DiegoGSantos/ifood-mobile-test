package com.diego.tweetssentimentsanalyzer.twitterRestClient.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class FileUtils {

    private static Gson gson;
    private static JsonParser parser;

    public static String readJson(String path) {
        URL url = FileUtils.class.getClassLoader().getResource(path);
        if (url == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String file = "";
            while (reader.ready()) {
                file += reader.readLine();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static <T> T readJson(String path, Class<T> modelOfJson) {
        String jsonString = readJson(path);
        if (jsonString == null) {
            return null;
        }

        JsonElement element = getJsonParser().parse(jsonString);
        Gson gson = getGson();
        return gson.fromJson(element, modelOfJson);
    }

    public static <T> List<T> readJsonList(String path, Class<T> modelOfJson) {
        String jsonString = readJson(path);
        if (jsonString == null) {
            return null;
        }

        return JsonUtils.parseList(jsonString, modelOfJson);
    }

    protected static JsonParser getJsonParser() {
        if (parser == null) {
            parser = new JsonParser();
        }
        return parser;
    }

    protected static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
