package com.zhuang.fileupload;

import java.io.InputStream;

public interface StoreProvider {

    void save(InputStream inputStream, String path);

    InputStream get(String path);

    void delete(String path);
}
