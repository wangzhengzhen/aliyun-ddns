package com.engrz.tool.ddns;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Engr-Z
 * @since 2020/12/9
 */
@UtilityClass
public class CommonUtil {

    public Map<String, String> parseArgs(String[] args) {

        Map<String, String> map = new HashMap<>();

        for (String s : args) {
            if (!s.startsWith("--") || s.indexOf("=") < 3) {
                Log.debug("非法参数：" + s);
                continue;
            }

            String[] split = s.substring(2).split("=");
            if (split.length < 2) {
                Log.debug("非法参数：" + s);
                continue;
            }
            String p = split[0];
            String v = split[1];
            map.put(p, v);
        }

        return map;
    }

}
