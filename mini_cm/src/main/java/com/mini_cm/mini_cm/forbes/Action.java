package com.mini_cm.mini_cm.forbes;


import com.mini_cm.mini_cm.service.EntityService;
import com.mini_cm.mini_cm.service.FinalActionSet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Data
@NoArgsConstructor
@Service
public class Action
{

    private String background_color;
    private int keyword_count;
    private int keyword_block;
    private String font_name;
    private String font_style;
    private String lid;

    public Action(String background_color, int keyword_count, int keyword_block, String font_name, String font_style, String lid)
    {
        this.background_color = background_color;
        this.keyword_count = keyword_count;
        this.keyword_block = keyword_block;
        this.font_name = font_name;
        this.font_style = font_style;
        this.lid = lid;
    }

    @Autowired
        private EntityService entityService;

    @Autowired
    private FinalActionSet finalActionSet;

    private int sectionId=-1;


    HashMap<String,Action> sectionAttribute=new HashMap<>();
    List<Section> sections=new ArrayList<>();
    Action customer,adtag,finalSet;



    public void getAttribute(String id,String type){

        switch (type){
            case "customer":customer=entityService.getActionValuesFromSql(id,type);
                break;
            case "adtag":adtag=entityService.getActionValuesFromSql(id,type);
                break;
        }

    }

    public String getSectionAttribute(RuleObject ruleObject,String  cid){

        sections=entityService.getSectionAttributesFromSql(cid);
        if(sections!=null){
           Action actions=finalSectionAttribute(sections,ruleObject);
            Action section=sectionAttribute.get("present");
            finalSet=finalActionSet.FinalAttribute(customer, adtag, actions);
//            System.out.println("final set");
//            System.out.println("color:"+finalSet.getBackground_color()+"count:"+finalSet.getKeyword_count()+finalSet.getKeyword_count()+finalSet.getFont_name()+finalSet.getFont_style()+finalSet.getLid());
//            return section.getLid();
            return  "";
        }else{
//            Action section=sectionAttribute.get("absent");
//            finalActionSet.FinalAttribute(customer, adtag, section);
//            finalSet=finalActionSet.FinalAttribute(customer, adtag, section);
            return "";
        }


    }




    public Action finalSectionAttribute(List<Section> sections,RuleObject ruleObject){
        System.out.println(sections.get(0));
        System.out.println(sections.size());
        List<Section> list=new ArrayList<>();
        TreeMap<Integer,List<Section>> lists=new TreeMap<>(Collections.reverseOrder());
        int priority= sections.get(0).getPriority();
        list.add(sections.get(0));
        for(int i=1;i<sections.size();i++){
            int p=sections.get(i).getPriority();
        if( priority==p){
            list.add(sections.get(i));
        }else
        {

            //System.out.println(lists.get(sections.get(i-1).getPriority()));
            lists.put(priority,list);
            list=new ArrayList<>();
//            System.out.println("1list"+list);
//            System.out.println("listsssss"+lists);
           // list.clear();
            list.add(sections.get(i));
            //System.out.println("list"+lists.get(priority));
            //System.out.println("2istsssss"+lists);
            priority=p;
        }
        }
        if(list.size()!=0){
            lists.put(priority,list);
        }

    Integer section_id=new Integer(-1);

        Action result=checkSections(lists,ruleObject,section_id);
        System.out.println(section_id);
       //   lists.put(priority,);

        //}

        return result;
    }

    public Action checkSections(TreeMap<Integer,List<Section>> sectionList,RuleObject ruleObject,Integer section_id){

        Action sec=null;
        for (Integer key : sectionList.keySet()){
            boolean check=true;
           List<Section> valueList=sectionList.get(key);
           for (Section s:valueList){
               //compare rule object,if any rule s does not match check=false
               System.out.println(s);
//               System.out.println(("!").concat(ruleObject.getCountry()));
//               System.out.println((s.getCountry())!=(("!").concat(ruleObject.getCountry())));
               //country
               if(ruleObject.getCountry()!=null){
                   if(!(ruleObject.getCountry().equals(s.getCountry()) || s.getCountry()==null || !(s.getCountry().substring(1).equals(ruleObject.getCountry())))){
                       check=false;
                       break;
                   }
               }

               //browser
               if(ruleObject.getBrowser()!=null){
                   if(!(ruleObject.getBrowser().equals(s.getBrowser()) || s.getBrowser()==null || s.getBrowser()!=(("!").concat(ruleObject.getBrowser())))){
                       check=false;
                       break;
                   }
               }

               //device
               if(ruleObject.getDevice()!=null){
                   if(!(ruleObject.getDevice().equals(s.getDevice()) || s.getDevice()==null || s.getDevice()!=(("!").concat(ruleObject.getDevice())))){
                       check=false;
                       break;
                   }
               }

               //author
               if(ruleObject.getAuthor_name()!=null){
                   if(!(ruleObject.getAuthor_name().equals(s.getAuthor_name()) || s.getAuthor_name()==null || s.getAuthor_name()!=(("!").concat(ruleObject.getAuthor_name())))){
                       check=false;
                       break;
                   }
               }


           }

           if(check){
               //return final section set
               List<Section> s=sectionList.get(key);
            sectionId=s.get(0).getSection_id();
               return new Action(s.get(0).getBackground_color(),s.get(0).getKeyword_count(),s.get(0).getKeyword_block(),s.get(0).getFont_name(),s.get(0).getFont_style(),s.get(0).getLid());
              // break;
           }
        }


        return null;
    }

    public Action getFinalSet(){
        return finalSet;
    }
    public int getSectionId(){
        return sectionId;
    }



}
