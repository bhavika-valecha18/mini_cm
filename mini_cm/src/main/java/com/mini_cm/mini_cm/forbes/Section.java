package com.mini_cm.mini_cm.forbes;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;


@Service
public class Section
{
    public HashMap<String,String> section=new HashMap<>();
    Set<String> s=new HashSet();
   static AttributeSet action_set[]=AttributeSet.values();
    RuleSet rule_set[]=RuleSet.values();
    SectionSet set[]=SectionSet.values();
    private static int  sectionId=-1;

    @Override
    public String toString()
    {
        return "Section{" +
                "section=" + section +
                '}';
    }



    public Section()
    {

        //rules
        for(RuleSet i:rule_set)
        {
            s.add(i.name().toString().toLowerCase());
            section.put(i.name().toString().toLowerCase(),null);

        }

        //actions
        for(AttributeSet i:action_set)
        {
            s.add(i.name().toString().toLowerCase());
            section.put(i.name().toString().toLowerCase(),null);

        }

        //section
        for(SectionSet i:set)
        {
           // System.out.println(i.name().toString().toLowerCase());
            s.add(i.name().toString().toLowerCase());
            section.put(i.name().toString().toLowerCase(),null);

        }

    }

    public void createSectionSetAttributes(){
     //  Attribute ruleEnum=new RuleSet();
//        Attribute actionEnum=new AttributeSet();

    }

    public void set_value_in_key(String key,String value){
        this.section.put(key,value);
        //System.out.println("in action: "+" key:"+key+" value:"+value+"final value:"+this.actions.get(key));
    }

    public static Action finalSectionAttribute(List<Section> sections, RuleObject ruleObject){

        List<Section> list=new ArrayList<>();
        TreeMap<Integer,List<Section>> lists=new TreeMap<>(Collections.reverseOrder());
        System.out.println("in final:"+sections);
        int priority= Integer.parseInt(sections.get(0).section.get("priority"));

        list.add(sections.get(0));
        for(int i=1;i<sections.size();i++){
           // System.out.println("in sections");
            int p=Integer.parseInt(sections.get(i).section.get("priority"));
            //System.out.println("priority 2:"+p);
            if( priority==p){
                list.add(sections.get(i));
            }else
            {

                System.out.println("sections ka else");
                lists.put(priority,list);
                list=new ArrayList<>();
                list.add(sections.get(i));
//                System.out.println("list"+lists.get(priority));
//                System.out.println("2istsssss"+lists);
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

    public static Action checkSections(TreeMap<Integer,List<Section>> sectionList,RuleObject ruleObject,Integer section_id){
       // System.out.println("in check sections");
        Action sec=null;
//        for (Integer key : sectionList.keySet()){
//            System.out.println("key:"+key);
//            System.out.println("value of "+key+" "+sectionList.get(key));
//            List<Section> valueList=sectionList.get(key);
//            for (Section s:valueList){
//                System.out.println("mini_cm:"+s.section.get("background_color"));
//            }
//        }
        for (Integer key : sectionList.keySet()){
            boolean check=true;
            List<Section> valueList=sectionList.get(key);
            for (Section s:valueList){
                //System.out.println(valueList.size());
                //compare rule object,if any rule s does not match check=false
                //System.out.println(s);
                //country
                if(ruleObject.getCountry()!=null){

                    if(!(ruleObject.getCountry().equals(s.section.get("country")) || s.section.get("country")==null || !(s.section.get("country").substring(1).equals(ruleObject.getCountry())))){
                        System.out.println("making check false");
                        check=false;
                        break;
                    }
                }

                //browser
                if(ruleObject.getBrowser()!=null){

                    if(!(ruleObject.getBrowser().equals(s.section.get("browser")) || s.section.get("browser")==null || s.section.get("browser")!=(("!").concat(ruleObject.getBrowser())))){
                        check=false;
                       break;
                    }
                }

                //device
                if(ruleObject.getDevice()!=null){

                    if(!(ruleObject.getDevice().equals(s.section.get("device")) || s.section.get("device")==null || s.section.get("device")!=(("!").concat(ruleObject.getDevice())))){
                        check=false;
                       break;
                    }
                }

                //author
                if(ruleObject.getAuthor_name()!=null){

                    if(!(ruleObject.getAuthor_name().equals(s.section.get("author_name")) || s.section.get("author_name")==null || s.section.get("author_name")!=(("!").concat(ruleObject.getAuthor_name())))){
                        check=false;
                        break;
                    }
                }


            }
            //System.out.println("out for check");

            if(check){
                //return final section set
               // System.out.println("checking");
                List<Section> s=sectionList.get(key);
                sectionId=Integer.parseInt(s.get(0).section.get("priority"));
               // System.out.println("id:"+s.get(0).section.get("priority"));
                Action action=new Action();
                for (AttributeSet i : action_set)
                {
                    String k = i.name().toString().toLowerCase();
                    //System.out.println("in dao:" + rs.getString(key));
                    action.set_value_in_key(k,s.get(0).section.get(k) );
                }
                return action;
            }
        }


        return null;
    }

    public static Action finalActionSet(RuleObject ruleObject,List<Section> sections)
    {
        Action action = new Action();
        boolean check;

        System.out.println("in final section check");
        String country = ruleObject.getCountry();
        String browser = ruleObject.getBrowser();
        String device = ruleObject.getDevice();
        String author = ruleObject.getAuthor_name();

        for (Section s : sections)
        {
            check=true;
            System.out.println("in" + s);

            if (country != null)
            {
                //System.out.println(country+"regex browser:"+s.section.get("exclude_country"));
                //System.out.println("match:"+Pattern.matches(s.section.get("exclude_country").toString(),country));
                if ( !( (s.section.get("include_country") == null || Pattern.matches(s.section.get("include_country").toString(), country)) && (s.section.get("exclude_country") == null || !(Pattern.matches(s.section.get("exclude_country").toString(), country)))))
                {

                    check = false;

                }
            }

            //browser
            if (browser != null)
            {

                if ( !((s.section.get("include_browser") == null || Pattern.matches(s.section.get("include_browser").toString(), browser)) && (s.section.get("exclude_browser") == null || !(Pattern.matches(s.section.get("exclude_browser").toString(), browser)))))
                {
                    check = false;

                }
            }

            //device
            if (device != null)
            {

                if ( !((s.section.get("include_device") == null || Pattern.matches(s.section.get("include_device").toString(), device)) && (s.section.get("exclude_device") == null || !(Pattern.matches(s.section.get("exclude_device").toString(), device)))))
                {
                    check = false;

                }
            }

            //author
            if (author != null)
            {


                if (!((s.section.get("include_author_name") == null || Pattern.matches(s.section.get("include_author").toString(), author)) && (s.section.get("exclude_author_name") == null || !(Pattern.matches(s.section.get("exclude_author").toString(), author)))))
                {
                    check = false;

                }
            }

       // System.out.println("out");
            System.out.println(check);
        if (check)
        {

            sectionId = Integer.parseInt(s.section.get("priority"));
            System.out.println("final priority:" + sectionId);
            System.out.println("final settt" + s);

            for (AttributeSet i : action_set)
            {
                String k = i.name().toString().toLowerCase();
                //System.out.println("in dao:" + rs.getString(key));
                action.set_value_in_key(k, s.section.get(k));
            }
            break;
        }

    }




        return action;
    }

    public static int getSectionId(){
        return sectionId;
    }


}
