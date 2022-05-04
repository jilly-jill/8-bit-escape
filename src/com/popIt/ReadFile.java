package com.popIt;

import java.io.InputStream;

public class ReadFile {

    public InputStream getFileFromResourceAsStream(String fileName, Class aClass) {
        ClassLoader classLoader = aClass.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

}
