package com.mini_cm.mini_cm.forbes;

import java.util.List;

public interface LogDataRepository
{
    //log pageview
    public void savePublisher(LogData logData,int view,String keywords);
    public void saveAdLoad(LogData logData);

    //log on keyword click
    public void saveKeyword(LogData logData,String title);
    public int getCount(String title,long uuid);
    public void updateKeyword(long id,String title,int count);

    //log on ad display and click
    public void saveAdClick(LogData logData,String adName, String keyword);
    public void saveAds(LogData logData,String adTitle,String keyword);

    //log attribute details
    public void saveAttributes(LogData logData,Action action,int section_id);


    //get attributes
    public LevelObject getActionValuesFromSql(String id, String type);
    public List<Section> getSectionAttributesFromSql(String cid);


}
