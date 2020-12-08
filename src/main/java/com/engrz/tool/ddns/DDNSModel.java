package com.engrz.tool.ddns;

import lombok.Data;

/**
 * @author Engr-Z
 * @since 2020/12/8
 */
@Data
public class DDNSModel {

    /**
     * 记录id
     */
    private String id;

    /**
     * 域名，如 engr-z.com
     */
    private String domain;

    /**
     * 记录，如 ddns
     */
    private String rr;

    /**
     * 记录值
     */
    private String value;

    /**
     * 解析类型，如：A/TXT/@/*
     */
    private String type;

}
