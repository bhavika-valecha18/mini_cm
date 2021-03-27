package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class RuleObject
{

    private String country;
    private String browser;
    private String device;
    private String author_name;


}
