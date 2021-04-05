package com.mini_cm.mini_cm.dao;

import com.mini_cm.mini_cm.model.AttributeSet;
import com.mini_cm.mini_cm.model.LogData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class LogDataRepository implements LogDataRepositoryInterface
{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private  final String INSERT_PAGE_VIEW_QUERY = "INSERT INTO pageview(uuid,cid,publisher_url,country,user_agent,timestamp,ad_tag_id,viewability,keywords) VALUES(?,?,?,?,?,?,?,?,?)";
    private  final String INSERT_NOT_LOAD_QUERY = "INSERT INTO adload(adload_uuid,publisher_url,user_agent,country,timestamp,cid,ad_tag_id) VALUES(?,?,?,?,?,?,?)";
    private  final String INSERT_KEYWORD_QUERY = "INSERT INTO keyword(keyword_uuid,keyword_title,user_agent,country,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?)";
    private  final String INSERT_ADCLICK_INFO = "INSERT INTO adclick(uid,ad_name,keyword,user_agent,country,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?,?)";
    private  final String INSERT_ADS_DISPLAY = "INSERT INTO adsdisplay(user_id,ad_title,keyword,country,user_agent,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?,?)";
    private  final String INSERT_FINAL_ATTRIBUTES = "INSERT INTO attributelog(uuid,cid,ad_tag_id,section_id,background_color,keyword_count,keyword_block,font_name,font_style,lid,publisher_url,timestamp,browser,country) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    @Override
    public void savePublisher(LogData logData, int view, String keywords)
    {
        try
        {

            jdbcTemplate.update(INSERT_PAGE_VIEW_QUERY, logData.getUuid(), logData.getCid(), logData.getPublisher_url(), logData.getCountry(), logData.getBrowser(), logData.getTimestamp().toString(), logData.getAd_tag_id(), view, keywords);
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }


    @Override
    public void saveKeywordNotLoad(LogData logData)
    {
        jdbcTemplate.update(INSERT_NOT_LOAD_QUERY, logData.getUuid(), logData.getPublisher_url(), logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id());
    }


    @Override
    public void saveKeyword(LogData logData, String title)
    {
        jdbcTemplate.update(INSERT_KEYWORD_QUERY, logData.getUuid(), title, logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }


    @Override
    public void saveAdClick(LogData logData, String adName, String keyword)
    {

        jdbcTemplate.update(INSERT_ADCLICK_INFO, logData.getUuid(), adName, keyword, logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }

    @Override
    public void saveAds(LogData logData, String adTitle, String keyword)
    {
        jdbcTemplate.update(INSERT_ADS_DISPLAY, logData.getUuid(), adTitle, keyword,  logData.getCountry(),logData.getBrowser(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }

    @Override
    public void saveAttributes(LogData logData, HashMap<Enum, String> finalSet, int section_id)
    {

        jdbcTemplate.update(INSERT_FINAL_ATTRIBUTES, logData.getUuid(), logData.getCid(), logData.getAd_tag_id(), section_id, finalSet.get(AttributeSet.BACKGROUND_COLOR), finalSet.get(AttributeSet.KEYWORD_COUNT), finalSet.get(AttributeSet.KEYWORD_BLOCK), finalSet.get(AttributeSet.FONT_NAME), finalSet.get(AttributeSet.FONT_STYLE), finalSet.get(AttributeSet.LID), logData.getPublisher_url(), logData.getTimestamp(), logData.getBrowser(), logData.getCountry());

    }



}

