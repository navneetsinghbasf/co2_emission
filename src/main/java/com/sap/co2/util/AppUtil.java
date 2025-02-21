package com.sap.co2.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppUtil {

    private static final String ORS_TOKEN = "ORS_TOKEN";
    private static String API_KEY;

    public static String fetchApiKey() {


        API_KEY = System.getenv(ORS_TOKEN);

        if (StringUtils.isBlank(API_KEY)) {
            System.out.println("###### ORS_TOKEN NOT CONFIGURED, ATTEMPTING TO USE FALLBACK VERSION FROM APPLICATION.PROPERTIES ######");
            API_KEY = AppConfig.get("api.key");
            if (StringUtils.isBlank(API_KEY)) {
                throw new IllegalArgumentException("API KEY NOT FOUND");
            }
        }
        return API_KEY;
    }

    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = AppUtil.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }


}
