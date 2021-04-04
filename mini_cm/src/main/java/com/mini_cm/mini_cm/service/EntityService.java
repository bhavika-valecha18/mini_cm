package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.dao.EntityAttributeRepository;
import com.mini_cm.mini_cm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class EntityService
{
    @Autowired
    private EntityAttributeRepository entityAttributeRepository;

    Action action=new Action();
    HashMap<Enum,String> overrideactions=action.getAttribute();
    CommonRequestDTO commonRequestDTO =null;
    int sectionId=0;


    //get  attribute set from db
    private void getActionValuesFromSql(String id, PriorityLevel type,CommonRequestDTO commonRequestDTO,String author_name){
        switch (type){
            case GLOBAL: Action global=new Action();
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
                        section_set=finalSectionSet(commonRequestDTO,author_name,sections);
                        overrideAttributes(section_set);
                        break;

        }


    }





    public HashMap<Enum,String> getFinalSet(){
        return overrideactions;
    }

    public int sectionId(){
        return sectionId;
    }

    public void callEntities(CommonRequestDTO commonRequestDTO, String author_name){
        this.commonRequestDTO = commonRequestDTO;

        //fetch and override
        for(Integer priority:PriorityLevel.getPriorityLevelName.keySet()){
            PriorityLevel type=PriorityLevel.getPriorityLevelName.get(priority);
            getActionValuesFromSql(getId(type),type,commonRequestDTO,author_name);
        }

    }

    private String getId(PriorityLevel type){
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

    private   Action finalSectionSet(CommonRequestDTO commonRequestDTO,String author_name,List<Section> sections)
    {
        Action action = new Action();
        boolean check;
        String country = commonRequestDTO.getCountry();
        String browser = commonRequestDTO.getBrowser();
        String device = commonRequestDTO.getDevice();
        String author = author_name;

        for (Section s : sections)
        {
            check=true;

            if (country != null)
            {
                if ( !(s.getAttribute().get(RuleSet.COUNTRY) == null || Pattern.matches(s.getAttribute().get(RuleSet.COUNTRY).toString(), country.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //browser
            if (browser != null)
            {

                if ( !(s.getAttribute().get(RuleSet.BROWSER) == null || Pattern.matches(s.getAttribute().get(RuleSet.BROWSER).toString(), browser.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //device
            if (device != null)
            {

                if ( !(s.getAttribute().get(RuleSet.DEVICE) == null || Pattern.matches(s.getAttribute().get(RuleSet.DEVICE).toString(), device.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //author
            if (author != null)
            {
                if (!(s.getAttribute().get(RuleSet.AUTHOR_NAME) == null || Pattern.matches(s.getAttribute().get(RuleSet.AUTHOR_NAME).toString(), author.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }


            if (check)
            {

                 sectionId = Integer.parseInt(s.getAttribute().get(SectionSet.PRIORITY));
                for (AttributeSet i : AttributeSet.values())
                {
                    action.set_value_in_key(i, s.getAttribute().get(i));
                }
                break;
            }

        }




        return action;
    }



    private void overrideAttributes(Action obj)
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
