package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonRequestDTO
{
private String customerId;
    private String browser;
    private String country;
    private String uuid;
    private String adTagId;
    private String device;



}
