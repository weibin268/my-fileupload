package com.zhuang.fileupload.util;

public class FileUtils {

    public static String getExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dotIndex = filename.lastIndexOf('.');
            if (dotIndex > -1) {
                return filename.substring(dotIndex);
            }
        }
        return "";
    }

    public static String getDirPath(String fileFullPath) {
        int index = fileFullPath.lastIndexOf("/");
        if (index == -1) return null;
        return fileFullPath.substring(0, index);
    }

    public static String getFileName(String fileFullPath) {
        return fileFullPath.substring(fileFullPath.lastIndexOf("/") + 1, fileFullPath.length());
    }

    public static String combinePath(String path1, String path2) {
        if (path1.endsWith("/")) {
            path1 = path1.substring(0, path1.length() - 1);
        }
        if (path2.startsWith("/")) {
            path2 = path2.substring(1, path2.length());
        }
        String seperator = "/";
        if (path1 == null || path1.length() == 0 || path2 == null || path2.length() == 0) {
            seperator = "";
        }
        return path1 + seperator + path2;
    }

}
