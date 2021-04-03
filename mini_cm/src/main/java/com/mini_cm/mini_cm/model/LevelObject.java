package com.mini_cm.mini_cm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelObject
{
    Action action;
    List<Section> section;
}
