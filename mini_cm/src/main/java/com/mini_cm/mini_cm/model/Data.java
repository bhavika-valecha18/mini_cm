package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Data
{
    private String author;
    private String domain;
    private String rurl;
    private String referrer;
    private String  dtld;
    private String  hint;

    @Override
    public String toString()
    {
        return "Data{" +
                "author='" + author + '\'' +
                ", domain='" + domain + '\'' +
                ", rurl='" + rurl + '\'' +
                ", referrer='" + referrer + '\'' +
                ", dtld='" + dtld + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
