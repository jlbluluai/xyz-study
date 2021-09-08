package com.xyz.study.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    /**
     * 传入文件所在resources下路径，读取文件，将每一行封装到list中
     *
     * @param filepath
     * @return
     */
    public static List<String> fileToList(String filepath) {
        List<String> subscribers = Lists.newArrayList();
        try (
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                subscribers.add(line);
            }
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return subscribers;
    }

    public static List<String> localFileToList(String filePath) {
        List<String> contents = Lists.newArrayList();
        File file = new File(filePath);
        try (
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return contents;
    }

    public static String localFileToString(File file) {
        List<String> contents = Lists.newArrayList();
        try (
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            log.error("read file error", e);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < contents.size(); i++) {
            if (i != contents.size() - 1) {
                builder.append(contents.get(i)).append("\r\n");
            } else {
                builder.append(contents.get(i));
            }
        }
        return builder.toString();
    }

    public static String localFileToString(String filePath) {
        List<String> contents = Lists.newArrayList();
        File file = new File(filePath);
        try (
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            log.error("read file error", e);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < contents.size(); i++) {
            if (i != contents.size() - 1) {
                builder.append(contents.get(i)).append("\r\n");
            } else {
                builder.append(contents.get(i));
            }
        }
        return builder.toString();
    }

    public static void writeToLocal(String filePath, String fileName, String content) {
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            File file = new File(filePath + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            os = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            log.error("write file error", e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error("write file error", e);
            }
        }
    }

    /**
     * 获取一个目录下所有文件的名字
     * @param path 路径
     * @return 文件名列表
     */
    public static List<String> localDirFileNames(String path) {
        File dir = new File(path);
        String[] files = dir.list();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(files).collect(Collectors.toList());
    }

    /**
     * 获取文件的后缀
     *
     * @param fileName 文件名
     * @return 后缀
     */
    public static String getFileSuffix(String fileName) {
        String[] s = StringUtils.split(fileName, ".");
        if (s.length > 1) {
            return s[s.length - 1];
        }
        return StringUtils.EMPTY;
    }

}
