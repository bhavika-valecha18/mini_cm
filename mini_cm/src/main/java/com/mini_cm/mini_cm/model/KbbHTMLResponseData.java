package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbbHTMLResponseData
{
    private ArrayList<String> list_of_keywords;
    private String background_color;
    private String keyword_count;
    private String keyword_block;
    private String font_name;
    private String font_style;
    private String lid;

}
