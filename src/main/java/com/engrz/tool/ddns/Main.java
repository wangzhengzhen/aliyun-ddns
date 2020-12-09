package com.engrz.tool.ddns;

import com.aliyuncs.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Engr-Z
 * @since 2020/12/8
 */
public class Main {

    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args) throws Exception {

        Map<String, String> paramMap = CommonUtil.parseArgs(args);
        Log.info("接收参数：" + paramMap);

        // 读取配置文件
        String configFile = paramMap.get("config");
        AliyunConfig config;
        if (StringUtils.isEmpty(configFile)) {
            config = AliyunConfig.getConfig(Main.class.getResourceAsStream("/config.properties"));
        } else {
            config = AliyunConfig.getConfig(configFile);
        }

        String domain = paramMap.get("domain");
        String rr = paramMap.get("rr");
        String type = paramMap.get("type");
        String value = paramMap.get("value");
        String op = paramMap.get("op");
        String interval = paramMap.get("interval");

        switch (op) {
            case "view": {
                AliyunDDNS ddns = new AliyunDDNS(config);
                DDNSModel model = ddns.getDnsInfo(domain, rr, type);
                Log.info("查询结果：" + model.toString());
                break;
            }
            case "update": {

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        AliyunDDNS ddns = new AliyunDDNS(config);
                        DDNSModel model = ddns.getDnsInfo(domain, rr, type);
                        String rw = model.getValue();
                        String ip;
                        if (StringUtils.isEmpty(value)) {
                            ip = NetUtil.getWLANIP();
                        } else {
                            ip = value;
                        }
                        if (rw.equals(ip)) {
                            Log.info("记录值相同，无需修改");
                        } else {
                            ddns.updateDns(model);
                            Log.info("记录更新成功，" + model.toString());
                        }
                    }
                };
                Timer timer = new Timer();
                if (StringUtils.isEmpty(interval)) {
                    timer.schedule(task, 0);
                } else {
                    timer.schedule(task, 0, Long.parseLong(interval));
                }

                break;
            }
            default: {
                Log.info("op参数非法");
            }
        }

    }

}
