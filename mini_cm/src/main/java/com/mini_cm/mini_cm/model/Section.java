package com.mini_cm.mini_cm.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



public class Section
{
    private HashMap<Enum,String> section=new HashMap<>();
   private Set<Enum> s=new HashSet();
   static AttributeSet action_set[]=AttributeSet.values();
    RuleSet rule_set[]=RuleSet.values();
    SectionSet set[]=SectionSet.values();


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


    public HashMap<Enum,String> getAttribute(){
        HashMap<Enum,String> attribute_set=new HashMap<>();
        attribute_set.putAll(this.section);
        return attribute_set;
    }

    public Set<Enum> getSectionAttributeSet(){
        Set<Enum> attribute_set=new HashSet<>();
        attribute_set.addAll(this.s);
        return attribute_set;
    }





}
