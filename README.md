阿里云DDNS
==================

接入阿里云API，实现代码修改域名解析设置功能。

## 配置文件 config.properties

```code
aliyun.region-id=区域id，可填 cn-shanghai
aliyun.access-key-id=阿里云接口访问id
aliyun.access-key-secret=阿里云接口访问secret
```
AccessKeyId和AccessKeySecret在阿里云后台获取。


## 示例：

**查看域名解析信息**

```code
java -jar ./aliyun-ddns.jar --op=view --config=D:/config.properties --domain=engr-z.com --rr=www --type=A
```

**修改域名解析**

```code
java -jar ./aliyun-ddns.jar --op=update --config=D:/config.properties --domain=engr-z.com --rr=www --type=A --value=127.0.0.1 --interval=5000
```

## 参数说明：

| 参数      | 备注         |
| --------------- | ---------------- |
| op | 执行的操作，必填。查看或更新，view/update |
| domain | 域名，如：baidu.com |
| rr | 记录，和域拼接在一起为完整域名，如：www |
| type | 解析类型，如：A/TXT/CNAME 等 |
| value | 记录值，如果是A类型则对应IP地址，以此类推 |
| interval | 执行间隔时间，单位毫秒。不指定则执行一次退出 |

