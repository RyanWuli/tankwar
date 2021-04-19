package com.tank.mca.tank6;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Ryan
 * @Date: 2021/4/17 14:18
 * @Version: 1.0
 * @Description: 配置加载类
 */
public class ConfigMgr {

    public static Properties prop = new Properties();

    static {
        try {
            prop.load(ConfigMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 key 获取 value
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        return prop.get(key);
    }

    public static Integer getIntValue(String key) {
        if (prop.get(key) == null) return null;
        return Integer.parseInt((String) prop.get(key));
    }

    public static String getStrValue(String key) {
        if (prop.get(key) == null) return null;
        return (String) prop.get(key);
    }

    public static void main(String[] args) {
        System.out.println(prop.get("initTankCount"));
        System.out.println(ConfigMgr.getIntValue("initTankCount"));
    }
}
