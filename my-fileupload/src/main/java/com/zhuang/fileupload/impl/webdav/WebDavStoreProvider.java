package com.zhuang.fileupload.impl.webdav;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.zhuang.fileupload.StoreProvider;
import com.zhuang.fileupload.config.MyFileUploadProperties;
import com.zhuang.fileupload.util.PathUtils;

import java.io.IOException;
import java.io.InputStream;

public class WebDavStoreProvider implements StoreProvider {

    private MyFileUploadProperties fileUploadProperties;

    public WebDavStoreProvider() {
        this(new MyFileUploadProperties());
    }

    public WebDavStoreProvider(MyFileUploadProperties fileUploadProperties) {
        this.fileUploadProperties = fileUploadProperties;
    }


    @Override
    public void save(InputStream inputStream, String path) {
        Sardine client = getClient();
        String fullPath = getFullPath(path);
        String dir = PathUtils.getDir(fullPath);
        String fileName = PathUtils.getFileName(fullPath);
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            createDirectory(client, dir);
            client.put(fullPath, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeClient(client);
        }
    }

    @Override
    public InputStream get(String path) {
        String fullPath = getFullPath(path);
        Sardine client = getClient();
        try {
            return client.get(fullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeClient(client);
        }
    }

    @Override
    public void delete(String path) {
        Sardine client = getClient();
        String fullPath = getFullPath(path);
        try {
            client.delete(fullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeClient(client);
        }
    }

    /**
     * 不支持单例模式运行，每次使用完了需要销毁
     */
    public Sardine getClient() {
        return SardineFactory.begin(fileUploadProperties.getWebDav().getUsername(), fileUploadProperties.getWebDav().getPassword());
    }

    public void closeClient(Sardine client) {
        if (client == null) return;
        try {
            client.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取远程绝对路径
     */
    public String getFullPath(String path) {
        return PathUtils.join(fileUploadProperties.getWebDav().getBaseUrl(), path);
    }


    /**
     * 递归创建目录
     */
    public void createDirectory(Sardine client, String path) throws IOException {
        if (!client.exists(path)) {
            createDirectory(client, PathUtils.join(PathUtils.getParent(path), "/"));
            client.createDirectory(path);
        }
    }
}
