package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher
{
    private long uuid;
    private String publisher_id;
    private String publisher_url;
    private int adtag_view;
    private int adtag_loaded;
    private int keywords_allowed;
    private String browser;
    private String country;
    private String timestamp;
    private String adview_timestamp;
    //private String audit_key;

}
