package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.forbes.Action;
import com.mini_cm.mini_cm.forbes.LogData;
import com.mini_cm.mini_cm.forbes.LogDataRepository;
import com.mini_cm.mini_cm.forbes.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EntityService
{
    @Autowired
    private LogDataRepository logDataRepository;

    //log attribute
    public void saveAttributes(LogData logData,Action action,int section_id){

        logDataRepository.saveAttributes(logData,action,section_id);
    }


    //get customer and adtag attributes
    public Action getActionValuesFromSql(String id, String type){
        return logDataRepository.getActionValuesFromSql(id,type);

    }

    //get sections
    public List<Section>  getSectionAttributesFromSql(String cid){
        return logDataRepository.getSectionAttributesFromSql(cid);

    }

}
