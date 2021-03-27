package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serp
{
    private long serp_uuid;
    private String ad_name;
    private int ad_clicked;
    private String keyword;
    private String ad_clicked_timestamp;
    //private int ad_loaded;
}
