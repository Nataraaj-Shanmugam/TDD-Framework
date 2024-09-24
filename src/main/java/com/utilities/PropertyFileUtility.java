package com.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class PropertyFileUtility {
    private final Properties property = new Properties();

    public PropertyFileUtility(String filePath) throws IOException {
        property.load(Files.newInputStream(new File(filePath).toPath()));
    }

    public String getProperty(String key) {
        return property.containsKey(key) ? (String) property.get(key) : "";
    }
}
