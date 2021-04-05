package com.mini_cm.mini_cm.model;

public enum SerpLogAndDisplay
{
    URL("rurl"),
    SESSION_ID("cookie"),
    KEYWORD_SELECTED("keywordSelected"),
    LISTING_ADS("listingAds");

    public final String value;

    SerpLogAndDisplay(String value){
        this.value=value;
    }

}
