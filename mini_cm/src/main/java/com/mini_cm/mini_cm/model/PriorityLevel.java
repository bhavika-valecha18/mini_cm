package com.mini_cm.mini_cm.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.TreeMap;

@AllArgsConstructor
public enum PriorityLevel
{
    GLOBAL(1),CUSTOMER(2),SECTION(3),ADTAG(4);
    @NonNull
    public final int level;


      static public TreeMap<Integer,PriorityLevel> getPriorityLevelName=new TreeMap<>();

    static {
        for(PriorityLevel e : PriorityLevel.values()){
            getPriorityLevelName.put(e.level,e);
        }
    }

}
