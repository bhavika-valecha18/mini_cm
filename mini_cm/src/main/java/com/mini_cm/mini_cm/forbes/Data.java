package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Data
{
    private final String author;
    private final String domain;
    private final String rurl;
    private final String referrer;
    private final String  dtld;
    private final String  hint;
    private final String  customerId;
    private final String browser;
    private final String country;
    private final String uuid;
    private final String adTagId;
    private final String device;


    public String getId(String type){
        String id="";
        switch (type){
            case "customer":id=getCustomerId();
                            break;
            case "adtag":id=getAdTagId();
                            break;
            case "section":id=getCustomerId();
                            break;
            case "global":id="";
        }
        return id;
    }




}
