package com.mini_cm.mini_cm.model;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;


@Service
public class Section
{
    public HashMap<Enum,String> section=new HashMap<>();
   public Set<Enum> s=new HashSet();
   static AttributeSet action_set[]=AttributeSet.values();
    RuleSet rule_set[]=RuleSet.values();
    SectionSet set[]=SectionSet.values();
    public static int  sectionId=-1;

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
            s.add(i);
            section.put(i,null);

        }

        //actions
        for(AttributeSet i:action_set)
        {
            s.add(i);
            section.put(i,null);

        }

        //section
        for(SectionSet i:set)
        {

            s.add(i);
            section.put(i,null);

        }

    }



    public void set_value_in_key(Enum key,String value){
        this.section.put(key,value);

    }




    public static Action finalActionSet(CommonRequestDTO commonRequestDTO,String author_name,List<Section> sections)
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
                if ( !(s.section.get(RuleSet.COUNTRY) == null || Pattern.matches(s.section.get(RuleSet.COUNTRY).toString(), country.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //browser
            if (browser != null)
            {

                if ( !(s.section.get(RuleSet.BROWSER) == null || Pattern.matches(s.section.get(RuleSet.BROWSER).toString(), browser.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //device
            if (device != null)
            {

                if ( !(s.section.get(RuleSet.DEVICE) == null || Pattern.matches(s.section.get(RuleSet.DEVICE).toString(), device.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }

            //author
            if (author != null)
            {
                if (!(s.section.get(RuleSet.AUTHOR_NAME) == null || Pattern.matches(s.section.get(RuleSet.AUTHOR_NAME).toString(), author.toLowerCase())))
                {
                    check = false;
                    continue;

                }
            }


        if (check)
        {

            sectionId = Integer.parseInt(s.section.get(SectionSet.PRIORITY));
            for (AttributeSet i : action_set)
            {
                action.set_value_in_key(i, s.section.get(i));
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
