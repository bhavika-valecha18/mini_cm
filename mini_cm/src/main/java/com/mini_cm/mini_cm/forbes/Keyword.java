package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keyword
{

    private long uuid;
    private String keyword_title;
   // private int keyword_count;
    private int keyword_clicked;
    private String timestamp;
}
