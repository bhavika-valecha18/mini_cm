package com.mini_cm.mini_cm.dao;

import com.mini_cm.mini_cm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityAttributeRepositoryImpl implements EntityAttributeRepository
{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String GET_CUSTOMER_ATTRIBUTE = "SELECT * FROM customer WHERE cid=?";
    private static final String GET_ADTAG_ATTRIBUTE = "SELECT * FROM adtag WHERE ad_tag_id=?";
    private static final String GET_SECTIONS = "SELECT * from sections WHERE  cid=? order by priority desc";
    private static final String GET_GLOBAL_ATTRIBUTE_SET="SELECT * from global_attribute_set order by global_id desc limit 1";

    @Override
    public LevelObject getActionValuesFromSql(String id, PriorityLevel type)
    {
//    {
        AttributeSet set[] = AttributeSet.values();

        if (type.equals(PriorityLevel.CUSTOMER))
        {
            Action customerAttributeSet = new Action();
            Action finalCid = customerAttributeSet;
            customerAttributeSet = jdbcTemplate.queryForObject(GET_CUSTOMER_ATTRIBUTE, (rs, rowNum) ->
            {
                for (AttributeSet i : set)
                {
                    String key = i.name().toString().toLowerCase();
                    //System.out.println("in dao:" + rs.getString(key));
                    finalCid.set_value_in_key(i, rs.getString(i.name().toString().toLowerCase()));
                }
                return finalCid;

            }, id);
            return new LevelObject(customerAttributeSet, null);
        } else if (type.equals(PriorityLevel.ADTAG))
        {
            Action adtag = new Action();
            // Action Adtag = adtag;
            Action finalAdtag = adtag;
            adtag = jdbcTemplate.queryForObject(GET_ADTAG_ATTRIBUTE, (rs, rowNum) ->
            {
                for (AttributeSet i : set)
                {
                    //String key = i.name().toString().toLowerCase();
                    //System.out.println("in dao:"+rs.getString(key));
                    finalAdtag.set_value_in_key(i, rs.getString(i.name().toString().toLowerCase()));
                }
                return finalAdtag;

            }, id);
            return new LevelObject(adtag, null);
        } else if (type.equals(PriorityLevel.SECTION))
        {
            try
            {
                List<Section> sections = new ArrayList<>();
                sections = jdbcTemplate.query(GET_SECTIONS, (rs, rowNum) ->
                {
                    Section section = new Section();
                    for (Enum key : section.s)
                    {
                        //String key = value;
                        //System.out.println("in dao:" + rs.getString(key));
                        section.set_value_in_key(key, rs.getString(key.name().toString().toLowerCase()));
                    }
                    return section;
                }, id);
                //System.out.println("in dao:"+sections.get(0).section);
                return new LevelObject(null, sections);

            } catch (EmptyResultDataAccessException exception)
            {

            }
        }else if (type.equals(PriorityLevel.GLOBAL)){
            Action globalAttributeSet = new Action();
            Action defaultSet = globalAttributeSet;
            globalAttributeSet = jdbcTemplate.queryForObject(GET_GLOBAL_ATTRIBUTE_SET, (rs, rowNum) ->
            {
                for (AttributeSet i : set)
                {
                    String key = i.name().toString().toLowerCase();
                    //System.out.println("in dao:" + rs.getString(key));
                    defaultSet.set_value_in_key(i, rs.getString(i.name().toString().toLowerCase()));
                }
                return defaultSet;

            });
            return new LevelObject(globalAttributeSet, null);
        }

        return null;

    }

}
