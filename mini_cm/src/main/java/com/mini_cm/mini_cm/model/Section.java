package com.mini_cm.mini_cm.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



public class Section
{
    private HashMap<Enum,String> sectionMap =new HashMap<>();
   private Set<Enum> sectionSet =new HashSet();
   static AttributeSet actionSet[]=AttributeSet.values();
    RuleSet ruleSet[]=RuleSet.values();
    SectionSet sectionValueSet[]=SectionSet.values();


    public Section()
    {

        //rules
        for(RuleSet i: ruleSet)
        {
            sectionSet.add(i);
            sectionMap.put(i,null);

        }

        //actions
        for(AttributeSet i:actionSet)
        {
            sectionSet.add(i);
            sectionMap.put(i,null);

        }

        //section
        for(SectionSet i: sectionValueSet)
        {

            sectionSet.add(i);
            sectionMap.put(i,null);

        }

    }



    public void setValueInSectionMap(Enum key, String value){
        this.sectionMap.put(key,value);

    }


    public HashMap<Enum,String> getSectionAttributeMap(){
        HashMap<Enum,String> attributeSet=new HashMap<>();
        attributeSet.putAll(this.sectionMap);
        return attributeSet;
    }

    public Set<Enum> getSectionAttributeSet(){
        Set<Enum> attributeSet=new HashSet<>();
        attributeSet.addAll(this.sectionSet);
        return attributeSet;
    }





}
