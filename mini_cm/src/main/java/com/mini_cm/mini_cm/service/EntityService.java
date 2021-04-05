package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.dao.EntityAttributeRepositoryInterface;
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
    private EntityAttributeRepositoryInterface entityAttributeRepositoryInterface;

    Action action=new Action();
    HashMap<Enum,String> finalAttributeMap =action.getActionAttribute();
    CommonRequestDTO commonRequestDTO =null;
    int sectionId=0;


    //get  attribute set from db
    private void getActionValuesFromSql(String id, PriorityLevel type,CommonRequestDTO commonRequestDTO,String author_name){
        switch (type){
            case GLOBAL: AttributeObject globalAttributeObject= entityAttributeRepositoryInterface.getActionValuesFromSql(id,type);
                         overrideAttributes(globalAttributeObject.getAction());
                         break;

            case CUSTOMER:
                        AttributeObject custAttributeObject= entityAttributeRepositoryInterface.getActionValuesFromSql(id,type);
                        overrideAttributes(custAttributeObject.getAction());
                        break;

            case ADTAG:
                        AttributeObject adtagAttributeObject= entityAttributeRepositoryInterface.getActionValuesFromSql(id,type);
                        overrideAttributes(adtagAttributeObject.getAction());
                        break;

            case SECTION:
                        AttributeObject sectionAttributeObject= entityAttributeRepositoryInterface.getActionValuesFromSql(id,type);
                        List<Section> sectionList=sectionAttributeObject.getSection();
                        Action sectionAttributeSet=new Action();
                        sectionAttributeSet=finalSectionSet(commonRequestDTO,author_name,sectionList);
                        overrideAttributes(sectionAttributeSet);
                        break;

        }


    }





    public HashMap<Enum,String> getFinalAttributeSet(){
        return finalAttributeMap;
    }

    public int sectionId(){
        return sectionId;
    }

    public void getActionValuesOnEntityLevel(CommonRequestDTO commonRequestDTO, String author_name){
        this.commonRequestDTO = commonRequestDTO;

        //fetch and override
        for(Integer priority:PriorityLevel.getPriorityLevelName.keySet()){
            PriorityLevel type=PriorityLevel.getPriorityLevelName.get(priority);
            getActionValuesFromSql(getId(type),type,commonRequestDTO,author_name);
        }

    }

    private String getId(PriorityLevel priorityLevelValue){
        String id="";
        switch (priorityLevelValue){
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

    private  Action finalSectionSet(CommonRequestDTO commonRequestDTO,String author_name,List<Section> sections)
    {
        Action finalSectionSet = new Action();
        boolean check;
        String country = commonRequestDTO.getCountry();
        String browser = commonRequestDTO.getBrowser();
        String device = commonRequestDTO.getDevice();
        String author = author_name;

        for (Section sectionListIterator : sections)
        {
            check=true;

            if (country != null)
            {
                if ( !(sectionListIterator.getSectionAttributeMap().get(RuleSet.COUNTRY) == null || Pattern.matches(sectionListIterator.getSectionAttributeMap().get(RuleSet.COUNTRY).toString(), country.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //browser
            if (browser != null)
            {

                if ( !(sectionListIterator.getSectionAttributeMap().get(RuleSet.BROWSER) == null || Pattern.matches(sectionListIterator.getSectionAttributeMap().get(RuleSet.BROWSER).toString(), browser.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //device
            if (device != null)
            {

                if ( !(sectionListIterator.getSectionAttributeMap().get(RuleSet.DEVICE) == null || Pattern.matches(sectionListIterator.getSectionAttributeMap().get(RuleSet.DEVICE).toString(), device.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //author
            if (author != null)
            {
                if (!(sectionListIterator.getSectionAttributeMap().get(RuleSet.AUTHOR_NAME) == null || Pattern.matches(sectionListIterator.getSectionAttributeMap().get(RuleSet.AUTHOR_NAME).toString(), author.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }


            if (check)
            {

                 sectionId = Integer.parseInt(sectionListIterator.getSectionAttributeMap().get(SectionSet.PRIORITY));
                for (AttributeSet i : AttributeSet.values())
                {
                    finalSectionSet.setValueInActionMap(i, sectionListIterator.getSectionAttributeMap().get(i));
                }
                break;
            }

        }
        return finalSectionSet;
    }


    //override attributes
    private void overrideAttributes(Action overridingActionObject)
    {
        if (overridingActionObject != null)
        {
            for (Enum key : finalAttributeMap.keySet())
            {
                if (overridingActionObject.getActionAttribute().get(key) != null)
                {
                    finalAttributeMap.put(key,overridingActionObject.getActionAttribute().get(key));
                }
            }
        }
    }


}
