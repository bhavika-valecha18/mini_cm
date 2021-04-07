package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueryParams
{

    private String uuid;
    private String keyword;
    private String country;
    private String adTagId;
    private String cid;
    private String publisher_url;
    private int auditKey;
    private int viewability;
    private String timestamp;
    private String[] adsDisplay;
    private String adUrl;
    private String adTitle;
    private String userAgent;

}
