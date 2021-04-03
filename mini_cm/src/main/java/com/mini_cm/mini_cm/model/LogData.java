package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogData
{
    private long uuid;
    private String browser;
    private String country;
    private String timestamp;
    private String cid;
    private String ad_tag_id;
    private String publisher_url;





}
