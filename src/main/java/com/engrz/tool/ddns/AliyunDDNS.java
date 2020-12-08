package com.engrz.tool.ddns;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

/**
 * @author Engr-Z
 * @since 2020/12/8
 */
public class AliyunDDNS {

    private AliyunConfig config;

    public AliyunDDNS(AliyunConfig config) {

        this.config = config;
    }

    public DDNSModel getDnsInfo(String domain, String rr, String type) {

        DDNSModel model = new DDNSModel();
        model.setDomain(domain);
        model.setRr(rr);
        model.setType(type);

        // 查询指定二级域名的最新解析记录
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        // 主域名
        describeDomainRecordsRequest.setDomainName(domain);
        // 主机记录
        describeDomainRecordsRequest.setRRKeyWord(rr);
        // 解析记录类型
        describeDomainRecordsRequest.setType(type);

        IAcsClient client = this.getClient();
        DescribeDomainRecordsResponse describeDomainRecordsResponse = this.describeDomainRecords(describeDomainRecordsRequest, client);
        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();
        if(domainRecords.size() == 0) {
            String msg = String.format("未找到记录，domain=%s, rr=%s, type=%s", domain, rr, type);
            throw new RuntimeException(msg);
        } else {
            DescribeDomainRecordsResponse.Record record = domainRecords.get(0);
            // 记录ID
            String recordId = record.getRecordId();
            model.setId(recordId);
            // 记录值
            String recordsValue = record.getValue();
            model.setValue(recordsValue);
        }

        return model;
    }

    /**
     *
     * @param model
     * @return
     */
    public void updateDns(DDNSModel model) {

        UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
        // 记录ID
        updateDomainRecordRequest.setRecordId(model.getId());
        // 主机记录
        updateDomainRecordRequest.setRR(model.getRr());
        // 将主机记录值改为当前主机IP
        updateDomainRecordRequest.setValue(model.getValue());
        // 解析记录类型
        updateDomainRecordRequest.setType(model.getType());

        IAcsClient client = this.getClient();
        UpdateDomainRecordResponse updateDomainRecordResponse = this.updateDomainRecord(updateDomainRecordRequest, client);
        Log.info(updateDomainRecordResponse.toString());
    }

    /**
     * 获取主域名的所有解析记录列表
     * @param request
     * @param client
     * @return
     */
    private DescribeDomainRecordsResponse describeDomainRecords(DescribeDomainRecordsRequest request, IAcsClient client){
        try {
            // 调用SDK发送请求
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            // 发生调用错误，抛出运行时异常
            throw new RuntimeException();
        }
    }

    /**
     * 更新解析
     * @param request
     * @param client
     * @return
     */
    private UpdateDomainRecordResponse updateDomainRecord(UpdateDomainRecordRequest request, IAcsClient client){
        try {
            // 调用SDK发送请求
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            // 发生调用错误，抛出运行时异常
            throw new RuntimeException();
        }
    }

    /**
     *
     * @return
     */
    private IAcsClient getClient() {

        // 设置鉴权参数，初始化客户端
        DefaultProfile profile = DefaultProfile.getProfile(
                config.getRegionId(),// 地域ID
                config.getAccessKeyId(),// 您的AccessKey ID
                config.getAccessKeySecret());// 您的AccessKey Secret
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
