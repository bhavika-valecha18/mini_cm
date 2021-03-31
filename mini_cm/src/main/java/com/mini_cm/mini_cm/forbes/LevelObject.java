package com.mini_cm.mini_cm.forbes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class LevelObject
{
    Action action;
    List<Section> section;


}
