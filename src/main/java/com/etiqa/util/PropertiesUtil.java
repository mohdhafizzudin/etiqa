package com.etiqa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final String MESSAGE_PROPERTIES = "message.properties";


    public static String findProperties(String key, String property) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(property)) {
            properties.load(inputStream);
        } catch (IOException e) {
            //log.error("Error", e);
            return "";
        }
        return properties.getProperty(key);
    }

    public static String loadMessageProperties(String key) {
        return findProperties(key, MESSAGE_PROPERTIES);
    }


}
