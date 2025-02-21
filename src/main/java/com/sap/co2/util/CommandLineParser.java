package com.sap.co2.util;

import java.util.HashMap;
import java.util.Map;

public class CommandLineParser {
    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : args[i + 1];
                params.put(key, value);
                if (parts.length == 1) i++; // skip next arg if value was separate
            }
        }
        return params;
    }
}
