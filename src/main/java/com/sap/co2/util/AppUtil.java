package com.sap.co2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppUtil {

    public static final String ORS_TOKEN = "ORS_TOKEN";

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
