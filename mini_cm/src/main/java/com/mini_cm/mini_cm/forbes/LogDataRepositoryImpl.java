package com.mini_cm.mini_cm.forbes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LogDataRepositoryImpl implements LogDataRepository
{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String INSERT_PAGE_VIEW_QUERY = "INSERT INTO pageview(uuid,cid,publisher_url,country,user_agent,timestamp,ad_tag_id,viewability,keywords) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_NOT_LOAD_QUERY = "INSERT INTO adload(adload_uuid,publisher_url,user_agent,country,timestamp,cid,ad_tag_id) VALUES(?,?,?,?,?,?,?)";
    private static final String INSERT_KEYWORD_QUERY = "INSERT INTO keyword(keyword_uuid,keyword_title,user_agent,country,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?)";
    private static final String GET_COUNT = "SELECT keyword_count FROM keyword WHERE keyword_title=? AND keyword_uuid=?";
    private static final String UPDATE_KEYWORD_BY_TITLE = "UPDATE keyword SET keyword_count=? WHERE keyword_title=? AND keyword_uuid=?";
    private static final String INSERT_ADCLICK_INFO = "INSERT INTO adclick(uid,ad_name,keyword,user_agent,country,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_ADS_DISPLAY = "INSERT INTO adsdisplay(user_id,ad_title,keyword,country,user_agent,timestamp,cid,ad_tag_id,publisher_url) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_FINAL_ATTRIBUTES = "INSERT INTO attributelog(uuid,cid,ad_tag_id,section_id,background_color,keyword_count,keyword_block,font_name,font_style,lid,publisher_url,timestamp,browser,country) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_CUSTOMER_ATTRIBUTE = "SELECT * FROM customer WHERE cid=?";
    private static final String GET_ADTAG_ATTRIBUTE = "SELECT * FROM adtag WHERE ad_tag_id=?";
    private static final String GET_SECTIONS = "SELECT * from section WHERE  customer_id=? order by priority desc";

    @Override
    public void savePublisher(LogData logData, int view, String keywords)
    {
        try
        {
            System.out.println("in dao publisher");
            System.out.println(logData.getTimestamp());
            jdbcTemplate.update(INSERT_PAGE_VIEW_QUERY, logData.getUuid(), logData.getCid(), logData.getPublisher_url(), logData.getCountry(), logData.getBrowser(), logData.getTimestamp().toString(), logData.getAd_tag_id(), view, keywords);
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }


    @Override
    public void saveAdLoad(LogData logData)
    {
        jdbcTemplate.update(INSERT_NOT_LOAD_QUERY, logData.getUuid(), logData.getPublisher_url(), logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id());
    }


    @Override
    public int getCount(String title, long uuid)
    {
        int result = 0;
        //jdbcTemplate.queryF

        try
        {
            result = jdbcTemplate.queryForObject(GET_COUNT, (rs, rowNum) ->
            {


                // return  (rs.getInt("keyword_count") + 1);


                return (rs.getInt("keyword_count") + 1);


            }, title, uuid);

        } catch (EmptyResultDataAccessException exception)
        {
            System.out.println(exception);
            //saveKeyword(keyword,1);
        }
        if (result == 0)
        {
            return 1;
        } else
        {
            return result;
        }
    }

    @Override
    public void saveKeyword(LogData logData, String title)
    {


        jdbcTemplate.update(INSERT_KEYWORD_QUERY, logData.getUuid(), title, logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }


    @Override
    public void updateKeyword(long id, String title, int count)
    {

        jdbcTemplate.update(UPDATE_KEYWORD_BY_TITLE, count, title, id);
    }

    @Override
    public void saveAdClick(LogData logData, String adName, String keyword)
    {

        jdbcTemplate.update(INSERT_ADCLICK_INFO, logData.getUuid(), adName, keyword, logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }

    @Override
    public void saveAds(LogData logData, String adTitle, String keyword)
    {
        jdbcTemplate.update(INSERT_ADS_DISPLAY, logData.getUuid(), adTitle, keyword, logData.getBrowser(), logData.getCountry(), logData.getTimestamp().toString(), logData.getCid(), logData.getAd_tag_id(), logData.getPublisher_url());
    }

    @Override
    public void saveAttributes(LogData logData, Action action, int section_id)
    {

        jdbcTemplate.update(INSERT_FINAL_ATTRIBUTES,logData.getUuid(),logData.getCid(),logData.getAd_tag_id(),section_id,action.getAttribute().get("background_color"),action.getAttribute().get("keyword_count"),action.getAttribute().get("keyword_block"),action.getAttribute().get("font_name"),action.getAttribute().get("font_style"),action.getAttribute().get("lid"),logData.getPublisher_url(),logData.getTimestamp(),logData.getBrowser(),logData.getCountry());

    }

    @Override
    public LevelObject getActionValuesFromSql(String id, String type)
    {
//    {
        AttributeSet set[] = AttributeSet.values();

        if (type.equals("customer"))
        {
            Action cid = new Action();
            Action finalCid = cid;
            cid = jdbcTemplate.queryForObject(GET_CUSTOMER_ATTRIBUTE, (rs, rowNum) ->
            {
                for (AttributeSet i : set)
                {
                    String key = i.name().toString().toLowerCase();
                    //System.out.println("in dao:" + rs.getString(key));
                    finalCid.set_value_in_key(key, rs.getString(key));
                }
                return finalCid;

            }, id);
            return new LevelObject(cid,null);
        } else if(type.equals("adtag")){
            Action adtag = new Action();
           // Action Adtag = adtag;
            Action finalAdtag = adtag;
            adtag = jdbcTemplate.queryForObject(GET_ADTAG_ATTRIBUTE, (rs, rowNum) ->
            {
                for (AttributeSet i : set)
                {
                    String key = i.name().toString().toLowerCase();
                    //System.out.println("in dao:"+rs.getString(key));
                    finalAdtag.set_value_in_key(key, rs.getString(key));
                }
                return finalAdtag;

            }, id);
            return new LevelObject(adtag,null);
        }else if(type.equals("section")){
            try{
                List<Section> sections=new ArrayList<>();
                sections=jdbcTemplate.query(GET_SECTIONS,(rs,rowNum)->{
                    Section section=new Section();
                    for (String value : section.s){
                        String key =value ;
                        //System.out.println("in dao:" + rs.getString(key));
                        section.set_value_in_key(key, rs.getString(key));
                    }
                    return section;
                },id);
                //System.out.println("in dao:"+sections.get(0).section);
                return new LevelObject(null,sections);

            }catch (EmptyResultDataAccessException exception){

            }
        }



        return null;

    }

    @Override
    public List<Section> getSectionAttributesFromSql(String cid)
    {
        List<Section> sections=new ArrayList<>();

        try{
           return jdbcTemplate.query(GET_SECTIONS,(rs,rowNum)->{
               Section section=new Section();
               for (String value : section.s){
                    String key =value ;
                    //System.out.println("in dao:" + rs.getString(key));
                    section.set_value_in_key(key, rs.getString(key));
                }
               return section;
            },cid);

        }catch (EmptyResultDataAccessException exception){

        }
        return null;
    }

}

