package com.mini_cm.mini_cm.dao;

import com.mini_cm.mini_cm.model.LogData;

import java.util.HashMap;

public interface LogDataRepository
{
    //log pageview
    public void savePublisher(LogData logData, int view, String keywords);
    public void saveKeywordNotLoad(LogData logData);

    //log on keyword click
    public void saveKeyword(LogData logData,String title);


    //log on ad display and click
    public void saveAdClick(LogData logData,String adName, String keyword);
    public void saveAds(LogData logData,String adTitle,String keyword);

    //log attribute details
    public void saveAttributes(LogData logData, HashMap<Enum,String> finalSet, int section_id);






}
