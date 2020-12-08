package com.engrz.tool.ddns;

import lombok.Data;

import java.io.*;
import java.util.Properties;

/**
 * @author Engr-Z
 * @since 2020/12/8
 */
@Data
public class AliyunConfig {

    /**
     * key
     */
    private String accessKeyId;

    /**
     * 密钥
     */
    private String accessKeySecret;

    /**
     * 地区
     * cn-shanghai
     */
    private String regionId;

    /**
     * 读取配置文件
     * @param path
     * @return
     */
    public static AliyunConfig getConfig(String path) throws IOException {

        File f = new File(path);
        if (!f.isFile() || !f.exists()) {
            throw new FileNotFoundException("配置文件未找到：" + path);
        }
        InputStream is = new FileInputStream(f);
        AliyunConfig config = getConfig(is);
        is.close();
        return config;
    }

    public static AliyunConfig getConfig(InputStream is) throws IOException {


        Properties p = new Properties();
        p.load(is);

        AliyunConfig config = new AliyunConfig();
        String regionId = p.getProperty("aliyun.region-id");
        config.setRegionId(regionId);
        String accessKeyId = p.getProperty("aliyun.access-key-id");
        config.setAccessKeyId(accessKeyId);
        String accessKeySecret = p.getProperty("aliyun.access-key-secret");
        config.setAccessKeySecret(accessKeySecret);

        Log.debug("加载配置：" + config.toString());

        return config;
    }

}
