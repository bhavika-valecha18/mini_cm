package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.dao.EntityAttributeRepository;
import com.mini_cm.mini_cm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class EntityService
{
    @Autowired
    public EntityAttributeRepository entityAttributeRepository;

    Action action=new Action();
    HashMap<Enum,String> overrideactions=action.getAttribute();
    CommonRequestDTO commonRequestDTO =null;


    //get customer and adtag attributes
    public void getActionValuesFromSql(String id, PriorityLevel type,CommonRequestDTO commonRequestDTO,String author_name){
        switch (type){
            case GLOBAL: Action global=new Action();

//                global.set_value_in_key(AttributeSet.BACKGROUND_COLOR,"blue");
//                global.set_value_in_key(AttributeSet.KEYWORD_COUNT,"7");
//                global.set_value_in_key(AttributeSet.KEYWORD_BLOCK,"0");
//                global.set_value_in_key(AttributeSet.FONT_NAME,"arial");
//                global.set_value_in_key(AttributeSet.FONT_STYLE,"normal");
//                global.set_value_in_key(AttributeSet.LID,null);
                LevelObject globalAttribute=entityAttributeRepository.getActionValuesFromSql(id,type);
                overrideAttributes(globalAttribute.getAction());
                break;

            case CUSTOMER:
                        LevelObject custAttribute=entityAttributeRepository.getActionValuesFromSql(id,type);
                        overrideAttributes(custAttribute.getAction());
                        break;

            case ADTAG:LevelObject adtagAttribute=entityAttributeRepository.getActionValuesFromSql(id,type);
                        overrideAttributes(adtagAttribute.getAction());
                        break;

            case SECTION:LevelObject sectionAttribute=entityAttributeRepository.getActionValuesFromSql(id,type);
                        List<Section> sections=sectionAttribute.getSection();
                        Action section_set=new Action();
                        section_set=Section.finalActionSet(commonRequestDTO,author_name,sections);
                        overrideAttributes(section_set);
                        break;

        }


    }





    public HashMap<Enum,String> getFinalSet(){
        HashMap<Enum,String> finalSet=action.getAttribute();


        return overrideactions;
    }

    public int sectionId(){
        return Section.getSectionId();
    }

    public void callEntities(CommonRequestDTO commonRequestDTO, String author_name){
        this.commonRequestDTO = commonRequestDTO;

        //fetch and override
        for(Integer priority:PriorityLevel.getPriorityLevelName.keySet()){
            PriorityLevel type=PriorityLevel.getPriorityLevelName.get(priority);
            getActionValuesFromSql(getId(type),type,commonRequestDTO,author_name);
        }

    }

    public String getId(PriorityLevel type){
        String id="";
        switch (type){
            case CUSTOMER:id= commonRequestDTO.getCustomerId();
                break;
            case ADTAG:id= commonRequestDTO.getAdTagId();
                break;
            case SECTION:id= commonRequestDTO.getCustomerId();
                break;
            case GLOBAL:id="";
        }
        return id;
    }

    public void overrideAttributes(Action obj)
    {
        if (obj != null)
        {
            for (Enum key : overrideactions.keySet())
            {
                if (obj.getAttribute().get(key) != null)
                {
                    overrideactions.put(key,obj.getAttribute().get(key));
                }
            }
        }
    }


}
