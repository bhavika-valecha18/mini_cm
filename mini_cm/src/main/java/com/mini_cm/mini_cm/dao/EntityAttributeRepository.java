package com.mini_cm.mini_cm.dao;

import com.mini_cm.mini_cm.model.LevelObject;
import com.mini_cm.mini_cm.model.PriorityLevel;

public interface EntityAttributeRepository
{
    //get attributes
    public LevelObject getActionValuesFromSql(String id, PriorityLevel type);
}
