package com.mini_cm.mini_cm.forbes;

public enum PriorityLevel
{
    GLOBAL(1),CUSTOMER(2),SECTION(3),ADTAG(4);

    public final int level;

    PriorityLevel(int level)
    {
     this.level=level;
    }

    public static String getNameByCode(int code){
        for(PriorityLevel e : PriorityLevel.values()){
            if(code == e.level) return e.name();
        }
        return null;
    }
}
