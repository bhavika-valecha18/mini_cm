package com.mini_cm.mini_cm.model;

import java.util.HashMap;



public class Action
{

    AttributeSet actionSet[]=AttributeSet.values();
    private  HashMap<Enum,String> actionMap=new HashMap<>();
    public Action()
    {
        for(AttributeSet i:actionSet)
        {
            actionMap.put(i,null);

        }

    }

    public void setValueInActionMap(Enum key, String value){
        this.actionMap.put(key,value);
    }

    public HashMap<Enum,String> getActionAttribute(){
        HashMap<Enum,String> attributeSet=new HashMap<>();
        attributeSet.putAll(this.actionMap);
        return attributeSet;
    }

}
