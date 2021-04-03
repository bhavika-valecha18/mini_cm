package com.mini_cm.mini_cm.model;

import java.util.HashMap;



public class Action
{

    AttributeSet set[]=AttributeSet.values();
    private  HashMap<Enum,String> actions=new HashMap<>();
    public Action()
    {
        for(AttributeSet i:set)
        {
            actions.put(i,null);

        }

    }

    public void set_value_in_key(Enum key,String value){
        this.actions.put(key,value);
    }

    public HashMap<Enum,String> getAttribute(){
        HashMap<Enum,String> attribute_set=new HashMap<>();
        attribute_set.putAll(this.actions);
        return attribute_set;
    }

    //todo get actions object

    //override final action set
    //todo put in entity service
//    public void overrideAttributes(Action obj)
//    {
//        if (obj != null)
//        {
//            for (Enum key : actions.keySet())
//            {
//                if (obj.actions.get(key) != null)
//                {
//                    this.actions.put(key,obj.actions.get(key));
//                }
//            }
//        }
//    }




}
