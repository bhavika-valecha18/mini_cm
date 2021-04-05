package com.mini_cm.mini_cm.dao;

import com.mini_cm.mini_cm.model.AttributeObject;
import com.mini_cm.mini_cm.model.PriorityLevel;

public interface EntityAttributeRepositoryInterface
{
    //get attributes
    public AttributeObject getActionValuesFromSql(String id, PriorityLevel type);
}
