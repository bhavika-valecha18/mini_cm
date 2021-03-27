package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.forbes.Action;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinalActionSet
{
    Action global=new Action("blue",7,0,"arial","normal",null);
    Action finalSet;

    public FinalActionSet()
    {
        this.finalSet=global;


    }
    List<Action> actions=new ArrayList<>();


    //global,customer,section,adtag
    public Action FinalAttribute(Action customer,Action adtag,Action section)
    {
//        actions.add(customer);
//        actions.add(section);
//        actions.add(adtag);
//        for(Action a:actions){
//           Field[] fields = a.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                String fieldName = field.getName();
//               // System.out.println("field"+fieldName);
//                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                System.out.println("method:"+a+"."+methodName+"()");}
////                try {
////                    Method method = a.getClass().getMethod(methodName);
////                    Object value = method.invoke(a);
////                    System.out.println("field:" + fieldName + ",value:" + value);
////                } catch (NoSuchMethodException e) {
////                    System.out.println("no such method:" + methodName);
////                }
//
//
//            }


       // }

        //customer
        if(customer.getBackground_color()!=null){
            finalSet.setBackground_color(customer.getBackground_color());
        }
        if(String.valueOf(customer.getKeyword_count())!=null){
            finalSet.setKeyword_count(customer.getKeyword_count());
        }
        if(String.valueOf(customer.getKeyword_block())!=null){
            finalSet.setKeyword_block(customer.getKeyword_block());
        }
        if(customer.getFont_name()!=null){
            finalSet.setFont_name(customer.getFont_name());
        }
        if(customer.getFont_style()!=null){
            finalSet.setFont_style(customer.getFont_style());
        }
        if(customer.getLid()!=null){
            finalSet.setLid(customer.getLid());
        }

        //section
        if(section!=null)
        {
            if (section.getBackground_color() != null)
            {
                finalSet.setBackground_color(section.getBackground_color());
            }
            if (String.valueOf(section.getKeyword_count()) != null)
            {
                finalSet.setKeyword_count(section.getKeyword_count());
            }
            if (String.valueOf(section.getKeyword_block()) != null)
            {
                finalSet.setKeyword_block(section.getKeyword_block());
            }
            if (section.getFont_name() != null)
            {
                finalSet.setFont_name(section.getFont_name());
            }
            if (section.getFont_style() != null)
            {
                finalSet.setFont_style(section.getFont_style());
            }
            if (section.getLid() != null)
            {
                finalSet.setLid(section.getLid());
            }

            //advertiser
            if (adtag.getBackground_color() != null)
            {
                finalSet.setBackground_color(adtag.getBackground_color());
            }
            if (String.valueOf(adtag.getKeyword_count()) != null)
            {
                finalSet.setKeyword_count(adtag.getKeyword_count());
            }
            if (String.valueOf(adtag.getKeyword_block()) != null)
            {
                finalSet.setKeyword_block(adtag.getKeyword_block());
            }
            if (adtag.getFont_name() != null)
            {
                finalSet.setFont_name(adtag.getFont_name());
            }
            if (adtag.getFont_style() != null)
            {
                finalSet.setFont_style(adtag.getFont_style());
            }
            if (adtag.getLid() != null)
            {
                finalSet.setLid(adtag.getLid());
            }
        }



        return finalSet;
    }
}
