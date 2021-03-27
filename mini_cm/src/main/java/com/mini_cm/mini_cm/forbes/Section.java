package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class Section
{
    private String country;
    private String browser;
    private String device;
    private String author_name;
    private int priority;
    private String background_color;
    private int keyword_count;
    private int keyword_block;
    private String font_name;
    private String font_style;
    private String lid;
    private int section_id;

}
