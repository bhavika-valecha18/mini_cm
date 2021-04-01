package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.forbes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class EntityService
{
    @Autowired
    private LogDataRepository logDataRepository;

    @Autowired
    private Action action;

    @Autowired
    private RuleObject ruleObject;



    //log attribute
    public void saveAttributes(LogData logData,Action action,int section_id){

        logDataRepository.saveAttributes(logData,action,section_id);
    }


    //get customer and adtag attributes
    public void getActionValuesFromSql(String id, String type){
        switch (type){
            case "global":Action global=new Action();
                global.set_value_in_key("background_color","blue");
                global.set_value_in_key("keyword_count","7");
                global.set_value_in_key("keyword_block","0");
                global.set_value_in_key("font_name","arial");
                global.set_value_in_key("font_style","normal");
                global.set_value_in_key("lid",null);
                action.overrideAttributes(global);
                break;

            case "customer":
                LevelObject custAttribute=logDataRepository.getActionValuesFromSql(id,type);
                HashMap<String,String> customer=new HashMap<String,String>();
                customer=custAttribute.getAction().getAttribute();
                action.overrideAttributes(custAttribute.getAction());



                break;
            case "adtag":LevelObject adtagAttribute=logDataRepository.getActionValuesFromSql(id,type);
                HashMap<String,String> adtag=new HashMap<String,String>();
                adtag=adtagAttribute.getAction().getAttribute();
                action.overrideAttributes(adtagAttribute.getAction());break;

            case "section":LevelObject sectionAttribute=logDataRepository.getActionValuesFromSql(id,type);
              //  System.out.println("ruleobj:"+ruleObject);
                List<Section> sections=sectionAttribute.getSection();
               // System.out.println("new sections:"+sections);
                Action section_set=new Action();
                //section_set=Section.finalSectionAttribute(sectionAttribute.getSection(),ruleObject);
               // System.out.println("firse check sections:"+sections);
                section_set=Section.finalActionSet(ruleObject,sections);
              //  System.out.println("section"+sections.size());
                //System.out.println(sections.get(0));
                //System.out.println("color:"+section_set.getAttribute().get("background_color"));
                action.overrideAttributes(section_set);
                //action.overrideAttributes(section_set);
                break;

        }
        //return logDataRepository.getActionValuesFromSql(id,type);

    }

    //get sections
    public List<Section> getSectionAttributesFromSql(String cid){
        return logDataRepository.getSectionAttributesFromSql(cid);

    }

    public void getRuleObject(RuleObject ruleObject){
        this.ruleObject=ruleObject;
    }

    public HashMap<String,String> getFinalSet(){
        HashMap<String,String> finalSet=new HashMap<>();
        finalSet=action.getAttribute();

        return finalSet;
    }

    public int sectionId(){
        return Section.getSectionId();
    }
}
