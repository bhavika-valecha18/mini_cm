package com.mini_cm.mini_cm.forbes;

import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class Action
{

    AttributeSet set[]=AttributeSet.values();
    private  HashMap<String,String> actions=new HashMap<>();
    public Action()
    {
        for(AttributeSet i:set)
        {
            actions.put(i.name().toString().toLowerCase(),null);

        }

    }

    public void set_value_in_key(String key,String value){
        this.actions.put(key,value);
    }

    public HashMap<String,String> getAttribute(){
        HashMap<String,String> attribute_set=new HashMap<>();
        attribute_set.putAll(this.actions);
        return attribute_set;
    }


    //override final action set
    public void overrideAttributes(Action obj)
    {
        if (obj != null)
        {
            for (String key : actions.keySet())
            {
                if (obj.actions.get(key) != null)
                {
                    this.actions.put(key,obj.actions.get(key));
                }
            }
        }
    }




}
